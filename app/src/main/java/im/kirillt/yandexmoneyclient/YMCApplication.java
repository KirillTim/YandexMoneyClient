package im.kirillt.yandexmoneyclient;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;

import com.yandex.money.api.methods.Token;
import com.yandex.money.api.model.Scope;
import com.yandex.money.api.net.DefaultApiClient;
import com.yandex.money.api.net.OAuth2Authorization;
import com.yandex.money.api.net.OAuth2Session;

/**
 * Created by kirill on 15.09.15.
 */
public class YMCApplication extends Application {
    private static YMCApplication singleton;

    public static final String PREFERENCES_STORAGE = "YMCPrefs";
    public static final String PREF_AUTH_TOKEN = "auth_token";
    public static final String PREF_LOCK_CODE = "lock_code";
    public static final String APP_ID = "DDD264D223C195815CB984B24B74220E9A551C81F9163AAD41A55EDA98C03E98";
    public static final String REDIRECT_URI = "client://authresult";
    public static final DefaultApiMobileClientWrapper apiClient = new DefaultApiMobileClientWrapper(new DefaultApiClient(APP_ID, true, "Android"));
    //public static final DefaultApiClient apiClient = new DefaultApiClient(APP_ID, true, "Android");
    public static final OAuth2Session auth2Session = new OAuth2Session(apiClient);

    public static final OAuth2Authorization authorization = new OAuth2Authorization(apiClient);
    public static final OAuth2Authorization.Params authParams = authorization.getAuthorizeParams()
            .addScope(Scope.ACCOUNT_INFO)
            .addScope(Scope.INCOMING_TRANSFERS)
            .addScope(Scope.OPERATION_DETAILS)
            .addScope(Scope.OPERATION_HISTORY)
            .addScope(Scope.PAYMENT_P2P)
            .setRedirectUri(REDIRECT_URI);

    private static volatile boolean accountDownloadedNow = false;
    private static volatile boolean historyDownloadedNow = false;
    public static void accountDownloadingStart() {
        accountDownloadedNow = true;
    }
    public static void accountDownloadingFinish() {
        accountDownloadedNow = false;
    }
    public static void historyDownloadingStart() {
        historyDownloadedNow = true;
    }
    public static void historyDownloadingFinish() {
        historyDownloadedNow = false;
    }
    public static boolean isDownloading() {
        return accountDownloadedNow || historyDownloadedNow;
    }

    private static Context appContext;
    public static Context getAppContext() {
        return appContext;
    }

    public static boolean lockOnResume = false;

    public YMCApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        Log.i("YMCApplication", "onCreate()");
        super.onCreate();
        auth2Session.setAccessToken(getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).getString(YMCApplication.PREF_AUTH_TOKEN, ""));
        singleton = this;
        appContext = getApplicationContext();
    }


}
