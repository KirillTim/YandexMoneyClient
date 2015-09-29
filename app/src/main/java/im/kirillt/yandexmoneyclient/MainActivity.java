package im.kirillt.yandexmoneyclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.events.download.DownloadAllEvent;
import im.kirillt.yandexmoneyclient.fragments.AboutFragment;
import im.kirillt.yandexmoneyclient.fragments.SettingsFragment;
import im.kirillt.yandexmoneyclient.fragments.UpdatableFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private MenuItem curMenuItemId = null;

    private Fragment currentFragment;

    private LinearLayout toolBarInnerView;
    private FloatingActionButton mainFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity", "onCreate()");
        super.onCreate(savedInstanceState);
        if (!YMCApplication.auth2Session.isAuthorized()) {
            login();
        }
        setContentView(R.layout.activity_main);
        mainFab = (FloatingActionButton)findViewById(R.id.activity_main_fab);
        initToolbar();
        initDrawerLayout();
        currentFragment = UpdatableFragment.newInstance(UpdatableFragment.MAIN_FRAGMENT);
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
        curMenuItemId = view.getMenu().getItem(0);
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
        curMenuItemId = menuItem;
        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
        //Fragment fragment;
        switch(menuItem.getItemId()) {
            case R.id.drawer_home:
                currentFragment = UpdatableFragment.newInstance(UpdatableFragment.MAIN_FRAGMENT);
                break;
            case R.id.drawer_pay:
                PaymentActivity.startActivity(MainActivity.this);
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
        Toast.makeText(this, errorEvent.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onEventAsync(DownloadAllEvent event) {
        event.download();
    }



    public LinearLayout getToolBarInnerView() {
        return toolBarInnerView;
    }

    public FloatingActionButton getFab() {
        return mainFab;
    }

    private void login() {
        Intent intent = new Intent(MainActivity.this, AuthActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1) {
            return;
        }
        if (resultCode != RESULT_OK) {
            android.os.Process.killProcess(android.os.Process.myPid());
            finish();
        } else {
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        }
    }
}
