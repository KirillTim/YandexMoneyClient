package im.kitillt.yandexmoneyclient;

import android.app.Application;

import com.yandex.money.api.net.DefaultApiClient;

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

    public YMCApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
