package im.kirillt.yandexmoneyclient;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yandex.money.api.methods.BaseRequestPayment;

import org.parceler.Parcels;

import java.math.BigDecimal;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.databinding.ActivityPaymentBinding;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.events.payment.PaymentProcessEvent;
import im.kirillt.yandexmoneyclient.events.payment.PaymentProcessResultEvent;
import im.kirillt.yandexmoneyclient.events.payment.PaymentRequestEvent;
import im.kirillt.yandexmoneyclient.events.payment.PaymentRequestResultEvent;
import im.kirillt.yandexmoneyclient.fragments.PaymentFragment;
import im.kirillt.yandexmoneyclient.model.PaymentInfo;
import im.kirillt.yandexmoneyclient.model.util.TextWatcherAdapter;
import im.kirillt.yandexmoneyclient.provider.account.AccountCursor;
import im.kirillt.yandexmoneyclient.provider.account.AccountSelection;
import im.kirillt.yandexmoneyclient.utils.Converters;

public class PaymentActivity extends AppCompatActivity {

    public static final String PAYMENT_INFO = "paymentInfo";

    private PaymentInfo paymentInfo;
    private FloatingActionButton floatingActionButton;
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

        paymentInfo = new PaymentInfo(getBalance(this), "", "", "", false, null, getResources());

        initToolBar(Converters.bigDecimalToAmountString(paymentInfo.balance));

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
        context.startActivity(new Intent(context, PaymentActivity.class));
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

    public static BigDecimal getBalance(Context context) {
        BigDecimal rv = BigDecimal.ZERO;
        AccountCursor cursor = new AccountSelection().accountnumberNot("").query(context.getContentResolver());
        if (cursor != null && cursor.moveToFirst()) {
            rv = new BigDecimal(cursor.getBalance());
            cursor.close();
        }
        return rv;
    }

    public void onEventMainThread(AnyErrorEvent errorEvent) {
        Toast.makeText(this, errorEvent.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(PaymentRequestResultEvent event) {
        paymentRunning = false;
        Log.i("payment request result", event.response.toString());
        String msg = event.response.status.name()+(event.response.error != null ? ":"+event.response.error.toString() : "");
        paymentInfo.requestResultMessage.set(msg);
        paymentInfo.refreshing.set(false);
        if (event.response.status == BaseRequestPayment.Status.SUCCESS) {
            EventBus.getDefault().post(new PaymentProcessEvent(event.response.requestId));
        }
    }

    public void onEventMainThread(PaymentProcessResultEvent event) {
        paymentRunning = false;
        paymentInfo.finished.set(true);
        paymentInfo.refreshing.set(false);
        String msg = event.response.status.name()+(event.response.error != null ? ":"+event.response.error.toString() : "");
        paymentInfo.processResultMessage.set(msg);
    }

    public void onEventAsync(PaymentRequestEvent event) {
        paymentInfo.refreshing.set(true);
        event.request();
    }

    public void onEventAsync(PaymentProcessEvent event) {
        paymentInfo.refreshing.set(true);
        event.process();
    }

}

