package com.justnd.octoryeclient.module.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.utils.ConstantUtil;

import butterknife.BindView;

public class ContentDetailActivity extends RxBaseActivity {
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingtoolbar;
    @BindView(R.id.content_title_image)
    ImageView mContentTitleImage;

    private Integer contentId;
    private String imgUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_content_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            contentId = intent.getIntExtra(ConstantUtil.EXTRA_AV, -1);
            imgUrl = intent.getStringExtra(ConstantUtil.EXTRA_IMG_URL);
        }
        Glide.with(ContentDetailActivity.this)
                .load(imgUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(mContentTitleImage);
        mCollapsingtoolbar.setTitle(getString(R.string.default_content_title));
    }

    @Override
    public void initToolBar() {

    }

    public static void launch(Activity activity, int aid, String imgUrl) {
        Intent intent = new Intent(activity, ContentDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ConstantUtil.EXTRA_AV, aid);
        intent.putExtra(ConstantUtil.EXTRA_IMG_URL, imgUrl);
        activity.startActivity(intent);
    }
}
