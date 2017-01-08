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
import im.kirillt.yandexmoneyclient.events.download.DownloadAllEvent
import im.kirillt.yandexmoneyclient.fragments.HistoryFragment
import im.kirillt.yandexmoneyclient.fragments.SettingsFragment
import im.kirillt.yandexmoneyclient.provider.account.AccountColumns
import org.jetbrains.anko.toast
import java.io.IOException


class BottomNavActivity : BaseActivity() {

    private var currentFragment: Fragment? = null
    private var historyFragment: HistoryFragment? = null
    private var settingsFragment: SettingsFragment? = null
    private var accountInfoContainer: LinearLayout? = null

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
        (accountInfoContainer!!.findViewById(R.id.account_info_reserved) as TextView).text = "line4"*/

        settingsFragment = SettingsFragment.newInstance()
        historyFragment = HistoryFragment.newInstance()
        currentFragment = historyFragment

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

    fun onEventMainThread(event: IncomingTransferProcessResultEvent) {
        toast(event.result)
    }

}
