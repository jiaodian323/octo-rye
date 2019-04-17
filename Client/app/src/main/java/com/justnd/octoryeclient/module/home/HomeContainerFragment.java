package com.justnd.octoryeclient.module.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxLazyFragment;
import com.justnd.octoryeclient.widget.adpter.DepthPageTransformer;
import com.justnd.octoryeclient.widget.adpter.HomeFragmentAdapter;
import com.justnd.octoryeclient.widget.adpter.ZoomOutPageTransformer;

import butterknife.BindView;

public class HomeContainerFragment extends RxLazyFragment {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    public HomeContainerFragment() {
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_container;
    }

    @Override
    public void finishCreateView(Bundle sate) {
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
//        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    // TODO: Rename and change types and number of parameters
    public static HomeContainerFragment newInstance() {
        HomeContainerFragment fragment = new HomeContainerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
