package im.kirillt.yandexmoneyclient;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yandex.money.api.model.AccountStatus;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.events.WebAuthResultEvent;
//import im.kirillt.yandexmoneyclient.events.download.DownloadAccountInfoEvent;
import im.kirillt.yandexmoneyclient.events.download.DownloadAccountInfoEvent;
import im.kirillt.yandexmoneyclient.events.download.SuccessAccountInfoEvent;
import im.kirillt.yandexmoneyclient.fragments.auth.CreateLockCodeFragment;
import im.kirillt.yandexmoneyclient.fragments.auth.ErrorFragment;
import im.kirillt.yandexmoneyclient.fragments.auth.WebViewFragment;

public class AuthActivity extends AppCompatActivity {

    private static final String KEY_TOKEN = "token";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_STATE = "state";
    private static final String KEY_MESSAGE = "message";

    private ProgressBar progressBar;
    private FrameLayout frameLayout;
    private TextView greetingTextView;
    private String token;
    private String login;
    private String message;
    private int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (savedInstanceState != null) {
         /*   token = savedInstanceState.getString(KEY_TOKEN);
            login = savedInstanceState.getString(KEY_LOGIN);
            state = savedInstanceState.getInt(KEY_STATE);
            message = savedInstanceState.getString(KEY_MESSAGE);*/
        }

        frameLayout = (FrameLayout)findViewById(R.id.activity_auth_fragment_container);
        progressBar = (ProgressBar)findViewById(R.id.auth_progress_bar);
        greetingTextView = (TextView)findViewById(R.id.activity_auth_greeting);
        greetingTextView.setVisibility(View.GONE);

        initToolbar();

        String token = getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0)
                .getString(YMCApplication.PREF_AUTH_TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            setResult(RESULT_OK);
            this.finish();
            return;
        }
        //TODO: use parecalble model to hold data and fancy enum with states
        Fragment fragment ;
        if (state == 0) {
            fragment = WebViewFragment.newInstance(YMCApplication.data.getUrl(), YMCApplication.data.getParameters());
                    //YMCApplication.authParams.build());
        } else if (state == 1) {
            fragment = CreateLockCodeFragment.newInstance();
        } else {
            fragment = ErrorFragment.newInstance(message);
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_auth_fragment_container, fragment).commit();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.activity_auth_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    public void getWebAuth(String token, String login, String errorMessage) {
        if (!TextUtils.isEmpty(errorMessage)) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_auth_fragment_container,
                    ErrorFragment.newInstance(errorMessage)).commit();
            this.message = message;
            this.state = 2;
        } else {
            YMCApplication.client.setAccessToken(token);
            this.token = token;
            this.login = login;
            showProgressBar();
            frameLayout.setVisibility(View.GONE);
            greetingTextView.setText(getString(R.string.greeting)+login+"!");
            EventBus.getDefault().post(new DownloadAccountInfoEvent(this, login));
        }
    }

    public void onEventMainThread(WebAuthResultEvent event) {
        getWebAuth(event.token, event.login, event.errorDescription);
    }

    public void onEventAsync(DownloadAccountInfoEvent event) {
        event.download();
    }

    public void onEventMainThread(AnyErrorEvent event) {
        Toast.makeText(this, event.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(SuccessAccountInfoEvent event) {
        progressBar.setVisibility(View.INVISIBLE);
        greetingTextView.setVisibility(View.GONE);
        if (event.response.accountStatus == AccountStatus.ANONYMOUS) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_auth_fragment_container,
                    ErrorFragment.newInstance(getString(R.string.error_anonymous_account))).commit();
        } else {
            /*frameLayout.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().add(R.id.activity_auth_fragment_container,
                    CreateLockCodeFragment.newInstance()).commit();
            state = 1;*/
            saveTokenAndExit();
        }

    }

    public void saveTokenAndExit() {
        getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).edit()
                .putString(YMCApplication.PREF_AUTH_TOKEN, token).apply();
        setResult(RESULT_OK);
        finish();
    }

    public void getLockCode(String lockCode) {
        /*getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).edit()
                .putString(YMCApplication.PREF_LOCK_CODE, Encryption.passMD5(lockCode).toString())
                .putString(YMCApplication.PREF_AUTH_TOKEN, Encryption.encryptToken(token, lockCode).toString()).apply();*/
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(KEY_TOKEN, token);
        outState.putString(KEY_LOGIN, login);
        outState.putInt(KEY_STATE, state);
        outState.putString(KEY_MESSAGE, message);
    }

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
