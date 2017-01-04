package im.kirillt.yandexmoneyclient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.events.IncomingTransferProcessResultEvent;
import im.kirillt.yandexmoneyclient.events.download.DownloadAllEvent;
import im.kirillt.yandexmoneyclient.fragments.AboutFragment;
import im.kirillt.yandexmoneyclient.fragments.HistoryFragment;
import im.kirillt.yandexmoneyclient.fragments.MainFragment;
//import im.kirillt.yandexmoneyclient.fragments.SettingsFragment;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private MenuItem curMenuItemId = null;
    private Menu menu = null;
    private Fragment currentFragment;

    private LinearLayout toolBarInnerView;
    private FloatingActionButton mainFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity", "onCreate()");
        super.onCreate(savedInstanceState);
       /* if (!YMCApplication.auth2Session.isAuthorized()) {
            Toast.makeText(this, "You are not authorized", Toast.LENGTH_SHORT).show();
            YMCApplication.deleteToken(this);
            finish();
        }*/
        setContentView(R.layout.activity_main);
        mainFab = (FloatingActionButton) findViewById(R.id.activity_main_fab);
        initToolbar();
        initDrawerLayout();
        currentFragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main_fragment, currentFragment).commit();
    }


    private void initToolbar() {
        toolBarInnerView = (LinearLayout) findViewById(R.id.account_info_container);
        toolBarInnerView.setVisibility(View.GONE);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(menuItem -> {
            selectDrawerItem(menuItem);
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            setTitle(menuItem.getTitle());
            return true;
        });
        menu = view.getMenu();
        curMenuItemId = menu.getItem(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectDrawerItem(MenuItem menuItem) {
        if (menuItem == curMenuItemId) {
            return;
        }
        curMenuItemId = menuItem;
        switch (menuItem.getItemId()) {
            case R.id.drawer_home:
                currentFragment = MainFragment.newInstance();
                break;
            case R.id.drawer_pay:
                curMenuItemId = menu.getItem(0);
                PaymentActivity.startActivity(MainActivity.this);
                break;
            case R.id.drawer_history:
                currentFragment = HistoryFragment.newInstance();
                break;
            case R.id.drawer_settings:
//                currentFragment = SettingsFragment.newInstance();
                break;
            case R.id.drawer_about:
                currentFragment = AboutFragment.newInstance();
                break;
            default:
                currentFragment = MainFragment.newInstance();

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_main_fragment, currentFragment).commit();
    }

    @Override
    public void onStart() {
        Log.i("MainActivity", "onStart()");
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        Log.i("MainActivity", "onStop()");
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEventMainThread(AnyErrorEvent errorEvent) {
        if (errorEvent.getException() instanceof IOException) {
            Toast.makeText(this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
        }
    }

    public void onEventAsync(DownloadAllEvent event) {
        event.download();
    }

    public void onEventMainThread(IncomingTransferProcessResultEvent event) {
        Toast.makeText(this, event.getResult(), Toast.LENGTH_SHORT).show();
    }

    public LinearLayout getToolBarInnerView() {
        return toolBarInnerView;
    }

    public FloatingActionButton getFab() {
        return mainFab;
    }


}
