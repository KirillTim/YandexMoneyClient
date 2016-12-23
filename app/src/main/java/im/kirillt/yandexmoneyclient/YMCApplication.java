package im.kirillt.yandexmoneyclient;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;

import com.yandex.money.api.authorization.AuthorizationData;
import com.yandex.money.api.authorization.AuthorizationParameters;
import com.yandex.money.api.model.Scope;
import com.yandex.money.api.net.clients.ApiClient;
import com.yandex.money.api.net.clients.DefaultApiClient;

//import com.yandex.money.api.methods.Token;
//import com.yandex.money.api.model.Scope;
//import com.yandex.money.api.net. //DefaultApiClient;
//import com.yandex.money.api.net.OAuth2Authorization;
//import com.yandex.money.api.net.OAuth2Session;

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
    public static final ApiClient client = new DefaultApiClient.Builder().setClientId(APP_ID).create();
//    public static final DefaultApiMobileClientWrapper apiClient = new DefaultApiMobileClientWrapper(new DefaultApiClient(APP_ID, true, "Android"));
//    public static final DefaultApiClient apiClient = new DefaultApiClient(APP_ID, true, "Android");
    public static final AuthorizationParameters parameters = new AuthorizationParameters.Builder()
            .addScope(Scope.ACCOUNT_INFO)
            .addScope(Scope.INCOMING_TRANSFERS)
            .addScope(Scope.OPERATION_DETAILS)
            .addScope(Scope.OPERATION_HISTORY)
            .addScope(Scope.PAYMENT_P2P)
            .setRedirectUri(REDIRECT_URI)
            .create();

    public static final AuthorizationData data = client.createAuthorizationData(parameters);

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
        super.onCreate();
        Log.i("YMCApplication", "onCreate()");
        String tokenStr = getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).getString(YMCApplication.PREF_AUTH_TOKEN, "");
        if (TextUtils.isEmpty(tokenStr)) {
            //askLock = false;
            askAuth = true;
        } else {
            setToken(tokenStr);
            //askLock = true;
            //askAuth = false;
        }
        singleton = this;
        appContext = getApplicationContext();
    }

    public boolean validateCode(String code) {
        String co = getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).getString(PREF_LOCK_CODE, "");
        return co.equals(Encryption.passMD5(code).toString());
    }

    public static void setToken(String token) {
        client.setAccessToken(token);
    }

    public static void deleteToken(Activity activity) {
        activity.getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).edit()
                .remove(YMCApplication.PREF_AUTH_TOKEN)
                .remove(YMCApplication.PREF_LOCK_CODE).apply();
    }
    public static final String FROM_INNER = "fromInner";
    public static boolean askLock = false;
    public static boolean askAuth = false;

}
