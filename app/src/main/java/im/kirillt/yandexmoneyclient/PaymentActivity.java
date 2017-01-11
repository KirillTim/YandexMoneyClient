package im.kirillt.yandexmoneyclient;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.yandex.money.api.methods.BaseRequestPayment;
import com.yandex.money.api.model.*;
import com.yandex.money.api.model.Error;
import com.yandex.money.api.util.UrlEncodedUtils;
//import com.yandex.money.api.utils.UrlEncodedUtils;

import java.io.IOError;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Map;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.databinding.ActivityPaymentBinding;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
//import im.kirillt.yandexmoneyclient.events.download.DownloadAccountInfoEvent;
import im.kirillt.yandexmoneyclient.events.download.DownloadAccountInfoEvent;
import im.kirillt.yandexmoneyclient.events.download.SuccessAccountInfoEvent;
import im.kirillt.yandexmoneyclient.events.payment.PaymentProcessEvent;
import im.kirillt.yandexmoneyclient.events.payment.PaymentProcessResultEvent;
import im.kirillt.yandexmoneyclient.events.payment.PaymentRequestEvent;
import im.kirillt.yandexmoneyclient.events.payment.PaymentRequestResultEvent;
import im.kirillt.yandexmoneyclient.model.PaymentInfo;
import im.kirillt.yandexmoneyclient.model.util.TextWatcherAdapter;
import im.kirillt.yandexmoneyclient.provider.account.AccountCursor;
import im.kirillt.yandexmoneyclient.provider.account.AccountSelection;

import static im.kirillt.yandexmoneyclient.utils.Converters.bigDecimalToAmountString;

public class PaymentActivity extends BaseActivity {


    private FloatingActionButton floatingActionButton;

    private PaymentInfo paymentInfo;
    private boolean paymentRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityPaymentBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_payment);

        floatingActionButton = (FloatingActionButton) binding.getRoot().findViewById(R.id.payment_fab);
        floatingActionButton.show();
        floatingActionButton.setOnClickListener(v -> {
            if (paymentInfo.validateAmount()) {
                if (!paymentRunning) {
                    paymentRunning = true;
                    EventBus.getDefault().post(new PaymentRequestEvent(paymentInfo));
                }
            }
        });
        Switch codeProSwitch = (Switch) binding.getRoot().findViewById(R.id.pay_codepro_switch);
        codeProSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            paymentInfo.codePro.set(isChecked);
        });
        EventBus.getDefault().post(new DownloadAccountInfoEvent(this));

        BigDecimal balance = getBalance(this);

        paymentInfo = getModelFromIntent(balance);
        if (paymentInfo == null) {
            paymentInfo = new PaymentInfo(balance, "", "", "", false, null, getResources());
        }

        initToolBar(bigDecimalToAmountString(balance));

        binding.setPaymentInfo(paymentInfo);
        binding.payToBePaid.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                paymentInfo.changeAmountTotal();
                if (s.toString().equals("")) {
                    paymentInfo.amountTotal.set("");
                }
            }
        });
        binding.payTotal.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                paymentInfo.changeAmountTBP();
                if (s.toString().equals("")) {
                    paymentInfo.amountToBePaid.set("");
                }

            }
        });

    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, PaymentActivity.class).putExtra(YMCApplication.Companion.getFROM_INNER(), true));
    }


    @Override
    public void onStart() {
        Log.i("PaymentActivity_old", "onStart()");
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        Log.i("PaymentActivity_old", "onStop()");
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable(PAYMENT_INFO, Parcels.wrap(paymentInfo));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolBar(String actionBarTitle) {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(actionBarTitle);
        }
    }

    private PaymentInfo getModelFromIntent(BigDecimal balance) {
        PaymentInfo rv = null;
        String action = getIntent().getAction();
        if (action != null && action.equals(Intent.ACTION_VIEW)) {
            Map<String,String> params = null;
            try {
                params = UrlEncodedUtils.parse(getIntent().getDataString().toLowerCase());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if (params != null) {
                rv = new PaymentInfo(balance, params.get("receiver"), params.get("sum"),
                        params.get("destination"), false, null, getResources());
            }
        } else {
            if (getIntent().getExtras() != null) {
                //TODO create payment info from here
            }
        }
        return rv;
    }

    public static BigDecimal getBalance(Context context) {
        BigDecimal rv = BigDecimal.ZERO;
        AccountCursor cursor = new AccountSelection().accountnumberNot("").query(context.getContentResolver());
        if (cursor != null && cursor.moveToFirst()) {
            rv = new BigDecimal(cursor.getBalance());
            cursor.close();
        }
        return rv;
    }

    public void onEventAsync(DownloadAccountInfoEvent event) {
        event.download();
    }

    public void onEventMainThread(SuccessAccountInfoEvent event) {
        BigDecimal balance = event.getResponse().balance;
        getActionBar().setTitle(bigDecimalToAmountString(balance));
        paymentInfo.balance = balance;
    }

    public void onEventMainThread(AnyErrorEvent errorEvent) {
        if (errorEvent.getException() instanceof IOException) {
            Toast.makeText(this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
        }
    }

    public void onEventMainThread(PaymentRequestResultEvent event) {
        paymentRunning = false;
        Log.i("payment request result", event.getResponse().toString());
        if (event.getResponse().error != null) {
            String msg = "";
            if (event.getResponse().error == Error.ILLEGAL_PARAM_TO) {
                msg = getString(R.string.pay_illegal_param_to_error);
            } else {
                msg = "Server answer with error: "+ event.getResponse().error.toString();
            }
            floatingActionButton.show();
            paymentInfo.requestResultMessage.set(msg);
        }
        paymentInfo.refreshing.set(false);
        if (event.getResponse().status == BaseRequestPayment.Status.SUCCESS) {
            EventBus.getDefault().post(new PaymentProcessEvent(event.getResponse().requestId));
        }
    }

    public void onEventMainThread(PaymentProcessResultEvent event) {
        paymentRunning = false;
        paymentInfo.requestResultMessage.set("");
        paymentInfo.finished.set(true);
        paymentInfo.refreshing.set(false);
        String msg ;
        if (event.getResponse().error == null) {
            msg = getString(R.string.pay_success);
        } else  {
            msg = getString(R.string.pay_error)+": "+ event.getResponse().error.name();
        }
        paymentInfo.processResultMessage.set(msg);
    }

    public void onEventAsync(PaymentRequestEvent event) {
        paymentInfo.refreshing.set(true);
        floatingActionButton.hide();
        event.request();
    }

    public void onEventAsync(PaymentProcessEvent event) {
        paymentInfo.refreshing.set(true);
        floatingActionButton.hide();
        event.process();
    }

}

