package com.justnd.octoryeclient.module.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.module.home.HomeRecommendedFragment;
import com.justnd.octoryeclient.utils.ToastUtil;

public class MainActivity extends RxBaseActivity {
    HomeRecommendedFragment mRecommendedFragment;

    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mRecommendedFragment = HomeRecommendedFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.home_fragment_container, mRecommendedFragment);
        transaction.addToBackStack(null);
        transaction.show(mRecommendedFragment).commit();
    }

    @Override
    public void initToolBar() {
    }

    private void initFragments() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp();
        }

        return true;
    }

    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.ShortToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
