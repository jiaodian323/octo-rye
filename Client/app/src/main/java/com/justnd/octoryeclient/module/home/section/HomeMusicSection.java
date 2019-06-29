package com.justnd.octoryeclient.module.home.section;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.entity.recommond.RecommendInfo;
import com.justnd.octoryeclient.module.activity.ContentDetailActivity;
import com.justnd.octoryeclient.music.MediaBrowserProvider;
import com.justnd.octoryeclient.music.MusicService;
import com.justnd.octoryeclient.music.utils.MusicIDHelper;
import com.justnd.octoryeclient.utils.ConstantUtil;
import com.justnd.octoryeclient.utils.DebugTagUtil;
import com.justnd.octoryeclient.utils.ToastUtil;
import com.justnd.octoryeclient.widget.Sectioned.SectionedRecyclerViewAdapter;
import com.justnd.octoryeclient.widget.Sectioned.StatelessSection;
import com.justnd.octoryeclient.widget.customview.CircleImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Justiniano
 * @Description:
 * @Email jiaodian822@163.com
 * @time 2019/6/10 0010 下午 9:20
 */
public class HomeMusicSection extends StatelessSection {

    private Context mContext;
    private SectionedRecyclerViewAdapter mAdapter;
    private MediaBrowserProvider mBrowserProvider;
    private ArrayList<RecommendInfo.ResultBean.BodyBean> mDatas;
    // 用以保存所有注册过的控制回调
    private static List<SectionControllerCallback> mControllerCallbacks = new ArrayList<>();
    private int lastPlayedPosition = -1;
    private Animation mAnimation;

    private static final String ACTION_IN_PLAYING_STATE = "playing";
    private static final String ACTION_IN_PAUSE_STATE = "pause";
    private static final String ACTION_IN_BUFFERING_STATE = "buffering";

    public HomeMusicSection(Context context, String type,
                            ArrayList<RecommendInfo.ResultBean.BodyBean> datas,
                            SectionedRecyclerViewAdapter adapter) {
        super(R.layout.layout_home_music_body);
        this.mContext = context;
        this.mBrowserProvider = (MediaBrowserProvider) context;
        this.mDatas = datas;
        this.mAdapter = adapter;
        this.mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
    }

    @Override
    public int getContentItemsTotal() {
        return mDatas.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        Log.i(ConstantUtil.TYPE_MUSIC, "获取ItemViewHolder:" + view.toString());
        return new HomeMusicSection.ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position,
                                     int sectionPosition, List<Object> payloads) {
        HomeMusicSection.ItemViewHolder itemViewHolder =
                (HomeMusicSection.ItemViewHolder) holder;
        final RecommendInfo.ResultBean.BodyBean bodyBean = mDatas.get(sectionPosition);

        // 当前Item对应音乐的ID
        String currentItemMusicId = MusicIDHelper.createMusicId(bodyBean.getAudio_url());

        if (payloads.isEmpty()) {
            itemViewHolder.mArticleTitle.setText(bodyBean.getTitle());
            String authorText = mContext.getString(R.string.authorName_prefix) + bodyBean
                    .getAuthorName();
            itemViewHolder.mAuthorName.setText(authorText);
            itemViewHolder.mArticleExtract.setText(bodyBean.getExtract());
            Glide.with(mContext)
                    .load(Uri.parse(bodyBean.getCover()))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.bili_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mAlbumImg);

            // 每次加载Item获取当前音乐对应的Item，更改其初始显示状态
            MediaControllerCompat initController =
                    MediaControllerCompat.getMediaController((Activity) mContext);
            if (initController != null) {
                String playingMusicId =
                        MusicIDHelper.getMusicIdPlaying((Activity) mContext);
                PlaybackStateCompat initState = initController.getPlaybackState();
                switch (initState.getState()) {
                    case PlaybackStateCompat.STATE_PLAYING:
                        if (currentItemMusicId.equals(playingMusicId)) {
                            itemViewHolder.mAlbumImg.startAnimation(mAnimation);
                            itemViewHolder.mPlayOrPause.setBackgroundResource(R.drawable.ic_pause_pre_xxhdpi);
                        }
                        break;
                    case PlaybackStateCompat.STATE_PAUSED:
                    case PlaybackStateCompat.STATE_STOPPED:
                    case PlaybackStateCompat.STATE_NONE:
                        itemViewHolder.mAlbumImg.clearAnimation();
                        itemViewHolder.mPlayOrPause.setBackgroundResource(R.drawable.ic_play_pre_xxhdpi);
                        break;
                    case PlaybackStateCompat.STATE_ERROR:
                        break;
                    default:
                        break;
                }
            }
        } else {
            // 适配器收到notifyItemChanged（）方法调用时，会触发此部分代码
            // 此部分代码为局部更新Item中控件
            for (Object payload : payloads) {
                switch (String.valueOf(payload)) {
                    case ACTION_IN_PLAYING_STATE:
                        itemViewHolder.mAlbumImg.startAnimation(mAnimation);
                        itemViewHolder.mPlayOrPause.setBackgroundResource(R.drawable.ic_pause_pre_xxhdpi);
                        break;
                    case ACTION_IN_PAUSE_STATE:
                        itemViewHolder.mAlbumImg.clearAnimation();
                        itemViewHolder.mPlayOrPause.setBackgroundResource(R.drawable.ic_play_pre_xxhdpi);
                        break;
                    default:
                        break;
                }
            }

            return;
        }

        // 发送广播，每加载一个音乐类型的RecycleItem,将该音乐信息写入音乐库中
        Intent intent = new Intent();
        intent.setAction(MusicService.ACTION_ADD_MUSIC_SOURCE);
        intent.putExtra(ConstantUtil.EXTRA_ADD_SOURCE, bodyBean);
        mContext.sendBroadcast(intent);

        itemViewHolder.mCardView.setOnClickListener(v ->
                ContentDetailActivity.launch((Activity) mContext,
                        bodyBean.getParam())
        );

        itemViewHolder.mPlayOrPause.setOnClickListener(v -> {

            if (lastPlayedPosition != -1 && lastPlayedPosition != position) {
                // 复原之前播放按钮为音乐暂停状态
                mAdapter.notifyItemChanged(lastPlayedPosition, ACTION_IN_PAUSE_STATE);
                Log.i(DebugTagUtil.HOME_MUSIC_SECTION,
                        "本次播放位置：" + position + ",上次播放位置：" + lastPlayedPosition);
            }

            // 获取MainActivity的MediaBrowser
            if (mBrowserProvider.getMediaBrowser().isConnected()) {
                MediaControllerCompat mediaController =
                        MediaControllerCompat.getMediaController((Activity) mContext);

                if (mediaController != null) {
                    Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG,
                            "根据token获取控制器，token：" + mediaController.getSessionToken().hashCode());
                    Iterator<SectionControllerCallback> iterator = mControllerCallbacks.iterator();
                    while (iterator.hasNext()) {
                        // 需要先解除之前的回调，不然会产生多个冗余回调
                        mediaController.unregisterCallback(iterator.next());
                    }
                    mControllerCallbacks.clear();
                    mediaController.registerCallback(mMediaControllerCallback.setPosition(position));
                    mControllerCallbacks.add(mMediaControllerCallback);

                    MediaControllerCompat.TransportControls transportControls =
                            mediaController.getTransportControls();
                    PlaybackStateCompat state = mediaController.getPlaybackState();
                    switch (state.getState()) {
                        case PlaybackStateCompat.STATE_BUFFERING:
                            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "播放器状态：" + state.getState());
                        case PlaybackStateCompat.STATE_PLAYING:
                            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "播放器状态：" + state.getState());
                            String nowPlayingMusicId =
                                    MusicIDHelper.getMusicIdPlaying((Activity) mContext);
                            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "当前正在播放的音乐ID：" + nowPlayingMusicId);
                            if (nowPlayingMusicId != null) {
                                if (nowPlayingMusicId.equals(currentItemMusicId)) {
                                    transportControls.pause();
                                } else {
                                    transportControls.playFromMediaId(currentItemMusicId, null);
                                    lastPlayedPosition = position;
                                }
                            }

                            break;
                        case PlaybackStateCompat.STATE_PAUSED:
                            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "播放器状态：" + state.toString());
                        case PlaybackStateCompat.STATE_STOPPED:
                            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "播放器状态：" + state.toString());
                        case PlaybackStateCompat.STATE_NONE:
                            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "播放器状态：" + state.toString());
//                            transportControls.play();
                            transportControls.playFromMediaId(currentItemMusicId, null);
                            lastPlayedPosition = position;
                            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "开始播放音乐，id：" + currentItemMusicId);
                            break;
                        case PlaybackStateCompat.STATE_ERROR:
                            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "播放器错误，错误码：" + state.getErrorCode() +
                                    "---错误信息:" + state.getErrorMessage());
                            break;
                        default:
                            Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "播放器状态：" + state.toString());
                    }
                }
            }
        });
    }

    private SectionPlaybackListener mPlaybackStateListener = new SectionPlaybackListener() {

        @Override
        public void updateInPausedState(int position) {
            Log.i(DebugTagUtil.HOME_MUSIC_SECTION, "In Section, adapter:" + mAdapter.hashCode());
            mAdapter.notifyItemChanged(position, ACTION_IN_PAUSE_STATE);
        }

        @Override
        public void updateInPlayingState(int position) {
            mAdapter.notifyItemChanged(position, ACTION_IN_PLAYING_STATE);
        }

        @Override
        public void updateInBufferingState(int position) {
            ToastUtil.ShortToast(mContext.getString(R.string.music_buffering));
        }
    };

    private SectionControllerCallback mMediaControllerCallback =
            new SectionControllerCallback(mPlaybackStateListener) {
                // 改变状态的音乐ID
                String activeId = "";

                @Override
                public void onPlaybackStateChanged(PlaybackStateCompat state) {
                    Log.i(DebugTagUtil.HOME_MUSIC_SECTION, "position:" + position);
                    super.onPlaybackStateChanged(state);
                    String playingMusicId =
                            MusicIDHelper.getMusicIdPlaying((Activity) mContext);
                    Log.i(DebugTagUtil.HOME_MUSIC_SECTION, "activeId:" + activeId + "," +
                            "playingId:" + playingMusicId);
                    // 如果当前播放音乐就是该Item对应的音乐，则更新其UI
//                        if (activeId.equals(currentItemMusicId)) {
                    switch (state.getState()) {
                        case PlaybackStateCompat.STATE_NONE:
                            Log.i(DebugTagUtil.HOME_MUSIC_SECTION, "控制回调，播放器状态：NONE");
                            break;
                        case PlaybackStateCompat.STATE_STOPPED:
                            Log.i(DebugTagUtil.HOME_MUSIC_SECTION, "控制回调，播放器状态：STOP");
                        case PlaybackStateCompat.STATE_PAUSED:
                            Log.i(DebugTagUtil.HOME_MUSIC_SECTION, "控制回调，播放器状态：PAUSED");
                            playbackStateListener.updateInPausedState(position);
                            break;
                        case PlaybackStateCompat.STATE_PLAYING:
                            Log.i(DebugTagUtil.HOME_MUSIC_SECTION, "控制回调，播放器状态：PLAYING");
                            playbackStateListener.updateInPlayingState(position);

                            break;
                        case PlaybackStateCompat.STATE_BUFFERING:
                            Log.i(DebugTagUtil.HOME_MUSIC_SECTION, "控制回调，播放器状态：缓冲中");
                            playbackStateListener.updateInBufferingState(position);
                            break;
                        default:
                    }
                }

                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    Log.i(ConstantUtil.TYPE_MUSIC, "HomeMusicSection.controlCallback" +
                            ".onMetadataChanged()---------元数据改变");
                    super.onMetadataChanged(metadata);
                    Log.i(ConstantUtil.TYPE_MUSIC,
                            "元数据信息:" + metadata.getDescription().getMediaId());
                    activeId = metadata.getDescription().getMediaId();
                }
            };

    private void configController(MediaControllerCompat controller,
                                  MediaControllerCompat.Callback callback) {
//        controller = new MediaControllerCompat(mContext, token);
//        MediaControllerCompat.setMediaController((Activity) mContext, controller);
        // 只初始化一次该回调，否则会注册多个相同回调
//        if (!isInitialized) {
        controller.registerCallback(callback);
//            isInitialized = true;
//        }

        onMediaControllerConnected();
    }

    private void onMediaControllerConnected() {
        // empty implementation, can be overridden by clients.
    }

    private final MediaBrowserCompat.SubscriptionCallback mBrowserSubscriptionCallback =
            new MediaBrowserCompat.SubscriptionCallback() {
                @Override
                public void onChildrenLoaded(@NonNull String parentId,
                                             @NonNull List<MediaBrowserCompat.MediaItem>
                                                     children) {
                    Log.i(ConstantUtil.TYPE_MUSIC, "subscriptionCallback" +
                            ".onChildrenLoaded()---------订阅音乐库成功,执行订阅回调-----(未知线程)");
                    super.onChildrenLoaded(parentId, children);
                }
            };

//    private class SectionControllerCallback extends MediaControllerCompat.Callback {
//        PlaybackStateListener mPlaybackStateListener;
//
//        SectionControllerCallback(PlaybackStateListener listener) {
//            this.mPlaybackStateListener = listener;
//        }
//    }
//
//    public interface PlaybackStateListener {
//        void updateInPausedState(int position);
//
//        void updateInPlayingState(int position);
//
//        void updateInBufferingState(int position);
//    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.article_title)
        TextView mArticleTitle;
        @BindView(R.id.author_name)
        TextView mAuthorName;
        @BindView(R.id.album_image)
        CircleImageView mAlbumImg;
        @BindView(R.id.article_extract)
        TextView mArticleExtract;
        @BindView(R.id.icon_like)
        ImageView mLikeIcon;
        @BindView(R.id.like_num)
        TextView mLikeNum;
        @BindView(R.id.space)
        Space mSpace;
        @BindView(R.id.icon_main_item_share)
        ImageView mItemShare;
        @BindView(R.id.play_or_pause_btn)
        ImageView mPlayOrPause;
        @BindView(R.id.layout_icons)
        RelativeLayout mIconLayout;

        //        @BindView(R.id.video_play_num)
//        TextView mVideoPlayNum;
//        @BindView(R.id.video_review_count)
//        TextView mVideoReviewCount;

        private ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
