package com.justnd.octoryeclient.adapter.section;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Space;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.entity.recommond.RecommendInfo;
import com.justnd.octoryeclient.module.activity.ContentDetailActivity;
import com.justnd.octoryeclient.utils.ConstantUtil;
import com.justnd.octoryeclient.utils.DisplayUtil;
import com.justnd.octoryeclient.utils.ToastUtil;
import com.justnd.octoryeclient.widget.Sectioned.StatelessSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecommendedSection extends StatelessSection {
    public static final String TAG = "HomeRecommendedSection";

    private Context mContext;
    private String title;
    private String type;
    private int liveCount;
    private static final String GOTO_BANGUMI = "bangumi_list";
    private static final String TYPE_ACTIVITY = "activity";
    private List<RecommendInfo.ResultBean.BodyBean> datas = new ArrayList<>();
    private final Random mRandom;

    public HomeRecommendedSection(Context context, String title, String type, int liveCount,
                                  List<RecommendInfo.ResultBean.BodyBean> datas) {
        super(R.layout.layout_home_recommend_boby);
        this.mContext = context;
        this.title = title;
        this.type = type;
        this.liveCount = liveCount;
        this.datas = datas;
        mRandom = new Random();
    }

    @Override
    public int getContentItemsTotal() {
        return datas.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
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
                .into(itemViewHolder.mVideoImg);

        itemViewHolder.mCardView.setOnClickListener(v -> {
            String style = bodyBean.getStyle();
            switch (style) {
                case ConstantUtil.TYPE_ARTICLE:
                    ContentDetailActivity.launch((Activity) mContext,
                            bodyBean.getParam());
                    break;
                case ConstantUtil.TYPE_MUSIC:
                    ToastUtil.ShortToast("进入Music界面");
                    break;
                case ConstantUtil.TYPE_VIDEO:
                    ToastUtil.ShortToast("进入Video界面");
                    break;
                case ConstantUtil.TYPE_AUDIO:
                    ToastUtil.ShortToast("进入Audio界面");
                    break;
                default:
                    ContentDetailActivity.launch((Activity) mContext,
                            bodyBean.getParam());
//                    TestActivity.launch((Activity) mContext,
//                            contentId, bodyBean.getCover());
                    break;
            }
        });

        Log.i(TAG, "in HomeRecommendedSection:onBindItemViewHolder(): type=" + type);
        switch (type) {
            case ConstantUtil.TYPE_MUSIC:
                //直播item
                itemViewHolder.mLiveLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mIconLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.GONE);
                itemViewHolder.mLiveUp.setText(bodyBean.getUp());
                itemViewHolder.mLiveOnline.setText(String.valueOf(bodyBean.getOnline()));
                break;
            case ConstantUtil.TYPE_VIDEO:
                // 番剧item
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mIconLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mBangumiUpdate.setText(bodyBean.getDesc1());
                break;
            case TYPE_ACTIVITY:
                ViewGroup.LayoutParams layoutParams = itemViewHolder.mCardView.getLayoutParams();
                layoutParams.height = DisplayUtil.dp2px(mContext, 200f);
                itemViewHolder.mCardView.setLayoutParams(layoutParams);
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mIconLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.GONE);
                break;
            default:
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.GONE);
                itemViewHolder.mIconLayout.setVisibility(View.VISIBLE);
//                itemViewHolder.mVideoPlayNum.setText(bodyBean.getPlay());
//                itemViewHolder.mVideoReviewCount.setText(bodyBean.getDanmaku());
                break;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.article_title)
        TextView mArticleTitle;
        @BindView(R.id.author_name)
        TextView mAuthorName;
        @BindView(R.id.article_title_image)
        ImageView mVideoImg;
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

        //        @BindView(R.id.video_play_num)
//        TextView mVideoPlayNum;
//        @BindView(R.id.video_review_count)
//        TextView mVideoReviewCount;
        @BindView(R.id.layout_live)
        RelativeLayout mLiveLayout;
        @BindView(R.id.layout_icons)
        RelativeLayout mIconLayout;
        @BindView(R.id.item_live_up)
        TextView mLiveUp;
        @BindView(R.id.item_live_online)
        TextView mLiveOnline;
        @BindView(R.id.layout_bangumi)
        RelativeLayout mBangumiLayout;
        @BindView(R.id.item_bangumi_update)
        TextView mBangumiUpdate;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
