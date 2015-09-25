package im.kirillt.yandexmoneyclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yandex.money.api.utils.UrlEncodedUtils;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Map;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.fragments.PaymentFragment;
import im.kirillt.yandexmoneyclient.provider.account.AccountCursor;
import im.kirillt.yandexmoneyclient.provider.account.AccountSelection;

import static im.kirillt.yandexmoneyclient.utils.Converters.bigDecimalOrZero;
import static im.kirillt.yandexmoneyclient.utils.Converters.bigDecimalToAmountString;

public class PaymentActivity extends AppCompatActivity {

    private LinearLayout containerView;
    private FloatingActionButton floatingActionButton;
    private BigDecimal balance;
    private PaymentFragment inputFragment;
    private PaymentState activityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("PaymentActivity", "onCreate");
        super.onCreate(savedInstanceState);
        /*Log.i("intent", getIntent() + "");
        Log.i("intent Action", getIntent().getAction());*/

        /*Bundle bundle = getIntent().getExtras();
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            Log.d("extras", String.format("%s %s (%s)", key,
                    value.toString(), value.getClass().getName()));
        }*/
        setContentView(R.layout.activity_payment);
        containerView = (LinearLayout) findViewById(R.id.payment_activity_container);
        balance = getBalance(this);
        initToolBar(getString(R.string.pay_you_have)+" "+ bigDecimalToAmountString(balance));
        inputFragment = PaymentFragment.newInstance(getModelFromIntent(balance));
        getSupportFragmentManager().beginTransaction().add(R.id.payment_input_fragment, inputFragment).commit();
        floatingActionButton = (FloatingActionButton) findViewById(R.id.payment_fab);
        floatingActionButton.show();
        floatingActionButton.setOnClickListener(v -> {
            PaymentFragment.PaymentModel model = inputFragment.getModel();
            if (model != null) {
                Toast.makeText(PaymentActivity.this, "valid", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i("PaymentActivity", "onStart()");
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        Log.i("PaymentActivity", "onStop()");
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, PaymentActivity.class));
    }

    private Bundle getModelFromIntent(BigDecimal balance) {
        Bundle rv = new Bundle();
        String action = getIntent().getAction();
        if (action != null && action.equals(Intent.ACTION_VIEW)) {
            Map<String,String> params = null;
            try {
                params = UrlEncodedUtils.parse(getIntent().getDataString().toLowerCase());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if (params != null) {
                rv.putString(PaymentFragment.KEY_WHO, params.get("receiver"));
                rv.putString(PaymentFragment.KEY_TOTAL, params.get("sum"));
                rv.putString(PaymentFragment.KEY_MESSAGE, params.get("destination"));
                rv.putString(PaymentFragment.KEY_COMMENT, params.get("comment"));
            }
        } else {
            if (getIntent().getExtras() != null) {
                rv = getIntent().getExtras();
            }
        }
        rv.putString(PaymentFragment.KEY_BALANCE, bigDecimalToAmountString(balance));
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

    public void onEventMainThread(AnyErrorEvent errorEvent) {
        Toast.makeText(this, errorEvent.toString(), Toast.LENGTH_SHORT).show();
    }

    private enum PaymentState {
        PRE_REQUEST,
        REQUEST,
        REQUEST_ERROR,
        PROCESS,
        PROCESS_ERROR
    }

}


