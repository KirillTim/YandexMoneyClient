package im.kirillt.yandexmoneyclient.fragments.auth;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yandex.money.api.methods.Token;
import com.yandex.money.api.net.AuthorizationCodeResponse;
import com.yandex.money.api.net.OAuth2Session;

import java.io.IOException;
import java.net.URISyntaxException;

import im.kirillt.yandexmoneyclient.AuthActivity;
import im.kirillt.yandexmoneyclient.R;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.utils.ResponseReady;

public class WebViewFragment extends Fragment {

    private static final String KEY_URL = "uri";
    private static final String KEY_POST_DATA = "postData";

    private String login = null;
    private WebView webView;

    public static WebViewFragment newInstance(String uri, byte[] postData) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(KEY_URL, uri);
        args.putByteArray(KEY_POST_DATA, postData);
        fragment.setArguments(args);
        return fragment;
    }

    public WebViewFragment() {
    }

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        webView = (WebView)inflater.inflate(R.layout.fragment_web_view, container, false);
        webView.setWebChromeClient(new Chrome());
        webView.setWebViewClient(new GetTempTokenClient());
        webView.getSettings().setJavaScriptEnabled(true);
        return webView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadPage(getArguments().getString(KEY_URL),
                getArguments().getByteArray(KEY_POST_DATA), webView);
    }

    private void loadPage(String url, byte[] postData, WebView view) {
        Log.i("loadPage", "load");
        view.setVisibility(View.VISIBLE);
        view.postUrl(url, postData);
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


    private class GetTempTokenClient extends WebViewClient {
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
                hideProgressBar();
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

    private void hideProgressBar() {
        ((AuthActivity)getActivity()).hideProgressBar();
    }

    private void showProgressBar() {
        ((AuthActivity)getActivity()).showProgressBar();
    }

    private void returnResult(String token, String errorDescription) {
        ((AuthActivity)getActivity()).getWebAuth(token, login, errorDescription);
    }
}
