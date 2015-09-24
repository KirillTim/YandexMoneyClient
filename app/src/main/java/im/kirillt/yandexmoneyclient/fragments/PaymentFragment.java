package im.kirillt.yandexmoneyclient.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import im.kirillt.yandexmoneyclient.R;

public class PaymentFragment extends Fragment {

    public static final String KEY_WHO = "who";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_TO_BE_PAID = "toBePaid";

    private static final BigDecimal P2P_COMMISSION = new BigDecimal(0.005);
    private Model model;

    /**
     * The factory method to create a new instance of
     * fragment using the provided parameters.
     *
     * @param data Map with arguments
     * @return A new instance of fragment PaymentFragment.
     */
    public static PaymentFragment newInstance(Map<String,String> data) {
        Bundle dataBundle = new Bundle();
        dataBundle.putString(KEY_WHO, data.get(KEY_WHO));
        dataBundle.putString(KEY_TOTAL, data.get(KEY_TOTAL));
        dataBundle.putString(KEY_TO_BE_PAID, data.get(KEY_TO_BE_PAID));
        return newInstance(dataBundle);
    }

    /**
     * The factory method to create a new instance of
     * fragment using the provided parameters.
     *
     * @param data bundle with arguments
     * @return A new instance of fragment PaymentFragment.
     */
    public static PaymentFragment newInstance(Bundle data) {
        PaymentFragment fragment = new PaymentFragment();
        fragment.setArguments(data);
        return fragment;
    }

    /**
     * The factory method to create a new instance of
     * fragment without parameters
     *
     * @return A new instance of fragment PaymentFragment.
     */

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("PaymentFragment", "onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            model = new Model(args.getString(KEY_WHO), args.getString(KEY_TOTAL), args.getString(KEY_TO_BE_PAID));
        } else {
            model = new Model("", null, null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    private class Model {
        public String who;
        public BigDecimal total = BigDecimal.ZERO;
        public BigDecimal toBePaid = BigDecimal.ZERO;

        public Model(String who, String totalStr, String toBePaidStr) {
            this.who = who == null ? "" : who;
            try {
                total = new BigDecimal(totalStr);
                toBePaid = new BigDecimal(toBePaidStr);
            } catch (NumberFormatException|NullPointerException ignored) {}
            try {
                if (!total.equals(BigDecimal.ZERO)) {
                    toBePaid = total.add(total.multiply(P2P_COMMISSION));
                } else if (!toBePaid.equals(BigDecimal.ZERO)) {
                    total = toBePaid.divide(BigDecimal.ONE.add(P2P_COMMISSION));
                }
            } catch (Exception ignored) {}


            if (TextUtils.isEmpty(totalStr)) {
                if (TextUtils.isEmpty(toBePaidStr))
            }
        }
    }
}
