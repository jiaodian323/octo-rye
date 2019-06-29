package com.justnd.octoryeclient.module.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.net.Uri;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.music.MusicService;
import com.justnd.octoryeclient.utils.DebugTagUtil;
import com.justnd.octoryeclient.utils.ImageUtil;
import com.justnd.octoryeclient.widget.customview.BlurringView;
import com.justnd.octoryeclient.widget.customview.RoundRectangleImageView;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;


import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * @author Justiniano
 * @Description:
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/6/23 0023 下午 8:22
 */
public class FullScreenPlayerActivity extends RxBaseActivity {
    private static final long PROGRESS_UPDATE_INTERNAL = 1000;
    private static final long PROGRESS_UPDATE_INITIAL_INTERVAL = 100;

    @BindView(R.id.album_image)
    RoundRectangleImageView mAlbumImage;
    @BindView(R.id.prev)
    ImageView mSkipPrev;
    @BindView(R.id.next)
    ImageView mSkipNext;
    @BindView(R.id.play_pause)
    ImageView mPlayPause;
    @BindView(R.id.startText)
    TextView mStart;
    @BindView(R.id.endText)
    TextView mEnd;
    @BindView(R.id.seekBar1)
    SeekBar mSeekBar;
    @BindView(R.id.music_name)
    TextView mMusicName;
    @BindView(R.id.music_author)
    TextView mMusicAuthor;
    @BindView(R.id.controllers)
    View mControllers;
    @BindView(R.id.background_image)
    ImageView mBackgroundImage;
    @BindView(R.id.background_blur_view)
    BlurringView mBlurView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Drawable mPauseDrawable;
    private Drawable mPlayDrawable;

    private final Handler mHandler = new Handler();
    private MediaBrowserCompat mMediaBrowser;

    private final Runnable mUpdateProgressTask = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    private final ScheduledExecutorService mExecutorService =
            Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> mScheduleFuture;
    private PlaybackStateCompat mLastPlaybackState;

    private final MediaControllerCompat.Callback mCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
            updatePlaybackState(state);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            if (metadata != null) {
                updateMediaDescription(metadata.getDescription());
                updateDuration(metadata);
            }
        }
    };

    private final MediaBrowserCompat.ConnectionCallback mConnectionCallback =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    try {
                        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "连接成功---------");
                        connectToSession(mMediaBrowser.getSessionToken());
                    } catch (RemoteException e) {
                        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "创建Session异常");
                    }
                }

                @Override
                public void onConnectionFailed() {
                    Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "连接失败---------");
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "FullScreenActivity-------onCreate()" +
                "-------------");
        super.onCreate(savedInstanceState);

        mMediaBrowser = new MediaBrowserCompat(this, new ComponentName(this, MusicService.class),
                mConnectionCallback, null);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_full_player;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBlurView.setBlurredView(mBackgroundImage);
        mAlbumImage.setRadius(60);

        mPauseDrawable = ContextCompat.getDrawable(this, R.drawable.ic_pause_tiny_white);
        mPlayDrawable = ContextCompat.getDrawable(this, R.drawable.ic_play_tiny_white);

        mSkipNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControllerCompat.TransportControls controls =
                        MediaControllerCompat.getMediaController(FullScreenPlayerActivity.this).getTransportControls();
                controls.skipToNext();
            }
        });

        mSkipPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControllerCompat.TransportControls controls =
                        MediaControllerCompat.getMediaController(FullScreenPlayerActivity.this).getTransportControls();
                controls.skipToPrevious();
            }
        });

        mPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaybackStateCompat state =
                        MediaControllerCompat.getMediaController(FullScreenPlayerActivity.this).getPlaybackState();
                if (state != null) {
                    MediaControllerCompat.TransportControls controls =
                            MediaControllerCompat.getMediaController(FullScreenPlayerActivity.this).getTransportControls();
                    switch (state.getState()) {
                        case PlaybackStateCompat.STATE_PLAYING: // fall through
                        case PlaybackStateCompat.STATE_BUFFERING:
                            controls.pause();
                            stopSeekBarUpdate();
                            break;
                        case PlaybackStateCompat.STATE_PAUSED:
                        case PlaybackStateCompat.STATE_STOPPED:
                            controls.play();
                            scheduleSeekbarUpdate();
                            break;
                        default:
                    }
                }
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mStart.setText(DateUtils.formatElapsedTime(progress / 1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopSeekBarUpdate();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MediaControllerCompat.getMediaController(FullScreenPlayerActivity.this).getTransportControls().seekTo(seekBar.getProgress());
                scheduleSeekbarUpdate();
            }
        });

        // Only update from the intent if we are not recreating from a config change:
        if (savedInstanceState == null) {
            updateFromParams(getIntent());
        }
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 左侧添加一个默认的返回图标
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("");
        }

        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        release();
        overridePendingTransition(R.anim.anim_bottom_silent, R.anim.anim_bottom_out);
    }

    @Override
    public void release() {
        stopSeekBarUpdate();
        mSkipPrev.setOnClickListener(null);
        mSkipNext.setOnClickListener(null);
        mPlayPause.setOnClickListener(null);
        mSeekBar.setOnSeekBarChangeListener(null);
    }

    @Override
    protected void onStart() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "FullScreenActivity-------OnStart()" +
                "-------------");
        super.onStart();

        if (!mMediaBrowser.isConnected() && mMediaBrowser != null) {
            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "客户端连接MusicService");
            mMediaBrowser.connect();
        }
    }

    @Override
    protected void onResume() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "FullScreenActivity-------onResume()" +
                "-------------");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "FullScreenActivity-------OnPause()" +
                "-------------");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "FullScreenActivity-------OnStop()" +
                "-------------");
        super.onStop();

        if (mMediaBrowser.isConnected() && mMediaBrowser != null) {
            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "断开客户端连接");
            mMediaBrowser.disconnect();
        }

        MediaControllerCompat controllerCompat = MediaControllerCompat.getMediaController(this);
        if (controllerCompat != null) {
            controllerCompat.unregisterCallback(mCallback);
        }

        stopSeekBarUpdate();
        mExecutorService.shutdown();
    }

    @Override
    protected void onDestroy() {
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "FullScreenActivity-------OnDestroy()" +
                "-------------");
        super.onDestroy();
    }

    private void connectToSession(MediaSessionCompat.Token token) throws RemoteException {
        MediaControllerCompat mediaController = new MediaControllerCompat(
                FullScreenPlayerActivity.this, token);
        MediaControllerCompat.setMediaController(FullScreenPlayerActivity.this, mediaController);
        if (mediaController.getMetadata() == null) {
            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "找不到元数据");
            return;
        }
        mediaController.registerCallback(mCallback);
        PlaybackStateCompat state = mediaController.getPlaybackState();
        updatePlaybackState(state);
        MediaMetadataCompat metadata = mediaController.getMetadata();
        if (metadata != null) {
            updateMediaDescription(metadata.getDescription());
            updateDuration(metadata);
        }
        updateProgress();
        if (state != null && (state.getState() == PlaybackStateCompat.STATE_PLAYING ||
                state.getState() == PlaybackStateCompat.STATE_BUFFERING)) {
            scheduleSeekbarUpdate();
        }
    }

    private void updateFromParams(Intent intent) {
        if (intent != null) {
//            MediaDescriptionCompat description = intent.getParcelableExtra(
//                MusicPlayerActivity.EXTRA_CURRENT_MEDIA_DESCRIPTION);
//            if (description != null) {
//                updateMediaDescription(description);
//            }
        }
    }

    private void scheduleSeekbarUpdate() {
        stopSeekBarUpdate();
        if (!mExecutorService.isShutdown()) {
            mScheduleFuture = mExecutorService.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            mHandler.post(mUpdateProgressTask);
                        }
                    }, PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS);
        }
    }

    private void stopSeekBarUpdate() {
        if (mScheduleFuture != null) {
            mScheduleFuture.cancel(false);
        }
    }

//    private void fetchImageAsync(@NonNull MediaDescriptionCompat description) {
//        if (description.getIconUri() == null) {
//            return;
//        }
//        String artUrl = description.getIconUri().toString();
//        mCurrentArtUrl = artUrl;
//        AlbumArtCache cache = AlbumArtCache.getInstance();
//        Bitmap art = cache.getBigImage(artUrl);
//        if (art == null) {
//            art = description.getIconBitmap();
//        }
//        if (art != null) {
//            // if we have the art cached or from the MediaDescription, use it:
//            mBackgroundImage.setImageBitmap(art);
//        } else {
//            // otherwise, fetch a high res version and update:
//            cache.fetch(artUrl, new AlbumArtCache.FetchListener() {
//                @Override
//                public void onFetched(String artUrl, Bitmap bitmap, Bitmap icon) {
//                    // sanity check, in case a new fetch request has been done while
//                    // the previous hasn't yet returned:
//                    if (artUrl.equals(mCurrentArtUrl)) {
//                        mBackgroundImage.setImageBitmap(bitmap);
//                    }
//                }
//            });
//        }
//    }

    private void updateMediaDescription(MediaDescriptionCompat description) {
        if (description == null) {
            return;
        }
        mMusicName.setText(description.getTitle());
        mMusicAuthor.setText(description.getSubtitle());
//        Glide.with(FullScreenPlayerActivity.this)
//                .load(description.getIconUri())
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.bili_default_image_tv)
//                .dontAnimate()
//                .into(mBackgroundImage);

//        new BlurBackgroundTask().execute(this, description.getIconUri());

//        Bitmap backBitmap = ImageUtil.genBlurBitmapFromUrl(this, description.getIconUri(), 500,
//        500);
//        mBackgroundImage.setImageBitmap(backBitmap);
//        fetchImageAsync(description);


        Glide.with(this)
                .load(description.getIconUri())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<?
                            super Bitmap> glideAnimation) {
                        Bitmap outBmp = ImageUtil.zoomBitmap(resource, 200, 200);
                        mAlbumImage.setImageBitmap(resource);
                        mBackgroundImage.setImageBitmap(outBmp);
                        mBlurView.invalidate();
                    }
                }); //方法中设置asBitmap可以设置回调类型

//        //设置图片圆角角度
//        RoundedCorners roundedCorners= new RoundedCorners(6);
//       //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
//        Glide.with(this)
//                .load(description.getIconUri())
//                .apply(options)
//                .into(mAlbumImage);
    }

    private void updateDuration(MediaMetadataCompat metadata) {
        if (metadata == null) {
            return;
        }
        int duration = (int) metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
        mSeekBar.setMax(duration);
        mEnd.setText(DateUtils.formatElapsedTime(duration / 1000));
    }

    private void updatePlaybackState(PlaybackStateCompat state) {
        if (state == null) {
            return;
        }
        mLastPlaybackState = state;
        MediaControllerCompat controllerCompat =
                MediaControllerCompat.getMediaController(FullScreenPlayerActivity.this);
        if (controllerCompat != null && controllerCompat.getExtras() != null) {
//            String castName = controllerCompat.getExtras().getString(MusicService
//            .EXTRA_CONNECTED_CAST);
//            String line3Text = castName == null ? "" : getResources()
//                        .getString(R.string.casting_to_device, castName);
//            mLine3.setText(line3Text);
        }

        switch (state.getState()) {
            case PlaybackStateCompat.STATE_PLAYING:
                mControllers.setVisibility(VISIBLE);
                mPlayPause.setImageDrawable(mPauseDrawable);
                scheduleSeekbarUpdate();
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                mControllers.setVisibility(VISIBLE);
                mPlayPause.setImageDrawable(mPlayDrawable);
                stopSeekBarUpdate();
                break;
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_STOPPED:
                mPlayPause.setImageDrawable(mPlayDrawable);
                stopSeekBarUpdate();
                break;
            case PlaybackStateCompat.STATE_BUFFERING:
                stopSeekBarUpdate();
                break;
            default:
        }

        mSkipNext.setVisibility((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_NEXT) == 0
                ? INVISIBLE : VISIBLE);
        mSkipPrev.setVisibility((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS) == 0
                ? INVISIBLE : VISIBLE);
    }

    private void updateProgress() {
        if (mLastPlaybackState == null) {
            return;
        }
        long currentPosition = mLastPlaybackState.getPosition();
        if (mLastPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING) {
            // Calculate the elapsed time between the last position update and now and unless
            // paused, we can assume (delta * speed) + current position is approximately the
            // latest position. This ensure that we do not repeatedly call the getPlaybackState()
            // on MediaControllerCompat.
            long timeDelta = (SystemClock.elapsedRealtime() -
                    mLastPlaybackState.getLastPositionUpdateTime());
            currentPosition =
                    (int) (currentPosition + timeDelta * mLastPlaybackState.getPlaybackSpeed());
        }
        mSeekBar.setProgress((int) currentPosition);
    }

    private static class BlurBackgroundTask extends AsyncTask<Object, Void, Bitmap> {
        WeakReference<FullScreenPlayerActivity> mTaskContext;

        @Override
        protected Bitmap doInBackground(Object... params) {
            mTaskContext = new WeakReference<>((FullScreenPlayerActivity) params[0]);
            float scale = 0.5f;

            Bitmap inputBitmap;
            Bitmap outputBitmap;
            try {
                inputBitmap = Glide.with(mTaskContext.get())
                        .load((Uri) params[1])
                        .asBitmap()
                        .into(400, 400)
                        .get();

                outputBitmap = ImageUtil.zoomBitmap(inputBitmap, 200, 200);

//                outputBitmap = ImageUtil.genBlurBitmap((Context) params[0], inputBitmap);
//                outputBitmap = ImageUtil.rsBlur((FullScreenPlayerActivity) params[0],
//                inputBitmap, 15, 0.5f);
            } catch (Exception e) {
                Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "模糊转换异常:" + e.getMessage());
                inputBitmap = null;
                outputBitmap = null;
            }

            return outputBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mTaskContext.get().mBackgroundImage.setImageBitmap(bitmap);
            mTaskContext.get().mBlurView.invalidate();
        }
    }
}
