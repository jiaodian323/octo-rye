package com.justnd.octoryeclient.module.home.section;

/**
 * @author Justiniano
 * @Description:
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/6/21 0021 下午 7:25
 */
public interface SectionPlaybackListener {
    void updateInPausedState(int position);

    void updateInPlayingState(int position);

    void updateInBufferingState(int position);
}
