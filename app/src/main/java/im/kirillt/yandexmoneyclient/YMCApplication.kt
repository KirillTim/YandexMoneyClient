package im.kirillt.yandexmoneyclient

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.support.v4.util.Pair
import android.text.TextUtils
import android.util.Log

import com.yandex.money.api.authorization.AuthorizationData
import com.yandex.money.api.authorization.AuthorizationParameters
import com.yandex.money.api.model.Scope
import com.yandex.money.api.net.clients.ApiClient
import com.yandex.money.api.net.clients.DefaultApiClient

//import com.yandex.money.api.methods.Token;
//import com.yandex.money.api.model.Scope;
//import com.yandex.money.api.net. //DefaultApiClient;
//import com.yandex.money.api.net.OAuth2Authorization;
//import com.yandex.money.api.net.OAuth2Session;

/**
 * Created by kirill on 15.09.15.
 */
class YMCApplication : Application() {

    val instance: YMCApplication?
        get() = singleton

    override fun onCreate() {
        super.onCreate()
        Log.i("YMCApplication", "onCreate()")
        //String tokenStr = getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).getString(YMCApplication.PREF_AUTH_TOKEN, "");
        val tokenStr = "410011641845768.5AE1D0B5D1FC724B4EF13B91D43254E6E38809140A21A784EB78EDF8D7AD5FC7000484587B23ECD995CB2091D6F828CB93FCB5FC4CD02149D9321572B4167C3E55C91F4FE1F1FC9BCA44F515F3328F9E589479C27F3038CD6C766611ADF6CE11327ECCEAFDE6DAC27A38FDFAF7A29D5055C53B057AA9EA58DFE81019573E3477"
        if (TextUtils.isEmpty(tokenStr)) {
            //askLock = false;
            askAuth = true
        } else {
            setToken(tokenStr)
            //askLock = true;
            //askAuth = false;
        }
        singleton = this
        appContext = applicationContext
    }

    fun validateCode(code: String): Boolean {
        val co = getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).getString(PREF_LOCK_CODE, "")
        return co == Encryption.passMD5(code).toString()
    }

    companion object {
        private var singleton: YMCApplication? = null
        val PREFERENCES_STORAGE = "YMCPrefs"
        val PREF_AUTH_TOKEN = "auth_token"
        val PREF_LOCK_CODE = "lock_code"
        val APP_ID = "DDD264D223C195815CB984B24B74220E9A551C81F9163AAD41A55EDA98C03E98"
        val REDIRECT_URI = "client://authresult"
        val client: ApiClient = DefaultApiClient.Builder().setClientId(APP_ID).create()
        //    public static final DefaultApiMobileClientWrapper apiClient = new DefaultApiMobileClientWrapper(new DefaultApiClient(APP_ID, true, "Android"));
        //    public static final DefaultApiClient apiClient = new DefaultApiClient(APP_ID, true, "Android");
        val parameters = AuthorizationParameters.Builder()
                .addScope(Scope.ACCOUNT_INFO)
                .addScope(Scope.INCOMING_TRANSFERS)
                .addScope(Scope.OPERATION_DETAILS)
                .addScope(Scope.OPERATION_HISTORY)
                .addScope(Scope.PAYMENT_P2P)
                .setRedirectUri(REDIRECT_URI)
                .create()

        val data = client.createAuthorizationData(parameters)

        @Volatile private var accountDownloadedNow = false
        @Volatile private var historyDownloadedNow = false
        fun accountDownloadingStart() {
            accountDownloadedNow = true
        }

        fun accountDownloadingFinish() {
            accountDownloadedNow = false
        }

        fun historyDownloadingStart() {
            historyDownloadedNow = true
        }

        fun historyDownloadingFinish() {
            historyDownloadedNow = false
        }

        val isDownloading: Boolean
            get() = accountDownloadedNow || historyDownloadedNow

        var appContext: Context? = null
            private set

        var lockOnResume = false

        fun setToken(token: String) {
            client.setAccessToken(token)
        }

        fun deleteToken(activity: Activity) {
            activity.getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).edit()
                    .remove(YMCApplication.PREF_AUTH_TOKEN)
                    .remove(YMCApplication.PREF_LOCK_CODE).apply()
        }

        val FROM_INNER = "fromInner"
        var askLock = false
        var askAuth = false
    }

}
