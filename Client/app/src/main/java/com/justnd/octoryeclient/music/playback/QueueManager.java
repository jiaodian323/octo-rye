package com.justnd.octoryeclient.music.playback;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;

import com.justnd.octoryeclient.music.model.MusicProvider;
import com.justnd.octoryeclient.music.utils.QueueHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Justiniano
 * @Description:
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/6/1 0001 下午 7:46
 */
public class QueueManager {
    private MusicProvider mMusicProvider;
    private Resources mResources;
    private MetadataUpdateListener mListener;

    //当前播放队列
    private List<MediaSessionCompat.QueueItem> mPlayingQueue;
    private int mCurrentIndex;

    /**
     * @param musicProvider 数据源提供者
     * @param resources     系统资源
     * @param listener      播放数据更新的回调接口
     */
    public QueueManager(@NonNull MusicProvider musicProvider,
                        @NonNull Resources resources,
                        @NonNull MetadataUpdateListener listener) {
        this.mMusicProvider = musicProvider;
        this.mListener = listener;
        this.mResources = resources;

        //mPlayingQueue是线程安全的
        mPlayingQueue = Collections.synchronizedList(new ArrayList<MediaSessionCompat.QueueItem>());
        mCurrentIndex = 0;
    }

    /**
     * 通过mCurrentIndex获取当前播放的音乐
     *
     * @return
     */
    public MediaSessionCompat.QueueItem getCurrentMusic() {
        if (!QueueHelper.isIndexPlayable(mCurrentIndex, mPlayingQueue)) {
            return null;
        }
        return mPlayingQueue.get(mCurrentIndex);
    }

    /**
     * 设置当前的队列索引值
     *
     * @param index
     */
    private void setCurrentQueueIndex(int index) {
        if (index >= 0 && index < mPlayingQueue.size()) {
            mCurrentIndex = index;
            mListener.onCurrentQueueIndexUpdated(mCurrentIndex);
        }
    }

    public boolean setCurrentQueueItem(long queueId) {
        // set the current index on queue from the queue Id:
        int index = QueueHelper.getMusicIndexOnQueue(mPlayingQueue, queueId);
        setCurrentQueueIndex(index);
        return index >= 0;
    }

    public boolean setCurrentQueueItem(String mediaId) {
        // set the current index on queue from the music Id:
        int index = QueueHelper.getMusicIndexOnQueue(mPlayingQueue, mediaId);
        setCurrentQueueIndex(index);
        return index >= 0;
    }

    public void setQueueFromMusic(String mediaId) {
        // The mediaId used here is not the unique musicId. This one comes from the
        // MediaBrowser, and is actually a "hierarchy-aware mediaID": a concatenation of
        // the hierarchy in MediaBrowser and the actual unique musicID. This is necessary
        // so we can build the correct playing queue, based on where the track was
        // selected from.
        //这里使用的mediaId并不仅限于作为唯一的音乐识别Id，mediaId依赖于MediaBrowser，实际上它是一种层次清晰的mediaId：
        //在MediaBrowser中它具有 表明层级之间是如何关联的 以及 作为实际音乐唯一识别id 的作用
        //使用mediaId的必要之处在于我们可以根据 所选择的播放轨迹 来构建正确的播放队列
        boolean canReuseQueue;
//        if (isSameBrowsingCategory(mediaId)) {
        canReuseQueue = setCurrentQueueItem(mediaId);
//        }
        if (!canReuseQueue) {
            setCurrentQueue(QueueHelper.getPlayingQueue(mMusicProvider), mediaId);
        }
        updateMetadata();
    }

//    public boolean setQueueFromSearch(String query, Bundle extras) {
//        List<MediaSessionCompat.QueueItem> queue =
//                QueueHelper.getPlayingQueueFromSearch(query, extras, mMusicProvider);
//        setCurrentQueue(mResources.getString(R.string.search_queue_title), queue);
//        updateMetadata();
//        return queue != null && !queue.isEmpty();
//    }

    /**
     * 按照传入的数量跳到队列该数量的位置后（若值为负数则向前跳）开始播放音乐
     *
     * @param amount
     * @return
     */
    public boolean skipQueuePosition(int amount) {
        int index = mCurrentIndex + amount;
        if (index < 0) {
            // 如果索引值跳到了第一首歌的索引之前，则会从第一首歌开始播放
            index = 0;
        } else {
            // 通过取余的方式，当索引跳过了最后一首音乐，则回到队列开始处继续计算最终的索引值
            //（通过这种方式实现了队列的循环）
            index %= mPlayingQueue.size();
        }
        if (!QueueHelper.isIndexPlayable(index, mPlayingQueue)) {
            return false;
        }
        mCurrentIndex = index;
        return true;
    }

//    public boolean setQueueFromSearch(String query, Bundle extras) {
//        List<MediaSessionCompat.QueueItem> queue =
//                QueueHelper.getPlayingQueueFromSearch(query, extras, mMusicProvider);
//        setCurrentQueue(mResources.getString(R.string.search_queue_title), queue);
//        updateMetadata();
//        return queue != null && !queue.isEmpty();
//    }

    /**
     * 设置当前播放队列
     *
     * @param newQueue       新的播放队列
     * @param initialMediaId 初始的mediaId
     */
    protected void setCurrentQueue(List<MediaSessionCompat.QueueItem> newQueue,
                                   String initialMediaId) {
        mPlayingQueue = newQueue;
        int index = 0;
        if (initialMediaId != null) {
            index = QueueHelper.getMusicIndexOnQueue(mPlayingQueue, initialMediaId);
        }
        mCurrentIndex = Math.max(index, 0);
        mListener.onQueueUpdated(newQueue);
    }

    protected void setCurrentQueue(List<MediaSessionCompat.QueueItem> newQueue) {
        setCurrentQueue(newQueue, null);
    }

    /**
     * 设置播放队列为随机队列
     */
    public void setRandomQueue() {
        setCurrentQueue(QueueHelper.getRandomQueue(mMusicProvider));
        updateMetadata();
    }

    /**
     * 更新媒体数据
     */
    public void updateMetadata() {
        MediaSessionCompat.QueueItem currentMusic = getCurrentMusic();
        if (currentMusic == null) {
            mListener.onMetadataRetrieveError();
            return;
        }
        final String musicId = currentMusic.getDescription().getMediaId();
        MediaMetadataCompat metadata = mMusicProvider.getMusic(musicId);
        if (metadata == null) {
            throw new IllegalArgumentException("Invalid musicId " + musicId);
        }

        mListener.onMetadataChanged(metadata);

        //在MediaSession中设置适当的音乐专辑封面插图，以便在锁屏界面和其他地方显示
//        if (metadata.getDescription().getIconBitmap() == null &&
//                metadata.getDescription().getIconUri() != null) {
//            String albumUri = metadata.getDescription().getIconUri().toString();
//            //异步获取音乐专辑封面图片，通过回调将图片位图等信息返回来
//            AlbumArtCache.getInstance().fetch(albumUri, new AlbumArtCache.FetchListener() {
//                @Override
//                public void onFetched(String artUrl, Bitmap bitmap, Bitmap icon) {
//                    mMusicProvider.updateMusicArt(musicId, bitmap, icon);
//
//                    // If we are still playing the same music, notify the listeners:
//                    MediaSessionCompat.QueueItem currentMusic = getCurrentMusic();
//                    if (currentMusic == null) {
//                        return;
//                    }
//                    String currentPlayingId = MediaIDHelper.extractMusicIDFromMediaID(
//                            currentMusic.getDescription().getMediaId());
//                    if (musicId.equals(currentPlayingId)) {
//                        mListener.onMetadataChanged(mMusicProvider.getMusic(currentPlayingId));
//                    }
//                }
//            });
//        }
    }

    public interface MetadataUpdateListener {
        void onMetadataChanged(MediaMetadataCompat metadata);//媒体数据变更时调用

        void onMetadataRetrieveError();//媒体数据检索失败时调用

        void onCurrentQueueIndexUpdated(int queueIndex);//当前播放索引变更时调用

        void onQueueUpdated(List<MediaSessionCompat.QueueItem> newQueue);//当前播放队列变更时调用
    }
}
