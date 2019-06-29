package com.justnd.octoryeclient.module.base;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.justnd.octoryeclient.module.musicinterface.QuickControlsFragment;
import com.justnd.octoryeclient.music.MediaBrowserProvider;
import com.justnd.octoryeclient.music.MusicService;
import com.justnd.octoryeclient.utils.ConstantUtil;

import java.util.List;

/**
 * @author Justiniano
 * @Description:
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/6/13 0013 下午 9:37
 */
public abstract class RxMediaBaseActivity extends RxBaseActivity implements MediaBrowserProvider {

    private MediaBrowserCompat mMediaBrowser;
//    private String mPlayingMusicId;
//    private MusicService.MusicServiceBinder mMusicServiceBinder;

//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mMusicServiceBinder = (MusicService.MusicServiceBinder) service;
//        }
//    };

//    public void setPlayingMusicId(String musicId) {
//        this.mPlayingMusicId = musicId;
//    }
//
//    public String getPlayingMusicId() {
//        return mPlayingMusicId;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ConstantUtil.TYPE_MUSIC, "Activity.onCreate()----------");
        super.onCreate(savedInstanceState);

        mMediaBrowser = new MediaBrowserCompat(this, new ComponentName(this, MusicService.class),
                mBrowserConnectionCallback, null);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
    }

    @Override
    public void initToolBar() {}

    @Override
    protected void onStart() {
        Log.i(ConstantUtil.TYPE_MUSIC, "Activity.onStart()----------");
        super.onStart();

        if (!mMediaBrowser.isConnected() && mMediaBrowser!= null) {
            mMediaBrowser.connect();
        }
    }

    @Override
    protected void onStop() {
        Log.i(ConstantUtil.TYPE_MUSIC, "Activity.onStop()----------");
        super.onStop();
        MediaControllerCompat controllerCompat = MediaControllerCompat.getMediaController(this);
        if (controllerCompat != null) {
            controllerCompat.unregisterCallback(mMediaControllerCallback);
        }
        mMediaBrowser.disconnect();
    }

    @Override
    public MediaBrowserCompat getMediaBrowser() {
        return mMediaBrowser;
    }

    /**
     * @param token
     * @throws RemoteException
     */
    private void connectToSession(MediaSessionCompat.Token token) throws RemoteException {
        MediaControllerCompat mediaController = new MediaControllerCompat(this, token);
        MediaControllerCompat.setMediaController(this, mediaController);
        mediaController.registerCallback(mMediaControllerCallback);

        onMediaControllerConnected();
    }

    protected void onMediaControllerConnected() {
        // empty implementation, can be overridden by clients.
    }

    private MediaBrowserCompat.ConnectionCallback mBrowserConnectionCallback =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    Log.i(ConstantUtil.TYPE_MUSIC, "RxMediaBaseActivity" +
                            ".mBrowserConnectionCallback.onConnected()---------连接成功");
                    String mediaId = mMediaBrowser.getRoot();
                    Log.i(ConstantUtil.TYPE_MUSIC, "In BaseActivity, mMediaId=" + mediaId);
                    mMediaBrowser.unsubscribe(mediaId);
                    mMediaBrowser.subscribe(mediaId, mBrowserSubscriptionCallback);

//                    try {
//                        Log.i(ConstantUtil.TYPE_MUSIC, "RxMediaBaseActivity.使用SessionToken创建Controller");
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
//                    StringBuilder musicStr = new StringBuilder();
//                    for (MediaBrowserCompat.MediaItem item : children) {
//                        musicStr.append("ID:" + item.getMediaId() + " ");
//                        musicStr.append("Title:" + item.getDescription().getTitle() + "\n");

//                        children.get(0))
//                        currentMediaId = item.getMediaId();
//                    }
//                    currentMediaId = children.get(0).getMediaId();
                }
            };

    private final MediaControllerCompat.Callback mMediaControllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onPlaybackStateChanged(PlaybackStateCompat state) {
                    Log.i(ConstantUtil.TYPE_MUSIC, "MainActivity.controlCallback" +
                            ".onPlaybackStateChanged()---------");
                    super.onPlaybackStateChanged(state);

                    switch (state.getState()) {
                        case PlaybackStateCompat.STATE_NONE:
                            Log.i(ConstantUtil.TYPE_MUSIC, "BaseActivity.MediaController.Callback, state:" + state.getState());
                            break;
                        case PlaybackStateCompat.STATE_PLAYING:
                            Log.i(ConstantUtil.TYPE_MUSIC, "BaseActivity.MediaController.Callback, state:" + state.getState());
                            break;
                        case PlaybackStateCompat.STATE_PAUSED:
                            Log.i(ConstantUtil.TYPE_MUSIC, "BaseActivity.MediaController.Callback, state:" + state.getState());
                            break;
                        default:
                    }
                }

                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    Log.i(ConstantUtil.TYPE_MUSIC, "MainActivity.controlCallback" +
                            ".onMetadataChanged()---------");
                    super.onMetadataChanged(metadata);
                }
            };
}
