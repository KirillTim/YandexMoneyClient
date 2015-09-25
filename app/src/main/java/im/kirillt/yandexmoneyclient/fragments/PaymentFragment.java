package im.kirillt.yandexmoneyclient.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SimpleTimeZone;

import im.kirillt.yandexmoneyclient.R;
import im.kirillt.yandexmoneyclient.provider.account.AccountCursor;
import im.kirillt.yandexmoneyclient.provider.account.AccountSelection;
import im.kirillt.yandexmoneyclient.utils.MyTextWatcher;
import im.kirillt.yandexmoneyclient.utils.Validator;

import static im.kirillt.yandexmoneyclient.utils.Converters.bigDecimalOrZero;
import static im.kirillt.yandexmoneyclient.utils.Converters.bigDecimalToAmountString;

public class PaymentFragment extends Fragment {

    public static final String KEY_WHO = "who";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_TO_BE_PAID = "toBePaid";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_BALANCE = "balance";
    public static final String KEY_COMMENT = "comment";

    private static final BigDecimal P2P_COMMISSION = new BigDecimal(0.005);
    private PaymentModel model;
    private TextInputLayout whoInputLayout;
    private EditText whoEditText;
    private TextInputLayout totalInputLayout;
    private EditText totalEditText;
    private TextInputLayout toBePaidInputLayout;
    private EditText toBePaidText;
    private TextInputLayout messageInputLayout;
    private EditText messageEditText;
    private View rootView;
    private BigDecimal balance = null;
    private int editTextAntiRecursionCounter = 0;

    public static PaymentFragment newInstance(Map<String, String> data) {
        Bundle dataBundle = new Bundle();
        dataBundle.putString(KEY_WHO, data.get(KEY_WHO));
        dataBundle.putString(KEY_TOTAL, data.get(KEY_TOTAL));
        dataBundle.putString(KEY_TO_BE_PAID, data.get(KEY_TO_BE_PAID));
        dataBundle.putString(KEY_MESSAGE, data.get(KEY_MESSAGE));
        dataBundle.putString(KEY_BALANCE, data.get(KEY_BALANCE));
        dataBundle.putString(KEY_COMMENT, data.get(KEY_COMMENT));
        return newInstance(dataBundle);
    }

    public static PaymentFragment newInstance(Bundle data) {
        PaymentFragment fragment = new PaymentFragment();
        fragment.setArguments(data);
        return fragment;
    }

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    public PaymentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("PaymentFragment", "onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            model = new PaymentModel(args.getString(KEY_WHO), args.getString(KEY_TOTAL),
                    args.getString(KEY_TO_BE_PAID), args.getString(KEY_MESSAGE), args.getString(KEY_COMMENT));
        } else {
            model = new PaymentModel("", null, null, "", "");
        }
        try {
            balance = new BigDecimal(getArguments().getString(KEY_BALANCE));
        } catch (Exception ignored) {};
        if (balance == null) {
            AccountCursor cursor = new AccountSelection().accountnumberNot("").query(getActivity().getContentResolver());
            if (cursor != null && cursor.moveToFirst()) {
                balance = new BigDecimal(cursor.getBalance());
                cursor.close();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        whoInputLayout = (TextInputLayout) rootView.findViewById(R.id.pay_who_wrapper);
        whoEditText = (EditText) rootView.findViewById(R.id.pay_who);
        whoEditText.setText(model.who);
        whoEditText.setOnFocusChangeListener((view, b) -> {
            if (!validateWho()) {
                whoInputLayout.setError("Invalid who parameter");
            }
        });
        totalInputLayout = (TextInputLayout) rootView.findViewById(R.id.pay_total_wrapper);
        totalInputLayout.setHint(getString(R.string.pay_total));
        totalEditText = (EditText) rootView.findViewById(R.id.pay_total);
        totalEditText.setText(bigDecimalToAmountString(model.total));
        totalEditText.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (editTextAntiRecursionCounter == 1) {
                    editTextAntiRecursionCounter = 0;
                } else if (editTextAntiRecursionCounter == 0) {
                    editTextAntiRecursionCounter = 1;
                    toBePaidText.setText(bigDecimalToAmountString(model.toBePaidFromTotal(bigDecimalOrZero(editable.toString()))));
                }
            }
        });
        toBePaidInputLayout = (TextInputLayout) rootView.findViewById(R.id.pay_to_be_paid_wrapper);
        toBePaidInputLayout.setHint(getString(R.string.pay_to_be_paid));
        toBePaidText = (EditText) rootView.findViewById(R.id.pay_to_be_paid);
        toBePaidText.setText(bigDecimalToAmountString(model.toBePaid));
        toBePaidText.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (editTextAntiRecursionCounter == 1) {
                    editTextAntiRecursionCounter = 0;
                } else if (editTextAntiRecursionCounter == 0) {
                    editTextAntiRecursionCounter = 1;
                    totalEditText.setText(bigDecimalToAmountString(model.totalFromToBePaid(bigDecimalOrZero(editable.toString()))));
                }
            }
        });
        ImageButton whoOptionsButton = (ImageButton) rootView.findViewById(R.id.pay_who_options_button);
        whoOptionsButton.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "More Options", Toast.LENGTH_SHORT).show();
        });
        messageInputLayout = (TextInputLayout) rootView.findViewById(R.id.pay_message_wrapper);
        messageInputLayout.setHint(getString(R.string.pay_recipient_message));
        messageEditText = (EditText) rootView.findViewById(R.id.pay_message);
        messageEditText.setText(model.message);
        return rootView;
    }

    public boolean validateInput() {
        boolean rv = true;
        if (!validateWho()) {
            whoInputLayout.setError("Invalid who parameter");
            rv = false;
        } else {
            whoInputLayout.setErrorEnabled(false);
        }
        if (!validatePayment()) {
            toBePaidInputLayout.setError("Not Enough Money");
            rv = false;
        } else {
            toBePaidInputLayout.setErrorEnabled(false);
        }
        return rv;
    }

    public PaymentModel getModel() {
        if (!validateInput()) {
            return null;
        }
        return model;
    }

    private boolean validateWho() {
        String who = whoEditText.getText().toString();
        return (Validator.validateAccountNumber(who) || Validator.validateEmail(who) || Validator.validatePhoneNumber(who));
    }

    private boolean validatePayment() {
        return bigDecimalOrZero(toBePaidText.getText().toString()).compareTo(balance) != 1;
    }

    public class PaymentModel {
        public String who;
        public BigDecimal total;
        public BigDecimal toBePaid;
        public String message;
        public String comment;
        public final String label = "Alternative Yandex Money Client";

        public PaymentModel(String who, String totalStr, String toBePaidStr, String message, String comment) {
            this.who = (who == null ? "" : who);
            this.message = (message == null ? "" : message);
            this.comment = (comment == null ? "" : comment);
            total = bigDecimalOrZero(totalStr);
            toBePaid = bigDecimalOrZero(toBePaidStr);
            try {
                if (!total.equals(BigDecimal.ZERO)) {
                    toBePaid = toBePaidFromTotal(total);
                } else if (!toBePaid.equals(BigDecimal.ZERO)) {
                    total = totalFromToBePaid(toBePaid);
                }
            } catch (Exception ignored) {ignored.printStackTrace();}
        }

        public BigDecimal totalFromToBePaid(BigDecimal toBePaid) {
            BigDecimal rv = BigDecimal.ZERO;
            try {
                rv = toBePaid.divide(BigDecimal.ONE.add(P2P_COMMISSION));
            } catch (Exception ignored) {ignored.printStackTrace();}
            return rv;
        }

        public BigDecimal toBePaidFromTotal(BigDecimal total) {
            BigDecimal rv = BigDecimal.ZERO;
            try {
                rv = total.add(total.multiply(P2P_COMMISSION));
            } catch (Exception ignored) {ignored.printStackTrace();}
            return rv;
        }
    }
}
