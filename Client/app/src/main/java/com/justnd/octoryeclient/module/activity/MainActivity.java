package com.justnd.octoryeclient.module.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.dicovery.DiscoveryFragment;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.module.home.HomeRecommendedFragment;
import com.justnd.octoryeclient.module.user.LoginModeFragment;
import com.justnd.octoryeclient.utils.ToastUtil;

import butterknife.BindView;

public class MainActivity extends RxBaseActivity {
    public static final String DEBUG_MAIN_TAG = "MainActivity";

    @BindView(R.id.toolbar_main)
    Toolbar mToolbarMain;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;

    private HomeRecommendedFragment mRecommendedFragment;

    private Fragment currentFragment;

    private static final String HOME_TAG = "HomeFragment";
    private static final String DISCOVER_TAG = "DiscoverFragment";
    private static final String MY_TAG = "MyFragment";

    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_MAIN_TAG, "onCreate()--");
        super.onCreate(savedInstanceState);

        initFragments();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        Log.i(DEBUG_MAIN_TAG, "initViews()--");
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mToolbarMain.setVisibility(View.VISIBLE);
                        replaceFragment(HOME_TAG);
                        break;
                    case R.id.navigation_discovery:
                        mToolbarMain.setVisibility(View.VISIBLE);
                        replaceFragment(DISCOVER_TAG);
                        break;
                    case R.id.navigation_my:
                        mToolbarMain.setVisibility(View.GONE);
                        replaceFragment(MY_TAG);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void initToolBar() {
    }

    private void initFragments() {
        Log.i(DEBUG_MAIN_TAG, "initFragments()--");
        mRecommendedFragment = HomeRecommendedFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.home_fragment_container, mRecommendedFragment, HOME_TAG)
                .addToBackStack(null)
                .show(mRecommendedFragment)
                .commit();

        currentFragment = mRecommendedFragment;
    }

    public void replaceFragment(String tag) {
        if (currentFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
        }

        currentFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (currentFragment == null) {
            switch (tag) {
                case HOME_TAG:
                    currentFragment = HomeRecommendedFragment.newInstance();
                    break;
                case DISCOVER_TAG:
                    currentFragment = DiscoveryFragment.newInstance();
                    break;
                case MY_TAG:
                    currentFragment = LoginModeFragment.newInstance();
                    break;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_container,
                    currentFragment, tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(currentFragment).commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp();
        }

        return true;
    }

    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.ShortToast(getString(R.string.exit_tip_text));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
