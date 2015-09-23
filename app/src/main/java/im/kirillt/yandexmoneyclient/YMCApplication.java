package im.kirillt.yandexmoneyclient;

import android.app.Application;

import com.yandex.money.api.net.DefaultApiClient;
import com.yandex.money.api.net.OAuth2Session;

/**
 * Created by kirill on 15.09.15.
 */
public class YMCApplication extends Application {
    private static YMCApplication singleton;

    public static final String PREFERENCES_STORAGE = "YMCPrefs";
    public static final String PREF_AUTH_TOKEN = "auth_token";
    public static final String APP_ID = "DDD264D223C195815CB984B24B74220E9A551C81F9163AAD41A55EDA98C03E98";
    public static final String REDIRECT_URI = "client://authresult";
    public static final DefaultApiMobileClientWrapper apiClient = new DefaultApiMobileClientWrapper(new DefaultApiClient(APP_ID, true, "Android"));
    public static OAuth2Session auth2Session = new OAuth2Session(apiClient);

    private static volatile boolean accountDownloadedNow = false;
    private static volatile boolean historyDownloadedNow = false;

    public static boolean lockOnResume = false;
    public YMCApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

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
}
