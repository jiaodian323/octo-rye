package com.justnd.octoryeclient.module.home.section;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Space;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.entity.recommond.RecommendInfo;
import com.justnd.octoryeclient.module.activity.ContentDetailActivity;
import com.justnd.octoryeclient.widget.Sectioned.StatelessSection;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeArticleSection extends StatelessSection {
    public static final String TAG = "HomeArticleSection";

    private Context mContext;
    private String title;
    private String type;
    private static final String TYPE_ACTIVITY = "activity";
    private List<RecommendInfo.ResultBean.BodyBean> mDatas;
    private final Random mRandom;

    public HomeArticleSection(Context context, String type,
                              List<RecommendInfo.ResultBean.BodyBean> datas) {
        super(R.layout.layout_home_aticle_body);
        this.mContext = context;
        this.type = type;
        this.mDatas = datas;
        mRandom = new Random();
    }

    @Override
    public int getContentItemsTotal() {
        return mDatas.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position, int sectionPosition, List<Object> payloads) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final RecommendInfo.ResultBean.BodyBean bodyBean = mDatas.get(sectionPosition);

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
                .into(itemViewHolder.mTitleImg);

        itemViewHolder.mCardView.setOnClickListener(v -> {
            ContentDetailActivity.launch((Activity) mContext,
                    bodyBean.getParam());
        });
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.article_title)
        TextView mArticleTitle;
        @BindView(R.id.author_name)
        TextView mAuthorName;
        @BindView(R.id.article_title_image)
        ImageView mTitleImg;
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
