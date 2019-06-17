package com.justnd.octoryeclient.music.playback;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.justnd.octoryeclient.music.model.MusicProvider;


/**
 * @author Justiniano
 * @Description:
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/6/1 0001 下午 5:39
 */
public class PlaybackManager implements Playback.Callback {

    private static final String TAG = "PlaybackManager";

    private MusicProvider mMusicProvider;
    private QueueManager mQueueManager;
    private Resources mResources;
    private Playback mPlayback;

    private PlaybackServiceCallback mServiceCallback;
    private MediaSessionCallback mMediaSessionCallback;

    public PlaybackManager(PlaybackServiceCallback serviceCallback, Resources resources,
                           MusicProvider musicProvider, QueueManager queueManager,
                           Playback playback) {
        mMusicProvider = musicProvider;
        mServiceCallback = serviceCallback;
        mResources = resources;
        mQueueManager = queueManager;
        mMediaSessionCallback = new MediaSessionCallback();
        mPlayback = playback;
        mPlayback.setCallback(this);
    }

    public Playback getPlayback() {
        return mPlayback;
    }

    public MediaSessionCompat.Callback getMediaSessionCallback() {
        return mMediaSessionCallback;
    }

    /**
     * 处理播放音乐的请求
     */
    public void handlePlayRequest() {
        MediaSessionCompat.QueueItem currentMusic = mQueueManager.getCurrentMusic();
        if (currentMusic != null) {
            mServiceCallback.onPlaybackStart();
            mPlayback.play(currentMusic);
        }
    }

    /**
     * Handle a request to pause music
     * 处理暂停音乐的请求
     */
    public void handlePauseRequest() {
        if (mPlayback.isPlaying()) {
            mPlayback.pause();
            mServiceCallback.onPlaybackStop();
        }
    }

    /**
     * Handle a request to stop music
     * 处理停止音乐的请求
     *
     * @param withError Error message in case the stop has an unexpected cause. The error
     *                  message will be set in the PlaybackState and will be visible to
     *                  MediaController clients.
     */
    public void handleStopRequest(String withError) {
        mPlayback.stop(true);
        mServiceCallback.onPlaybackStop();
        updatePlaybackState(withError);
    }

    /**
     * Update the current media player state, optionally showing an error message.
     * 更新当前媒体播放器的状态，可选择是否显示错误信息
     *
     * @param error 如果不为null，错误信息将呈现给用户.
     */
    public void updatePlaybackState(String error) {
        long position = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN;
        if (mPlayback != null && mPlayback.isConnected()) {
            position = mPlayback.getCurrentStreamPosition();
        }

        //noinspection ResourceType
        PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(getAvailableActions());

        setCustomAction(stateBuilder);
        int state = mPlayback.getState();

        // If there is an error message, send it to the playback state:
        if (error != null) {
            // Error states are really only supposed to be used for errors that cause playback to
            // stop unexpectedly and persist until the user takes action to fix it.
            stateBuilder.setErrorMessage(error);
            state = PlaybackStateCompat.STATE_ERROR;
        }
        //noinspection ResourceType
        stateBuilder.setState(state, position, 1.0f, SystemClock.elapsedRealtime());

        // Set the activeQueueItemId if the current index is valid.
        //如果当前索引是有效的
        MediaSessionCompat.QueueItem currentMusic = mQueueManager.getCurrentMusic();
        if (currentMusic != null) {
            stateBuilder.setActiveQueueItemId(currentMusic.getQueueId());
        }

        mServiceCallback.onPlaybackStateUpdated(stateBuilder.build());

        if (state == PlaybackStateCompat.STATE_PLAYING ||
                state == PlaybackStateCompat.STATE_PAUSED) {
            mServiceCallback.onNotificationRequired();
        }
    }

    /**
     * 设置自定义的操作
     * @param stateBuilder
     */
    private void setCustomAction(PlaybackStateCompat.Builder stateBuilder) {
        MediaSessionCompat.QueueItem currentMusic = mQueueManager.getCurrentMusic();
        if (currentMusic == null) {
            return;
        }
        // Set appropriate "Favorite" icon on Custom action:
        //在自定义操作中设置适当的"喜爱"图标
        String mediaId = currentMusic.getDescription().getMediaId();
        if (mediaId == null) {
            return;
        }
//        String musicId = MediaIDHelper.extractMusicIDFromMediaID(mediaId);
//        int favoriteIcon = mMusicProvider.isFavorite(musicId) ?
//                R.drawable.ic_star_on : R.drawable.ic_star_off;
//        Bundle customActionExtras = new Bundle();
//        WearHelper.setShowCustomActionOnWear(customActionExtras, true);
//        stateBuilder.addCustomAction(new PlaybackStateCompat.CustomAction.Builder(
//                CUSTOM_ACTION_THUMBS_UP, mResources.getString(R.string.favorite), favoriteIcon)
//                .setExtras(customActionExtras)
//                .build());
    }

    //获取所有可用的动作命令
    private long getAvailableActions() {
        long actions =
                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID |
                        PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT;
        if (mPlayback.isPlaying()) {
            actions |= PlaybackStateCompat.ACTION_PAUSE;
        } else {
            actions |= PlaybackStateCompat.ACTION_PLAY;
        }
        return actions;
    }

    @Override
    public void onCompletion() {
        // The media player finished playing the current song, so we go ahead
        // and start the next.
        //当音乐播放器播完了当前歌曲，则继续播放下一首
        if (mQueueManager.skipQueuePosition(1)) {
            handlePlayRequest();
//            mQueueManager.updateMetadata();
        } else {
            // If skipping was not possible, we stop and release the resources:
            //若不可能跳到下一首音乐进行播放，则停止并释放资源
            handleStopRequest(null);
        }
    }

    @Override
    public void onPlaybackStatusChanged(int state) {
        updatePlaybackState(null);
    }

    @Override
    public void onError(String error) {
        updatePlaybackState(error);
    }

    @Override
    public void setCurrentMediaId(String mediaId) {
//        mQueueManager.setQueueFromMusic(mediaId);
    }

    /**
     * Switch to a different Playback instance, maintaining all playback state, if possible.
     *
     * @param playback switch to this playback
     */
    public void switchToPlayback(Playback playback, boolean resumePlaying) {
        if (playback == null) {
            throw new IllegalArgumentException("Playback cannot be null");
        }
        // Suspends current state.
        int oldState = mPlayback.getState();
        long pos = mPlayback.getCurrentStreamPosition();
        String currentMediaId = mPlayback.getCurrentMediaId();
        mPlayback.stop(false);
        playback.setCallback(this);
        playback.setCurrentMediaId(currentMediaId);
        playback.seekTo(pos < 0 ? 0 : pos);
        playback.start();
        // Swaps instance.
        mPlayback = playback;
        switch (oldState) {
            case PlaybackStateCompat.STATE_BUFFERING:
            case PlaybackStateCompat.STATE_CONNECTING:
            case PlaybackStateCompat.STATE_PAUSED:
                mPlayback.pause();
                break;
            case PlaybackStateCompat.STATE_PLAYING:
                MediaSessionCompat.QueueItem currentMusic = mQueueManager.getCurrentMusic();
                if (resumePlaying && currentMusic != null) {
                    mPlayback.play(currentMusic);
                } else if (!resumePlaying) {
                    mPlayback.pause();
                } else {
                    mPlayback.stop(true);
                }
                break;
            case PlaybackStateCompat.STATE_NONE:
                break;
            default:
                Log.i(TAG, String.valueOf(oldState));
        }
    }

    private class MediaSessionCallback extends MediaSessionCompat.Callback {
        //点击播放按钮时触发
        //通过MediaControllerCompat.getTransportControls().play()触发
        @Override
        public void onPlay() {
            if (mQueueManager.getCurrentMusic() == null) {
                mQueueManager.setRandomQueue();
            }
            handlePlayRequest();
        }

        //播放指定队列媒体时触发
        //通过MediaControllerCompat.getTransportControls().onSkipToQueueItem(queueId)触发
        @Override
        public void onSkipToQueueItem(long queueId) {
            mQueueManager.setCurrentQueueItem(queueId);
            mQueueManager.updateMetadata();
        }

        //设置到指定进度时触发
        //通过MediaControllerCompat.getTransportControls().seekTo(position)触发
        @Override
        public void onSeekTo(long position) {
            mPlayback.seekTo((int) position);
        }

        //播放指定媒体数据时触发
        //通过MediaControllerCompat.getTransportControls().playFromMediaId(mediaItem.getMediaId(), null)触发
        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            mQueueManager.setQueueFromMusic(mediaId);
            handlePlayRequest();
        }

        //暂停时触发
        //通过MediaControllerCompat.getTransportControls().pause()触发
        @Override
        public void onPause() {
            handlePauseRequest();
        }

        //停止播放时触发
        //通过MediaControllerCompat.getTransportControls().stop()触发
        @Override
        public void onStop() {
            handleStopRequest(null);
        }

        //跳到下一首时触发
        //通过MediaControllerCompat.getTransportControls().skipToNext()触发
        @Override
        public void onSkipToNext() {
            if (mQueueManager.skipQueuePosition(1)) {
                handlePlayRequest();
            } else {
                handleStopRequest("Cannot skip");
            }
            mQueueManager.updateMetadata();
        }

        //跳到上一首时触发
        //通过MediaControllerCompat.getTransportControls().skipToPrevious()触发
        @Override
        public void onSkipToPrevious() {
            if (mQueueManager.skipQueuePosition(-1)) {
                handlePlayRequest();
            } else {
                handleStopRequest("Cannot skip");
            }
            mQueueManager.updateMetadata();
        }

        @Override
        public void onCustomAction(@NonNull String action, Bundle extras) {
//            if (CUSTOM_ACTION_THUMBS_UP.equals(action)) {
//                MediaSessionCompat.QueueItem currentMusic = mQueueManager.getCurrentMusic();
//                if (currentMusic != null) {
//                    String mediaId = currentMusic.getDescription().getMediaId();
//                    if (mediaId != null) {
//                        String musicId = MediaIDHelper.extractMusicIDFromMediaID(mediaId);
//                        mMusicProvider.setFavorite(musicId, !mMusicProvider.isFavorite(musicId));
//                    }
//                }
//                // playback state needs to be updated because the "Favorite" icon on the
//                // custom action will change to reflect the new favorite state.
//                updatePlaybackState(null);
//            } else {
//            }
        }

        /**
         * Handle free and contextual searches.
         * <p/>
         * All voice searches on Android Auto are sent to this method through a connected
         * {@link android.support.v4.media.session.MediaControllerCompat}.
         * <p/>
         * Threads and async handling:
         * Search, as a potentially slow operation, should run in another thread.
         * <p/>
         * Since this method runs on the main thread, most apps with non-trivial metadata
         * should defer the actual search to another thread (for example, by using
         * an {AsyncTask} as we do here).
         **/
        @Override
        public void onPlayFromSearch(final String query, final Bundle extras) {

            mPlayback.setState(PlaybackStateCompat.STATE_CONNECTING);
            mMusicProvider.retrieveMediaAsync(new MusicProvider.Callback() {
                @Override
                public void onMusicCatalogReady(boolean success) {
                    if (!success) {
                        updatePlaybackState("Could not load catalog");
                    }

//                    boolean successSearch = mQueueManager.setQueueFromSearch(query, extras);
//                    if (successSearch) {
//                        handlePlayRequest();
//                        mQueueManager.updateMetadata();
//                    } else {
//                        updatePlaybackState("Could not find music");
//                    }
                }
            });
        }
    }

    public interface PlaybackServiceCallback {
        void onPlaybackStart();

        void onNotificationRequired();

        void onPlaybackStop();

        void onPlaybackStateUpdated(PlaybackStateCompat newState);
    }
}
