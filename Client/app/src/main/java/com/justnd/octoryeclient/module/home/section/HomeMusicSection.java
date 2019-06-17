package com.justnd.octoryeclient.module.home.section;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.justnd.octoryeclient.utils.ConstantUtil;
import com.justnd.octoryeclient.utils.ToastUtil;
import com.justnd.octoryeclient.widget.Sectioned.StatelessSection;
import com.justnd.octoryeclient.widget.customview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Justiniano
 * @Description:
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/6/10 0010 下午 9:20
 */
public class HomeMusicSection extends StatelessSection {
    public static final String TAG = "HomeMusicSection";

    private Context mContext;
    private MediaBrowserProvider mBrowserProvider;
    private String mMediaId;
    private String type;
    private static final String TYPE_ACTIVITY = "activity";
    private ArrayList<RecommendInfo.ResultBean.BodyBean> datas;

    public HomeMusicSection(Context context, String type,
                            ArrayList<RecommendInfo.ResultBean.BodyBean> datas) {
        super(R.layout.layout_home_music_body);
        this.mContext = context;
        this.mBrowserProvider = (MediaBrowserProvider) context;
        this.type = type;
        this.datas = datas;
    }

    @Override
    public int getContentItemsTotal() {
        return datas.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new HomeMusicSection.ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeMusicSection.ItemViewHolder itemViewHolder =
                (HomeMusicSection.ItemViewHolder) holder;
        final RecommendInfo.ResultBean.BodyBean bodyBean = datas.get(position);

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

        itemViewHolder.mCardView.setOnClickListener(v ->
                ContentDetailActivity.launch((Activity) mContext,
                        bodyBean.getParam())
        );

        // 每加载一个音乐类型的ListItem,将该音乐信息写入音乐库中
        Intent intent = new Intent();
        intent.setAction(MusicService.ACTION_ADD_MUSIC_SOURCE);
        intent.putExtra(ConstantUtil.EXTRA_ADD_SOURCE, bodyBean);
        mContext.sendBroadcast(intent);

        itemViewHolder.mPlayOrPause.setOnClickListener(v -> {
//            Intent intent = new Intent();
//            intent.setAction(MusicService.ACTION_ADD_MUSIC_SOURCE);
//            intent.putExtra(ConstantUtil.EXTRA_ADD_SOURCE, bodyBean);
//            mContext.sendBroadcast(intent);

//            if (mBrowserProvider.getMediaBrowser().isConnected()) {
////                onConnected();
////            }

            PlaybackStateCompat state =
                    MediaControllerCompat.getMediaController((Activity) mContext).getPlaybackState();
            if (state != null) {
                MediaControllerCompat.TransportControls controls =
                        MediaControllerCompat.getMediaController((Activity) mContext).getTransportControls();
//                        controls.play();
                switch (state.getState()) {
                    case PlaybackStateCompat.STATE_PLAYING: // fall through
                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器状态：" + state.getState());
                    case PlaybackStateCompat.STATE_BUFFERING:
                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器状态：" + state.getState());
                        controls.pause();
                        break;
                    case PlaybackStateCompat.STATE_PAUSED:
                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器状态：" + state.getState());
                    case PlaybackStateCompat.STATE_STOPPED:
                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器状态：" + state.getState());
                    case PlaybackStateCompat.STATE_NONE:
                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器状态：" + state.getState());
//                                controls.play();
                        String mediaId =
                                String.valueOf(datas.get(position).getAudio_url().hashCode());
                        controls.playFromMediaId(mediaId, null);
                        Log.i(ConstantUtil.TYPE_MUSIC, "开始播放音乐，id：" + mediaId);
                        break;
                    case PlaybackStateCompat.STATE_ERROR:
                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器错误，错误码：" + state.getErrorCode() +
                                "---错误信息:" + state.getErrorMessage());
                        break;
                    default:
                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器状态：" + state.getState());
                }
            }

            // 每次播放前，重新检索音乐库信息
//            Log.i(ConstantUtil.TYPE_MUSIC, "执行订阅前----------（主线程）");
//            reSubscribe();

//            Log.i(ConstantUtil.TYPE_MUSIC, "执行订阅后---------（主线程）");
//            PlaybackStateCompat state =
//                    MediaControllerCompat.getMediaController((Activity) mContext)
//                    .getPlaybackState();
//            if (state != null) {
//                MediaControllerCompat.TransportControls controls =
//                        MediaControllerCompat.getMediaController((Activity) mContext)
//                        .getTransportControls();
//                controls.play();
//                switch (state.getState()) {
//                    case PlaybackStateCompat.STATE_PLAYING: // fall through
//                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器状态：" + state.getState());
//                    case PlaybackStateCompat.STATE_BUFFERING:
//                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器状态：" + state.getState());
//                        controls.pause();
//                        break;
//                    case PlaybackStateCompat.STATE_PAUSED:
//                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器状态：" + state.getState());
//                    case PlaybackStateCompat.STATE_STOPPED:
//                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器状态：" + state.getState());
//                        controls.play();
//                        break;
//                    default:
//                        Log.i(ConstantUtil.TYPE_MUSIC, "播放器状态：" + state.getState());
//                }
//            }
        });
    }

    public void onConnected() {
        // Unsubscribing before subscribing is required if this mediaId already has a subscriber
        // on this MediaBrowser instance. Subscribing to an already subscribed mediaId will replace
        // the callback, but won't trigger the initial callback.onChildrenLoaded.
        //
        // This is temporary: A bug is being fixed that will make subscribe
        // consistently call onChildrenLoaded initially, no matter if it is replacing an existing
        // subscriber or not. Currently this only happens if the mediaID has no previous
        // subscriber or if the media content changes on the service side, so we need to
        // unsubscribe first.
        reSubscribe();

        // Add MediaController callback so we can redraw the list when metadata changes:
        MediaControllerCompat controller =
                MediaControllerCompat.getMediaController((Activity) mContext);
        if (controller != null) {
            controller.registerCallback(mMediaControllerCallback);
        }
    }

    /**
     * @param
     * @return
     * @throws
     * @Description: 发起音乐库订阅
     * @author Justiniano
     */
    private void reSubscribe() {
        mMediaId = mBrowserProvider.getMediaBrowser().getRoot();
        Log.i(ConstantUtil.TYPE_MUSIC, "In Section, mMediaId=" + mMediaId);
        mBrowserProvider.getMediaBrowser().unsubscribe(mMediaId);
        mBrowserProvider.getMediaBrowser().subscribe(mMediaId, mBrowserSubscriptionCallback);
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

    private final MediaControllerCompat.Callback mMediaControllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onPlaybackStateChanged(PlaybackStateCompat state) {
                    Log.i(ConstantUtil.TYPE_MUSIC, "MainActivity.controlCallback" +
                            ".onPlaybackStateChanged()---------");
                    super.onPlaybackStateChanged(state);

                    switch (state.getState()) {
                        case PlaybackStateCompat.STATE_NONE:
                            Log.i(ConstantUtil.TYPE_MUSIC, "控制回调，播放器状态：NONE");
                            break;
                        case PlaybackStateCompat.STATE_PAUSED:
                            Log.i(ConstantUtil.TYPE_MUSIC, "控制回调，播放器状态：PAUSED");
                            break;
                        case PlaybackStateCompat.STATE_PLAYING:
                            Log.i(ConstantUtil.TYPE_MUSIC, "控制回调，播放器状态：PLAYING");
                            break;
                        case PlaybackStateCompat.STATE_BUFFERING:
                            Log.i(ConstantUtil.TYPE_MUSIC, "控制回调，播放器状态：缓冲中");
                            ToastUtil.ShortToast(mContext.getString(R.string.music_buffering));
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

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
