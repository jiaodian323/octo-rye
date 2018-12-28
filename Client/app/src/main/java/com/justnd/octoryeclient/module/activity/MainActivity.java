package com.justnd.octoryeclient.module.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxBaseActivity;
import com.justnd.octoryeclient.module.home.HomeRecommendedFragment;

public class MainActivity extends RxBaseActivity {
    HomeRecommendedFragment recommendedFragment;

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
        recommendedFragment = HomeRecommendedFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.home_fragment_container, recommendedFragment);
        transaction.addToBackStack(null);
        transaction.show(recommendedFragment).commit();
    }

    @Override
    public void initToolBar() {
    }

    private void initFragments() {

    }
}
