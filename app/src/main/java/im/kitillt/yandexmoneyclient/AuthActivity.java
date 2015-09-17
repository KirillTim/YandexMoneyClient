package im.kitillt.yandexmoneyclient;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yandex.money.api.methods.Token;
import com.yandex.money.api.model.*;
import com.yandex.money.api.net.AuthorizationCodeResponse;
import com.yandex.money.api.net.OAuth2Authorization;
import com.yandex.money.api.net.OAuth2Session;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.Callable;

import im.kitillt.yandexmoneyclient.utils.ResponseReady;

public class AuthActivity extends AppCompatActivity {

    public static final String KEY_URL = "uri";
    public static final String KEY_POST_DATA = "postData";
    public static final String RESULT_ERROR_MSG = "errorMessage";
    public static final String RESULT_SUCCESS = "Success";

    private String errorDescription = null;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_auth);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        hideProgressBar();

        Intent intent = getIntent();
        String url = intent.getStringExtra(KEY_URL);
        byte[] postDataBytes = intent.getByteArrayExtra(KEY_POST_DATA);
        webView = (WebView) findViewById(R.id.auth_webview);

        authorization(url, postDataBytes, webView);

        intent = new Intent();
        if (TextUtils.isEmpty(errorDescription)) {
            intent.putExtra(RESULT_SUCCESS, true);
        } else {
            intent.putExtra(RESULT_SUCCESS, false);
            intent.putExtra(RESULT_ERROR_MSG, errorDescription);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    private void loadPage(String url, byte[] postData, WebView view) {
        Log.i("loadPage", "load");
        view.setVisibility(View.VISIBLE);
        view.postUrl(url, postData);
    }

    public void showProgressBar() {
        setProgressBarIndeterminateVisibility(true);
    }

    public void hideProgressBar() {
        setProgressBarIndeterminateVisibility(false);
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
        Log.i("onBackPressed", "pressed");
        super.onBackPressed();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void authorization(String url, byte[] postData, WebView authView) {
        authView.setWebChromeClient(new Chrome());
        authView.setWebViewClient(new GetTempTokenClient());
        authView.getSettings().setJavaScriptEnabled(true);
        loadPage(url, postData, authView);
    }

    private class GetTempTokenClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("GetTempTokenClient", "loading " + url);
            boolean completed = false;
            try {
                AuthorizationCodeResponse authResponse = AuthorizationCodeResponse.parse(url);
                if (authResponse.error == null) {
                    getPermanentToken(authResponse.code);
                    completed = true;
                } else {
                    completed = true;
                    errorDescription = authResponse.errorDescription;
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if (completed) {
                hideProgressBar();
            }
            return completed || super.shouldOverrideUrlLoading(view, url);
        }
    }

    private class Chrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.d("WebChromeClient", "progress = " + newProgress);
            if (newProgress == 100) {
                hideProgressBar();
            } else {
                showProgressBar();
            }
        }
    }

    private void getPermanentToken(String tempToken) {
        String getTokenUrl = new Token.Request(tempToken, YMCApplication.APP_ID,YMCApplication.REDIRECT_URI)
                .requestUrl(YMCApplication.apiClient.getHostsProvider());
        OAuth2Session session = new OAuth2Session(YMCApplication.apiClient);
        try {
            session.enqueue(new Token.Request(tempToken, YMCApplication.APP_ID, YMCApplication.REDIRECT_URI), new ResponseReady<Token>() {
                @Override
                protected void failure(Exception exception) {
                    Log.i("Token fail: ", exception.getLocalizedMessage());
                    errorDescription = exception.getMessage();
                }

                @Override
                protected void response(Token response) {
                    Log.i("Token success: ", response.toString());
                    Toast.makeText(AuthActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    if (response.error == null) {
                        saveToken(response.accessToken);
                    } else {
                        errorDescription = response.error.name();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            errorDescription = e.getMessage();
        }
    }

    private void saveToken(String token) {
        getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0)
                .edit().putString(YMCApplication.PREF_AUTH_TOKEN, token).apply();
    }

}
