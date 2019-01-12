package com.justnd.octoryeclient.module.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.utils.ConstantUtil;

import butterknife.BindView;


public class TestActivity extends RxBaseActivity {
    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.alertTitle)
    DialogTitle mDialogTitle;
    @BindView(R.id.message)
    TextView mMessage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Glide.with(TestActivity.this)
                .load("https://pic.36krcnd.com/201901/12010951/7jjm804yun7isbrt!1200")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(mIcon);

    }

    @Override
    public void initToolBar() {

    }

    public static void launch(Activity activity, int aid, String imgUrl) {
        Intent intent = new Intent(activity, TestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ConstantUtil.EXTRA_AV, aid);
        intent.putExtra(ConstantUtil.EXTRA_IMG_URL, imgUrl);
        activity.startActivity(intent);
    }

}
