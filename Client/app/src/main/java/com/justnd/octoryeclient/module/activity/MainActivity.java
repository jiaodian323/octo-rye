package com.justnd.octoryeclient.module.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.module.base.RxMediaBaseActivity;
import com.justnd.octoryeclient.module.dicovery.DiscoveryFragment;
import com.justnd.octoryeclient.module.home.HomeContainerFragment;
import com.justnd.octoryeclient.module.user.LoginModeFragment;
import com.justnd.octoryeclient.module.user.MeFragment;
import com.justnd.octoryeclient.music.MediaBrowserProvider;
import com.justnd.octoryeclient.music.MusicService;
import com.justnd.octoryeclient.utils.ConstantUtil;
import com.justnd.octoryeclient.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends RxMediaBaseActivity {
    public static final String DEBUG_MAIN_TAG = "MainActivity";

//    @BindView(R.id.toolbar_main)
//    Toolbar mToolbarMain;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;

    private HomeContainerFragment mHomeContainerFragment;
    private Fragment currentFragment;

    /**
    * @Fields: 是否已登录标志位 0:未登录；1：已登录
    */
    private int mLoginStatus = 0;

    public static final String HOME_TAG = "HomeContainerFragment";
    public static final String DISCOVER_TAG = "DiscoverFragment";
    public static final String LOGIN_MODE_TAG = "LoginModeFragment";
    public static final String ME_TAG = "MeFragment";

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
                        replaceFragment(HOME_TAG);
                        break;
                    case R.id.navigation_discovery:
                        replaceFragment(DISCOVER_TAG);
                        break;
                    case R.id.navigation_my:
                        if (mLoginStatus == 1)
                            // 已登录
                            replaceFragment(ME_TAG);
                        else
                            // 未登录
                            replaceFragment(LOGIN_MODE_TAG);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void initToolBar() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
	protected void onNewIntent(android.content.Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        openMeFragment();
	}

    private void initFragments() {
        Log.i(DEBUG_MAIN_TAG, "initFragments()--");
        mHomeContainerFragment = HomeContainerFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mHomeContainerFragment, HOME_TAG)
                .addToBackStack(null)
                .show(mHomeContainerFragment)
                .commit();

        currentFragment = mHomeContainerFragment;
    }

    public void replaceFragment(String tag) {
        if (currentFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
        }

        currentFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (currentFragment == null) {
            switch (tag) {
                case HOME_TAG:
                    currentFragment = HomeContainerFragment.newInstance();
                    break;
                case DISCOVER_TAG:
                    currentFragment = DiscoveryFragment.newInstance();
                    break;
                case LOGIN_MODE_TAG:
                    currentFragment = LoginModeFragment.newInstance();
                    break;
                case ME_TAG:
                    currentFragment = MeFragment.newInstance();
                    break;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    currentFragment, tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(currentFragment).commit();
        }
    }

	private void openMeFragment() {
        int tagValue = getIntent().getIntExtra(ME_TAG, 0);
        if (tagValue == 2) {
            // 打开个人中心
            replaceFragment(ME_TAG);
        }

        mLoginStatus = 1;      // 登录状态标志位置为已登录
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
