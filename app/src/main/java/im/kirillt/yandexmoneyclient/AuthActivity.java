package im.kirillt.yandexmoneyclient;

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
import android.webkit.CookieManager;
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

import im.kirillt.yandexmoneyclient.utils.ResponseReady;

public class AuthActivity extends AppCompatActivity {

    public static final String KEY_URL = "uri";
    public static final String KEY_POST_DATA = "postData";
    public static final String RESULT_SUCCESS = "success";
    public static final String RESULT_TOKEN = "token";
    public static final String RESULT_LOGIN = "login";
    public static final String RESULT_ERROR_MSG = "errorMessage";

    private WebView webView;

    private String login = null;

    private String url;
    private byte[] postDataBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("AuthActivity", "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

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

        Intent intent = getIntent();
        url = intent.getStringExtra(KEY_URL);
        postDataBytes = intent.getByteArrayExtra(KEY_POST_DATA);
        webView = (WebView)findViewById(R.id.auth_webview);
        authorization(url, postDataBytes, webView);


    }

    private void loadPage(String url, byte[] postData, WebView view) {
        Log.i("loadPage", "load");
        showWebView(view);
        view.postUrl(url, postData);
    }

    private void showWebView(WebView view) {
        view.setVisibility(View.VISIBLE);
    }

    private void hideWebView() {
        webView.setVisibility(View.GONE);
    }

    private class Chrome extends WebChromeClient {
        @Override
            public void onProgressChanged(WebView view, int newProgress) {
            Log.d("WebChromeClient", "progress = " + newProgress);
            if (newProgress == 100) {
                //TODO add
            } else {
                //TODO add
            }
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
                if (authResponse.code != null) {
                   getPermanentToken(authResponse.code);
                    completed = true;
                } else if (authResponse.error != null) {
                    completed = true;
                    //Toast.makeText(AuthActivity.this, "Error: "+errorDescription, Toast.LENGTH_SHORT).show();
                    returnResult(null, authResponse.errorDescription);
                }
            } catch (URISyntaxException e) {
                completed = true;
                e.printStackTrace();
                returnResult(null, "Unknown error");
            }
            if (completed) {
                //TODO add smth
            }
            return completed || super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String cookies = CookieManager.getInstance().getCookie(url);
            if (cookies != null && login == null) {
                login = getLoginFromCookies(cookies);
                if (login != null) {
                    Log.i("login", login);
                }
            }
        }
    }

    private void getPermanentToken(String tempToken) {
        OAuth2Session session = new OAuth2Session(YMCApplication.apiClient);
        try {
            session.enqueue(new Token.Request(tempToken, YMCApplication.APP_ID, YMCApplication.REDIRECT_URI), new ResponseReady<Token>() {
                @Override
                protected void failure(Exception exception) {
                    Log.i("Token fail: ", exception.getLocalizedMessage());
                    returnResult(null, exception.getMessage());
                }

                @Override
                protected void response(Token response) {
                    Log.i("Token success: ", response.toString());
                    //Toast.makeText(AuthActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    returnResult(response.accessToken, response.error == null ? null : response.error.name());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            returnResult(null, e.getMessage());
        }
    }

    private void returnResult(String token, String errorDescription) {
        Intent intent = new Intent();
        if (TextUtils.isEmpty(errorDescription) && !TextUtils.isEmpty(token)) {
            intent.putExtra(RESULT_SUCCESS, true);
            intent.putExtra(RESULT_TOKEN, token);
            intent.putExtra(RESULT_LOGIN, login == null ? "" : login);
        } else {
            intent.putExtra(RESULT_SUCCESS, false);
            intent.putExtra(RESULT_ERROR_MSG, errorDescription);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    private String getLoginFromCookies(String cookies) {
        final String key = "yandex_login=";
        int begin = cookies.indexOf(key)+key.length();
        if (begin == -1) {
            return null;
        }
        int end = cookies.indexOf(";", begin);
        if (end == -1) {
            return null;
        }
        return cookies.substring(begin, end);
    }
}
