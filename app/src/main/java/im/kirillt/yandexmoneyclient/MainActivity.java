package im.kirillt.yandexmoneyclient;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.events.result.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.events.download.DownloadAllEvent;
import im.kirillt.yandexmoneyclient.events.result.SuccessAccountInfoEvent;
import im.kirillt.yandexmoneyclient.events.result.SuccessEvent;
import im.kirillt.yandexmoneyclient.fragments.AboutFragment;
import im.kirillt.yandexmoneyclient.fragments.PaymentFragment;
import im.kirillt.yandexmoneyclient.fragments.SettingsFragment;
import im.kirillt.yandexmoneyclient.fragments.UpdatableFragment;

public class MainActivity extends LockableActivity {

    private DrawerLayout drawerLayout;
    private MenuItem curMenuItemId = null;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initDrawerLayout();
        currentFragment = UpdatableFragment.newInstance(UpdatableFragment.MAIN_FRAGMENT);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main_fragment, currentFragment).commit();
    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
            return ;
        }
        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
        //Fragment fragment;
        switch(menuItem.getItemId()) {
            case R.id.drawer_home:
                currentFragment = UpdatableFragment.newInstance(UpdatableFragment.MAIN_FRAGMENT);
                break;
            case R.id.drawer_pay:
                currentFragment = PaymentFragment.newInstance();
                break;
            case R.id.drawer_history:
                currentFragment = UpdatableFragment.newInstance(UpdatableFragment.HISTORY_FRAGMENT);
                break;
            case R.id.drawer_settings:
                currentFragment = SettingsFragment.newInstance();
                break;
            case R.id.drawer_about:
                currentFragment = AboutFragment.newInstance();
                break;
            default:
                currentFragment = UpdatableFragment.newInstance(UpdatableFragment.MAIN_FRAGMENT);

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_main_fragment, currentFragment).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEventMainThread(AnyErrorEvent errorEvent) {
        Toast.makeText(this, errorEvent.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onEventAsync(DownloadAllEvent event) {
        event.download();
    }

    public void onEventMainThread(SuccessEvent event) {
        Toast.makeText(this, "downloaded: "+event.getClass().getName(), Toast.LENGTH_SHORT).show();
        if (!YMCApplication.isDownloading()) {
            stopRefreshingWidget();
        }
    }

    private void stopRefreshingWidget() {
        //TODO: stop widget download circle
    }
}
