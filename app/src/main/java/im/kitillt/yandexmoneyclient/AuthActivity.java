package im.kitillt.yandexmoneyclient;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import im.kitillt.yandexmoneyclient.utils.Bundles;

public class AuthActivity extends AppCompatActivity {

    public static final String KEY_URL = "uri";
    public static final String KEY_POST_DATA = "postData";

    private WebView webView;

    private String url;
    private Map<String, String> postDataMap = null;
    private byte[] postDataBytes = null;

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
        url = intent.getStringExtra(KEY_URL);
        postDataBytes = intent.getByteArrayExtra(KEY_POST_DATA);
        webView = initWebView();
        loadPage(url, postDataBytes);
    }


    @SuppressLint("SetJavaScriptEnabled")
    private WebView initWebView() {
        WebView rv = (WebView)findViewById(R.id.auth_webview);
        rv.setWebViewClient(new Client());
        rv.setWebChromeClient(new Chrome());
        rv.getSettings().setJavaScriptEnabled(true);
        return rv;
    }

    private void loadPage(String url, byte[] postData) {
        Log.i("loadPage", "load");
        showWebView();
        webView.postUrl(url, postData);
    }

    public void showProgressBar() {
        setProgressBarIndeterminateVisibility(true);
    }

    public void hideProgressBar() {
        setProgressBarIndeterminateVisibility(false);
    }

    private void showWebView() {
        webView.setVisibility(View.VISIBLE);
    }

    private void hideWebView() {
        webView.setVisibility(View.GONE);
    }


    private class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("WebViewClient", "loading " + url);
            boolean completed = false;
            if (url.contains("?code=")) {
                Uri uri = Uri.parse(url);
                String authCode = uri.getQueryParameter("code");
                completed = true;
                hideWebView();
                Toast.makeText(AuthActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (url.contains("error")) {
                completed = true;
                //showError(Error.AUTHORIZATION_REJECT, null);
                Toast.makeText(AuthActivity.this, "Error: "+url, Toast.LENGTH_SHORT).show();
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
}
