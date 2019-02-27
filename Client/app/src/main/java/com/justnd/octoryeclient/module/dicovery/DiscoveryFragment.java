package com.justnd.octoryeclient.module.dicovery;

import android.content.Context;
import android.os.Bundle;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.module.base.RxLazyFragment;

public class DiscoveryFragment extends RxLazyFragment {

    public DiscoveryFragment() {
    }

    public static DiscoveryFragment newInstance() {
        DiscoveryFragment fragment = new DiscoveryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void finishCreateView(Bundle state) {

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
