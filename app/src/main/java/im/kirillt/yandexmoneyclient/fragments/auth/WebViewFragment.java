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
//import com.yandex.money.api.net.OAuth2Session;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.AuthActivity;
import im.kirillt.yandexmoneyclient.R;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.events.WebAuthResultEvent;
import im.kirillt.yandexmoneyclient.model.util.Objects;
//import im.kirillt.yandexmoneyclient.utils.ResponseReady;

public class WebViewFragment extends Fragment {

    private static final String KEY_URL = "url";
    private static final String KEY_POST_DATA = "postData";

    private String login = null;
    private WebView webView;

    public static WebViewFragment newInstance(String url, byte[] postData) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(KEY_URL, url);
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
        Log.i("WebViewFragment", "onActivityCreated() thread.id= "+Thread.currentThread().getId());
        loadPage(getArguments().getString(KEY_URL),
                getArguments().getByteArray(KEY_POST_DATA), webView);
    }

    private void loadPage(String url, byte[] postData, WebView view) {
        Log.i("loadPage", "load");
        view.setVisibility(View.VISIBLE);
        showProgressBar();
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
            if (url.startsWith("client")) {
                AuthorizationCodeResponse response;
                try {
                    response = AuthorizationCodeResponse.parse(url);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }

                if (response.error == null) {
                    // try to get OAuth2 access token
                    new Thread(() -> {
                        try {
                            Token token = YMCApplication.client.execute(new Token.Request(response.code, YMCApplication.client.getClientId(), YMCApplication.REDIRECT_URI, YMCApplication.APP_ID));
                            returnResult(token.accessToken, token.error == null ? null : token.error.name());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else {
                    throw new RuntimeException("Could not authorize");
                }
                return true;
            }
            return false;
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

    private String getLoginFromCookies(String cookies) {
        final String key = "yandex_login=";
        int begin = cookies.indexOf(key);
        if (begin == -1) {
            return null;
        }
        begin +=key.length();
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
        Log.i("WebViewFragment", "hideProgressBar thread.id= "+Thread.currentThread().getId());
        AuthActivity activity = (AuthActivity)getActivity();
        if (activity!= null) {
            activity.hideProgressBar();
        }
    }

    private void showProgressBar() {
        AuthActivity activity = (AuthActivity)getActivity();
        if (activity != null) {
            activity.showProgressBar();
        }
    }

    private void returnResult(String token, String errorDescription) {
        Log.i("WebViewFragment", "returnResult thread.id= " + Thread.currentThread().getId());
        EventBus.getDefault().post(new WebAuthResultEvent(login, token, errorDescription));
    }
}
