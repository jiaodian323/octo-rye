package com.justnd.octoryeclient.module.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.module.dicovery.DiscoveryFragment;
import com.justnd.octoryeclient.module.home.HomeContainerFragment;
import com.justnd.octoryeclient.module.user.LoginModeFragment;
import com.justnd.octoryeclient.module.user.MeFragment;
import com.justnd.octoryeclient.music.MediaBrowserProvider;
import com.justnd.octoryeclient.music.MusicService;
import com.justnd.octoryeclient.utils.ConstantUtil;
import com.justnd.octoryeclient.utils.DebugTagUtil;
import com.justnd.octoryeclient.utils.NavigationViewHelper;
import com.justnd.octoryeclient.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends RxBaseActivity implements MediaBrowserProvider {
    public static final String DEBUG_MAIN_TAG = "MainActivity";

    //    @BindView(R.id.toolbar_main)
//    Toolbar mToolbarMain;
    @BindView(R.id.navigation)
    public BottomNavigationView mNavigationView;
    @BindView(R.id.navigation_center_image)
    public ImageView mNavigationMusicEntry;

    private HomeContainerFragment mHomeContainerFragment;
    private Fragment mCurrentFragment;
    private MediaBrowserCompat mMediaBrowser;

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
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "MainActivity-------onCreate()");
        super.onCreate(savedInstanceState);

        mMediaBrowser = new MediaBrowserCompat(this, new ComponentName(this, MusicService.class),
                mBrowserConnectionCallback, null);

        initFragments();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Log.i(DEBUG_MAIN_TAG, "initViews()--");
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };
        int[] colors = new int[]{getResources().getColor(R.color.navigation_tint, null),
                getResources().getColor(R.color.colorPrimary, null)
        };

        ColorStateList csl = new ColorStateList(states, colors);
        mNavigationView.setItemTextColor(csl);
        mNavigationView.setItemIconTintList(csl);
        NavigationViewHelper.disableShiftMode(mNavigationView);
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

        mNavigationMusicEntry.setOnClickListener(v -> {
            PlaybackStateCompat state = MediaControllerCompat.getMediaController(MainActivity.this).getPlaybackState();
            if (state != null) {
                switch (state.getState()) {
                    case PlaybackStateCompat.STATE_NONE:
                        ToastUtil.ShortToast(getString(R.string.no_songs_to_show));
                        break;
                    default:
                        Intent intent = new Intent(MainActivity.this, FullScreenPlayerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.this.startActivity(intent);
                        overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_bottom_silent);
                        break;
                }
            }

        });
    }

    @Override
    public void initToolBar() {
    }

    @Override
    protected void onStart() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "MainActivity-------onStart()");
        super.onStart();
        if (mMediaBrowser != null) {
            mMediaBrowser.connect();
        }
    }

    @Override
    protected void onResume() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "MainActivity-------onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "MainActivity-------onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "MainActivity-------onStop()");
        super.onStop();
        if (mMediaBrowser != null) {
            mMediaBrowser.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "MainActivity-------onDestroy()");
        super.onDestroy();
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

        mCurrentFragment = mHomeContainerFragment;
    }

    public void replaceFragment(String tag) {
        if (mCurrentFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).commit();
        }

        mCurrentFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (mCurrentFragment == null) {
            switch (tag) {
                case HOME_TAG:
                    mCurrentFragment = HomeContainerFragment.newInstance();
                    break;
                case DISCOVER_TAG:
                    mCurrentFragment = DiscoveryFragment.newInstance();
                    break;
                case LOGIN_MODE_TAG:
                    mCurrentFragment = LoginModeFragment.newInstance();
                    break;
                case ME_TAG:
                    mCurrentFragment = MeFragment.newInstance();
                    break;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    mCurrentFragment, tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(mCurrentFragment).commit();
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
    public MediaBrowserCompat getMediaBrowser() {
        return mMediaBrowser;
    }

    private MediaBrowserCompat.ConnectionCallback mBrowserConnectionCallback =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "MainActivity" +
                            ".mBrowserConnectionCallback.onConnected()---------连接成功");
                    String mediaId = mMediaBrowser.getRoot();
                    Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "In MainActivity, mMediaId=" + mediaId);
                    mMediaBrowser.unsubscribe(mediaId);
                    mMediaBrowser.subscribe(mediaId, mBrowserSubscriptionCallback);

                    try {
                        connectToSession(mMediaBrowser.getSessionToken());
                    } catch (RemoteException e) {
                        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "MainActivity 根据Session建立控制器异常");
                    }

//                    try {
//                        Log.i(ConstantUtil.TYPE_MUSIC, "RxMediaBaseActivity
//                        .使用SessionToken创建Controller");
//                        connectToSession(mMediaBrowser.getSessionToken());
//                    } catch (RemoteException e) {
//                        Log.i(ConstantUtil.TYPE_MUSIC, "RxMediaBaseActivity.connectToSession()" +
//                                "---------远程调用异常");
//                        e.printStackTrace();
//                    }

//                    if (mMediaBrowser.isConnected()) {
//                        String mediaId = mMediaBrowser.getRoot();
//
//                        mMediaBrowser.unsubscribe(mediaId);
//                        mMediaBrowser.subscribe(mediaId, browserSubscriptionCallback);
//
//                        try {
//                            mController = new MediaControllerCompat(RxMediaBaseActivity.this,
//                                    mMediaBrowser.getSessionToken());
//                            mController.registerCallback(controlCallback);
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }

                @Override
                public void onConnectionFailed() {
                    Log.i(ConstantUtil.TYPE_MUSIC, "连接失败---------");
                }
            };

    private final MediaBrowserCompat.SubscriptionCallback mBrowserSubscriptionCallback =
            new MediaBrowserCompat.SubscriptionCallback() {
                @Override
                public void onChildrenLoaded(@NonNull String parentId,
                                             @NonNull List<MediaBrowserCompat.MediaItem>
                                                     children) {
                    Log.i(ConstantUtil.TYPE_MUSIC, "subscriptionCallback" +
                            ".onChildrenLoaded()---------订阅音乐库成功,执行订阅回调-----(未知线程)");
                    super.onChildrenLoaded(parentId, children);
                    StringBuilder musicStr = new StringBuilder();
                    for (MediaBrowserCompat.MediaItem item : children) {
                        musicStr.append("ID:" + item.getMediaId() + " ");
                        musicStr.append("Title:" + item.getDescription().getTitle() + "\n");
                    }

                    Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG,
                            "---children:" + musicStr.toString());
                }
            };

    private void connectToSession(MediaSessionCompat.Token token) throws RemoteException {
        MediaControllerCompat mediaController = new MediaControllerCompat(this, token);
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "根据token获取控制器，token：" + token.hashCode());
        MediaControllerCompat.setMediaController(this, mediaController);
    }
}
