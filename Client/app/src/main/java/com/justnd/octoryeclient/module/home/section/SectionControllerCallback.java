package com.justnd.octoryeclient.module.home.section;

import android.support.v4.media.session.MediaControllerCompat;

/**
 * @author Justiniano
 * @Description:
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/6/21 0021 下午 7:26
 */
public class SectionControllerCallback extends MediaControllerCompat.Callback {
    SectionPlaybackListener playbackStateListener;
    int position;

    public SectionControllerCallback setPosition(int pos) {
        this.position = pos;

        return this;
    }

    SectionControllerCallback(SectionPlaybackListener listener) {
        this.playbackStateListener = listener;
    }
}
