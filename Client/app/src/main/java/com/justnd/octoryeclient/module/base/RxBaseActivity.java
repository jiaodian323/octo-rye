package com.justnd.octoryeclient.module.base;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.justnd.octoryeclient.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RxBaseActivity extends RxAppCompatActivity {
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 设置布局内容
        setContentView(getLayoutId());
        // 初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        // 初始化控件
        initViews(savedInstanceState);
        // 初始化ToolBar
        initToolBar();
        // 适配沉浸式状态栏高度
//        setStatusBar();
    }


    /**
     * 设置布局layout
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化views
     *
     * @param savedInstanceState
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 初始化toolbar
     */
    public abstract void initToolBar();

    /**
     * 加载数据
     */
    public void loadData() {
    }

    /**
     * 显示进度条
     */
    public void showProgressBar() {
    }

    /**
     * 隐藏进度条
     */
    public void hideProgressBar() {
    }

    /**
     * 初始化recyclerView
     */
    public void initRecyclerView() {
    }

    /**
     * 初始化refreshLayout
     */
    public void initRefreshLayout() {
    }

    /**
     * 设置数据显示
     */
    public void finishTask() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    /**
     * 适配沉浸式状态栏高度
     */
//    protected void setStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            final ViewGroup linear_bar = (ViewGroup) findViewById(R.id.bar_layout);
//            final int statusHeight = getStatusBarHeight();
//            linear_bar.post(new Runnable() {
//                @Override
//                public void run() {
//                    int titleHeight = linear_bar.getHeight();
//                    android.support.constraint.ConstraintLayout.LayoutParams params = (android
//                            .support.constraint.ConstraintLayout.LayoutParams) linear_bar
//                            .getLayoutParams();
//                    params.height = statusHeight + titleHeight;
//                    linear_bar.setLayoutParams(params);
//                }
//            });
//        }
//    }
//
//    /**
//     * 获取状态栏的高度
//     *
//     * @return
//     */
//    protected int getStatusBarHeight() {
//        Resources resources = this.getResources();
//        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
//        int height = resources.getDimensionPixelSize(resourceId);
//        return height;
//    }
}
