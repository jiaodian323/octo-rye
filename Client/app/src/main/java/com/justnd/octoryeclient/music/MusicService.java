package com.justnd.octoryeclient.music;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.entity.recommond.RecommendInfo;
import com.justnd.octoryeclient.music.model.MusicProvider;
import com.justnd.octoryeclient.music.model.MusicProviderSource;
import com.justnd.octoryeclient.music.model.SimpleMusicProviderSource;
import com.justnd.octoryeclient.music.playback.LocalPlayback;
import com.justnd.octoryeclient.music.playback.PlaybackManager;
import com.justnd.octoryeclient.music.playback.QueueManager;
import com.justnd.octoryeclient.utils.ConstantUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Justiniano
 * @Description:
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/5/31 0031 下午 10:17
 */
public class MusicService extends MediaBrowserServiceCompat implements
        PlaybackManager.PlaybackServiceCallback {

    private static final String TAG = "MusicService";

    private static final String MEDIA_ID_ROOT = "RootMedia";

    public static final String ACTION_CMD = "com.justnd.newuniversalmusic.ACTION_CMD";
    public static final String ACTION_ADD_MUSIC_SOURCE = "com.justnd.octoryeclient" +
            ".ADD_MUSIC_SOURCE";

    public static final String CMD_NAME = "CMD_NAME";
    public static final String CMD_PAUSE = "CMD_PAUSE";

    private MusicProvider mMusicProvider;
    private PlaybackManager mPlaybackManager;
    private MusicProviderSource mProviderSource;

    private MediaSessionCompat mSession;

    private final DelayedStopHandler mDelayedStopHandler = new DelayedStopHandler(this);

    private static final int STOP_DELAY = 30000;

    private BroadcastReceiver mAddMusicSourceReceiver;
//    private MusicServiceBinder mBinder = new MusicServiceBinder();

    @Override
    public void onCreate() {
        Log.i(ConstantUtil.TYPE_MUSIC, "MusicService.onCreate()--------");
        super.onCreate();

        mProviderSource = new SimpleMusicProviderSource();
        mMusicProvider = new MusicProvider(mProviderSource);

//        mMusicProvider.retrieveMediaAsync(null /* Callback */);

        //QueueManager提供四个回调接口
        QueueManager queueManager = new QueueManager(mMusicProvider, getResources(),
                new QueueManager.MetadataUpdateListener() {
                    @Override
                    public void onMetadataChanged(MediaMetadataCompat metadata) {
                        mSession.setMetadata(metadata);
                    }

                    @Override
                    public void onMetadataRetrieveError() {
                        mPlaybackManager.updatePlaybackState(
                                getString(R.string.error_no_metadata));
                    }

                    @Override
                    public void onCurrentQueueIndexUpdated(int queueIndex) {
                        mPlaybackManager.handlePlayRequest();
                    }

                    @Override
                    public void onQueueUpdated(List<MediaSessionCompat.QueueItem> newQueue) {
                        mSession.setQueue(newQueue);
//                        mSession.setQueueTitle(title);
                    }
                });

        //Service初始化时传入PlaybackManager中的是LocalPlayback
        LocalPlayback playback = new LocalPlayback(this, mMusicProvider);
        mPlaybackManager = new PlaybackManager(this, getResources(), mMusicProvider, queueManager,
                playback);

        //开始一个新的MediaSession
        mSession = new MediaSessionCompat(this, "MusicService");
        setSessionToken(mSession.getSessionToken());
        mSession.setCallback(mPlaybackManager.getMediaSessionCallback());
        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

//        Context context = getApplicationContext();
//        Intent intent = new Intent(context, NowPlayingActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(context, 99 /*request code*/,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mSession.setSessionActivity(pi);


        mPlaybackManager.updatePlaybackState(null);
        registerAddMusicSourceReceiver();
    }

    @Override
    public int onStartCommand(Intent startIntent, int flags, int startId) {
        if (startIntent != null) {
            String action = startIntent.getAction();
            String command = startIntent.getStringExtra(CMD_NAME);
            if (ACTION_CMD.equals(action)) {
                if (CMD_PAUSE.equals(command)) {
                    mPlaybackManager.handlePauseRequest();
                }
//                else if (CMD_STOP_CASTING.equals(command)) {
//                    CastContext.getSharedInstance(this).getSessionManager().endCurrentSession
//                    (true);
//                }
            } else {
                // Try to handle the intent as a media button event wrapped by MediaButtonReceiver
                MediaButtonReceiver.handleIntent(mSession, startIntent);
            }
        }
        // Reset the delay handler to enqueue a message to stop the service if
        // nothing is playing.
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        mDelayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY);
        return START_STICKY;
    }

    /*
     * Handle case when user swipes the app away from the recents apps list by
     * stopping the service (and any ongoing playback).
     */
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    /**
     * (non-Javadoc)
     *
     * @see android.app.Service#onDestroy()
     */
    @Override
    public void onDestroy() {
        // Service is being killed, so make sure we release our resources
        mPlaybackManager.handleStopRequest(null);
        unregisterReceivers();

//        if (mCastSessionManager != null) {
//            mCastSessionManager.removeSessionManagerListener(mCastSessionManagerListener,
//                    CastSession.class);
//        }

        mDelayedStopHandler.removeCallbacksAndMessages(null);
        mSession.release();
    }

//    @Override
//    public IBinder onBind(Intent intent) {
//        return mBinder;
//    }

    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid,
                                 Bundle rootHints) {
        Log.i(ConstantUtil.TYPE_MUSIC, "MusicService.onGetRoot()--------");
        return new BrowserRoot(MEDIA_ID_ROOT, null);
    }

    @Override
    public void onLoadChildren(@NonNull final String parentMediaId,
                               @NonNull final Result<List<MediaBrowserCompat.MediaItem>> result) {
        Log.i(ConstantUtil.TYPE_MUSIC, "MusicService.onLoadChildren()---------加载音乐库");
//        if (MEDIA_ID_EMPTY_ROOT.equals(parentMediaId)) {
//            result.sendResult(new ArrayList<MediaBrowserCompat.MediaItem>());
//        } else
//        if (mMusicProvider.isInitialized()) {
////            // if music library is ready, return immediately
////            //如果音乐库已经准备好了，立即将数据发送至客户端
////            result.sendResult(mMusicProvider.getChildren(parentMediaId, getResources()));
////        } else {
        // otherwise, only return results when the music library is retrieved
        //音乐数据检索完毕后返回结果
        result.detach();
        mMusicProvider.retrieveMediaAsync(new MusicProvider.Callback() {
            //完成音乐加载后的回调
            @Override
            public void onMusicCatalogReady(boolean success) {
                result.sendResult(mMusicProvider.getChildren(parentMediaId, getResources()));
            }
        });
//        }
    }

    @Override
    public void onPlaybackStart() {
        mSession.setActive(true);

        mDelayedStopHandler.removeCallbacksAndMessages(null);

        // The service needs to continue running even after the bound client (usually a
        // MediaController) disconnects, otherwise the music playback will stop.
        // Calling startService(Intent) will keep the service running until it is explicitly killed.
        //即使绑定的客户端（通常是指MediaController）断开连接了，Service也需要继续运行，否则音乐将会停止播放。
        //调用startService(Intent)将保持Service持续运行直到明确要将服务杀掉为止
        startService(new Intent(getApplicationContext(), MusicService.class));
    }


    /**
     * Callback method called from PlaybackManager whenever the music stops playing.
     */
    @Override
    public void onPlaybackStop() {
        mSession.setActive(false);
        // Reset the delayed stop handler, so after STOP_DELAY it will be executed again,
        // potentially stopping the service.
        //重置延迟停止的Handler，所以收到 STOP_DELAY 消息后将再次执行
        //有可能会停止Service
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        mDelayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY);
        stopForeground(true);
    }

    @Override
    public void onNotificationRequired() {
//        mMediaNotificationManager.startNotification();
    }

    @Override
    public void onPlaybackStateUpdated(PlaybackStateCompat newState) {
        mSession.setPlaybackState(newState);
    }

    /**
     * @throws
     * @Description: 注册添加音乐数据源广播监听器，UI执行播放操作时，将此次播放的音乐添加入音乐库
     * @author Justiniano
     */
    private void registerAddMusicSourceReceiver() {
        IntentFilter filter = new IntentFilter(ACTION_ADD_MUSIC_SOURCE);
        mAddMusicSourceReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(ConstantUtil.TYPE_MUSIC, "监听到音乐Section添加音乐广播");
                RecommendInfo.ResultBean.BodyBean bodyBean =
                        intent.getParcelableExtra(ConstantUtil.EXTRA_ADD_SOURCE);
                Log.i(ConstantUtil.TYPE_MUSIC, "audio_url:" + bodyBean.getAudio_url());
                Log.i(ConstantUtil.TYPE_MUSIC, "audio_platform:" + bodyBean.getAudio_platform());
                Log.i(ConstantUtil.TYPE_MUSIC, "music_name:" + bodyBean.getMusic_name());
                Log.i(ConstantUtil.TYPE_MUSIC,
                        "platform_icon:" + bodyBean.getAudio_platform_icon());
                Log.i(ConstantUtil.TYPE_MUSIC,
                        "platform_name:" + bodyBean.getAudio_platform_name());
                Log.i(ConstantUtil.TYPE_MUSIC, "audio_author:" + bodyBean.getAudio_author());
                Log.i(ConstantUtil.TYPE_MUSIC, "audio_album:" + bodyBean.getAudio_album());
                Log.i(ConstantUtil.TYPE_MUSIC, "audio_cover:" + bodyBean.getAudio_cover());

                Log.i(ConstantUtil.TYPE_MUSIC, "source维护的list大小：" + mProviderSource.sourceSize());
                mProviderSource.add(bodyBean.getMusic_name(), bodyBean.getAudio_album(),
                        bodyBean.getAudio_author(), "", bodyBean.getAudio_url(),
                        bodyBean.getAudio_cover());
                mMusicProvider.updateSource(mProviderSource);
                notifyChildrenChanged(MEDIA_ID_ROOT);
            }
        };
        registerReceiver(mAddMusicSourceReceiver, filter);
    }

    private void unregisterReceivers() {
        unregisterReceiver(mAddMusicSourceReceiver);
    }

//    public class MusicServiceBinder extends Binder {
//        public boolean isSourceExist(String audioURL) {
//            return mProviderSource.isSourceExist(audioURL);
//        }
//    }

    /**
     * A simple handler that stops the service if playback is not active (playing)
     * 当playback不在活跃状态时停止服务
     */
    private static class DelayedStopHandler extends Handler {
        private final WeakReference<MusicService> mWeakReference;

        private DelayedStopHandler(MusicService service) {
            mWeakReference = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            MusicService service = mWeakReference.get();
            if (service != null && service.mPlaybackManager.getPlayback() != null) {
                if (service.mPlaybackManager.getPlayback().isPlaying()) {
                    return;
                }
                service.stopSelf();
            }
        }
    }
}
