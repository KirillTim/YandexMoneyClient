package im.kirillt.yandexmoneyclient;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yandex.money.api.model.AccountStatus;

import org.w3c.dom.Text;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.events.download.DownloadAccountInfoEvent;
import im.kirillt.yandexmoneyclient.events.download.DownloadAllEvent;
import im.kirillt.yandexmoneyclient.events.download.SuccessAccountInfoEvent;
import im.kirillt.yandexmoneyclient.fragments.auth.CreateLockCodeFragment;
import im.kirillt.yandexmoneyclient.fragments.auth.ErrorFragment;
import im.kirillt.yandexmoneyclient.fragments.auth.WebViewFragment;

public class AuthActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FrameLayout frameLayout;
    private TextView greetingTextView;
    private String token;
    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("AuthActivity", "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        frameLayout = (FrameLayout)findViewById(R.id.activity_auth_fragment_container);
        progressBar = (ProgressBar)findViewById(R.id.auth_progress_bar);
        greetingTextView = (TextView)findViewById(R.id.activity_auth_greeting);
        greetingTextView.setVisibility(View.GONE);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String token = getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).getString(YMCApplication.PREF_AUTH_TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            Log.i("Already authorized", "");
            setResult(RESULT_OK);
            this.finish();
            return;
        }
        Fragment fragment = WebViewFragment.newInstance(YMCApplication.REDIRECT_URI, YMCApplication.authParams.build());
        getSupportFragmentManager().beginTransaction().add(R.id.activity_auth_fragment_container, fragment).commit();
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
        Log.i("onBackPressed", "pressed");
        setResult(RESULT_CANCELED);
        finish();
    }

    public void getWebAuth(String token, String login, String errorMessage) {
        if (!TextUtils.isEmpty(errorMessage)) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_auth_fragment_container,
                    ErrorFragment.newInstance(errorMessage)).commit();
        } else {
            Log.i("getWebAuth()", "login: "+login+", token: "+token);
            this.token = token;
            this.login = login;
            showProgressBar();
            frameLayout.setVisibility(View.GONE);
            greetingTextView.setText(getString(R.string.greeting)+login+"!");
            EventBus.getDefault().post(new DownloadAccountInfoEvent(this));
        }
    }

    public void onEventAsync(DownloadAccountInfoEvent event) {
        event.download();
    }

    public void onEventMainThread(SuccessAccountInfoEvent event) {
        progressBar.setVisibility(View.INVISIBLE);
        greetingTextView.setVisibility(View.GONE);
        if (event.response.accountStatus == AccountStatus.ANONYMOUS) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_auth_fragment_container,
                    ErrorFragment.newInstance(getString(R.string.error_anonymous_account))).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_auth_fragment_container,
                    CreateLockCodeFragment.newInstance()).commit();
        }

    }

    public void getLockCode(String lockCode) {
        Log.i("getLockCode", "lock code: " + lockCode);
        YMCApplication.auth2Session.setAccessToken(token);
        getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).edit()
                .putString(YMCApplication.PREF_LOCK_CODE, lockCode)
                .putString(YMCApplication.PREF_AUTH_TOKEN, token).apply();
        setResult(RESULT_OK);
        finish();
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
