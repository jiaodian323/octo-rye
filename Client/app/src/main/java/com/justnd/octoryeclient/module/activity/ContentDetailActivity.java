package com.justnd.octoryeclient.module.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.entity.base.BaseBean;
import com.justnd.octoryeclient.entity.recommond.ArticleDetail;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.network.RetrofitHelper;
import com.justnd.octoryeclient.utils.ConstantUtil;
import com.justnd.octoryeclient.widget.customview.AuthorTagView;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ContentDetailActivity extends RxBaseActivity {
    @BindView(R.id.content_title_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingtoolbar;
    @BindView(R.id.content_title_image)
    ImageView mContentTitleImage;
    @BindView(R.id.content_title_tv)
    TextView mTitleTextView;
    @BindView(R.id.author_tv)
    TextView mAuthorTextView;
    @BindView(R.id.article_extract_tv)
    TextView mExtractTextView;
    @BindView(R.id.article_content_tv)
    TextView mContentTextView;
    @BindView(R.id.author_tag)
    AuthorTagView mAuthorTagView;

    private String contentType;
    private Integer contentId;
    private BaseBean<ArticleDetail> detail = new BaseBean<ArticleDetail>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_content_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        Intent intent = getIntent();
        if (intent != null) {
            contentType = intent.getStringExtra(ConstantUtil.EXTRA_CONTENT_TYPE);
            contentId = intent.getIntExtra(ConstantUtil.EXTRA_CONTENT_ID, -1);
        }

        loadData();
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void loadData() {
        RetrofitHelper.getRecommendService()
                .getArticleDetail(contentId)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultBeans -> {
                    detail = resultBeans;
                    finishTask();
                });
    }

    @Override
    public void finishTask() {
        mTitleTextView.setText(detail.getData().getTitle());
        String authorName = getString(R.string.authorName_prefix) + detail.getData().getAuthor()
                .getName();
        mAuthorTextView.setText(authorName);
        mExtractTextView.setText(detail.getData().getExtract());
        mContentTextView.setText(detail.getData().getContent());

        Glide.with(ContentDetailActivity.this)
                .load(detail.getData().getHeadImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(mContentTitleImage);

        mAuthorTagView.setUpWithInfo(ContentDetailActivity.this, detail.getData().getAuthor().getId(),
                detail.getData().getAuthor().getName(), detail.getData().getAuthor().getIntroduction(),
                detail.getData().getHeadImage());
        mCollapsingtoolbar.setTitle(getString(R.string.default_content_title));
    }

    public static void launch(Activity activity, int aid) {
        Intent intent = new Intent(activity, ContentDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ConstantUtil.EXTRA_CONTENT_ID, aid);
        activity.startActivity(intent);
    }
}
