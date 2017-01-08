package im.kirillt.yandexmoneyclient

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import de.greenrobot.event.EventBus
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent
import im.kirillt.yandexmoneyclient.events.IncomingTransferProcessResultEvent
import im.kirillt.yandexmoneyclient.events.download.DownloadAccountInfoEvent
import im.kirillt.yandexmoneyclient.events.download.DownloadAllEvent
import im.kirillt.yandexmoneyclient.events.download.SuccessAccountInfoEvent
import im.kirillt.yandexmoneyclient.fragments.HistoryFragment
import im.kirillt.yandexmoneyclient.fragments.SettingsFragment
import im.kirillt.yandexmoneyclient.provider.account.AccountColumns
import im.kirillt.yandexmoneyclient.utils.Converters
import org.jetbrains.anko.toast
import java.io.IOException
import java.math.BigDecimal
import java.text.NumberFormat


class BottomNavActivity : BaseActivity() {

    private var currentFragment: Fragment? = null
    private var historyFragment: HistoryFragment? = null
    private var settingsFragment: SettingsFragment? = null
    private var accountInfoContainer: LinearLayout? = null
    private var userName: TextView? = null
    private var account: TextView? = null
    private var balance: TextView? = null
    private var reserved: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("BottomNavActivity", "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        /*val fab = findViewById(R.id.activity_bot_fab) as FloatingActionButton
        fab.setOnClickListener {
            PaymentActivity.startActivity(YMCApplication.getAppContext())
        }
        accountInfoContainer = findViewById(R.id.account_info_container) as LinearLayout
        val toolbar = findViewById(R.id.activity_main_toolbar) as Toolbar
        setSupportActionBar(toolbar)

        (accountInfoContainer!!.findViewById(R.id.account_info_user_name) as TextView).text = "line1"
        (accountInfoContainer!!.findViewById(R.id.account_info_account_number) as TextView).text = "line2"
        (accountInfoContainer!!.findViewById(R.id.account_info_balance) as TextView).text = "line3"
        (accountInfoContainer!!.findViewById(R.id.account_info_reserved) as TextView).text = "line4"
        */

        settingsFragment = SettingsFragment.newInstance()
        historyFragment = HistoryFragment.newInstance()
        currentFragment = historyFragment

        userName = findViewById(R.id.account_info_user_name) as TextView
        account = findViewById(R.id.account_info_account_number) as TextView
        balance = findViewById(R.id.account_info_balance) as TextView
        reserved = findViewById(R.id.account_info_reserved) as TextView

        supportFragmentManager.beginTransaction().add(R.id.activity_bot_fragment, currentFragment).commit()


        val bottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_history -> {
                    //"history"
                    if (currentFragment != historyFragment) {
                        currentFragment = historyFragment
                        supportFragmentManager.beginTransaction().replace(R.id.activity_bot_fragment, currentFragment).commit()
                    }
                }
                R.id.action_favorites -> {
                    //"favorites"
                    toast("nothing here yet")
                }
                R.id.action_settings -> {
                    //"settings"
                    if (currentFragment != settingsFragment) {
                        currentFragment = settingsFragment
                        supportFragmentManager.beginTransaction().replace(R.id.activity_bot_fragment, currentFragment).commit()
                    }
                }
                else -> {
                }
            }
            true
        }
    }

    public override fun onStart() {
        Log.i("MainActivity", "onStart()")
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        Log.i("MainActivity", "onStop()")
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    fun onEventMainThread(errorEvent: AnyErrorEvent) {
        if (errorEvent.exception is IOException) {
            toast(R.string.net_error)
        }
    }

    fun onEventAsync(event: DownloadAllEvent) {
        event.download()
    }

    fun onEventAsync(event: SuccessAccountInfoEvent) {
        runOnUiThread {
            val accountInfo = event.response
            account!!.text = accountInfo.account
            balance!!.text = String.format("%s: %s %s",
                    getString(R.string.balance),
                    NumberFormat.getInstance().format(accountInfo.balance),
                    Converters.fancyCurrencyName(accountInfo.currency)
            )
            reserved!!.text = String.format("%s: %s %s",
                    getString(R.string.reserved),
                    NumberFormat.getInstance().format(accountInfo.balanceDetails.hold ?: 0), // wtf nulls in my Kotlin
                    Converters.fancyCurrencyName(accountInfo.currency)
            )
        }
    }

    fun onEventMainThread(event: IncomingTransferProcessResultEvent) {
        toast(event.result)
    }

}
