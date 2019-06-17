/*
 * Copyright (C) 2015 Naman Dwivedi
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */
package com.justnd.octoryeclient.module.musicinterface;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat;

import com.justnd.octoryeclient.module.base.RxLazyFragment;

public class QuickControlsFragment extends RxLazyFragment {

    public QuickControlsFragment() {
    }

    @Override
    public int getLayoutResId() {
        return 0;
    }

    @Override
    public void finishCreateView(Bundle sate) {
    }

    // TODO: Rename and change types and number of parameters
    public static QuickControlsFragment newInstance() {
        QuickControlsFragment fragment = new QuickControlsFragment();
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

    public void onConnected() {
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
        if (controller != null) {
//            onMetadataChanged(controller.getMetadata());
//            onPlaybackStateChanged(controller.getPlaybackState());
//            controller.registerCallback(mCallback);
        }
    }
}
