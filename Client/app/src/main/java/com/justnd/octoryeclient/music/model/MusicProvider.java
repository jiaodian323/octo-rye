package com.justnd.octoryeclient.music.model;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;

import com.justnd.octoryeclient.entity.recommond.RecommendInfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Justiniano
 * @Description:
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/5/31 0031 下午 10:33
 */
public class MusicProvider {

    public static final String MEDIA_ID_ROOT = "__ROOT__";
    public static final String MEDIA_ID_MUSICS_BY_GENRE = "__BY_GENRE__";

    private static MusicProvider providerInstance;
    private MusicProviderSource mSource;
    private final ConcurrentMap<String, MutableMediaMetadata> mMusicListById;

    enum State {
        NON_INITIALIZED, INITIALIZING, INITIALIZED
    }

    private volatile State mCurrentState = State.NON_INITIALIZED;

    public interface Callback {
        void onMusicCatalogReady(boolean success);
    }

    /**
    * @Description: 设置为单例模式，提供唯一数据访问对象，以防止MusicService服务被意外
     *              销毁后，丢失之前的音乐库信息
    * @param
    * @return
    * @throws
    * @author Justiniano
    */
    public static MusicProvider getInstance() {
        if (providerInstance == null) {
            providerInstance = new MusicProvider(SimpleMusicProviderSource.getInstance());
        }

        return providerInstance;
    }

    private MusicProvider(MusicProviderSource source) {
        mSource = source;
        mMusicListById = new ConcurrentHashMap<>();
    }

    public void updateSource(MusicProviderSource source) {
        mSource = source;
    }

    public MediaMetadataCompat getMusic(String musicId) {
        return mMusicListById.containsKey(musicId) ? mMusicListById.get(musicId).metadata : null;
    }

    public List<MediaMetadataCompat> getAllMusics() {
        List<MediaMetadataCompat> allMusics = new ArrayList<>();
        for (MutableMediaMetadata m : mMusicListById.values()) {
            allMusics.add(m.metadata);
        }

        return allMusics;
    }

    public boolean isInitialized() {
        return mCurrentState == State.INITIALIZED;
    }

    public void retrieveMediaAsync(final Callback callback) {
//        if (mCurrentState == State.INITIALIZED) {
//            if (callback != null) {
//                // Nothing to do, execute callback immediately
//                callback.onMusicCatalogReady(true);
//            }
//            return;
//        }

        //在单独的线程中异步加载音乐目录
        new RetrieveAsyncTask(this, callback).execute();
    }

    private static class RetrieveAsyncTask extends AsyncTask<Void, Void, State> {
        private WeakReference<MusicProvider> providerReference;
        private MusicProvider.Callback callback;

        RetrieveAsyncTask(MusicProvider provider, MusicProvider.Callback callback) {
            providerReference = new WeakReference<>(provider);
            this.callback = callback;
        }

        @Override
        protected State doInBackground(Void... params) {
            MusicProvider provider = providerReference.get();
            provider.retrieveMedia();
//            provider.addMedia();
            return provider.mCurrentState;
        }

        @Override
        protected void onPostExecute(State current) {
            if (callback != null) {
                callback.onMusicCatalogReady(current == State.INITIALIZED);
            }
        }
    }

    private synchronized void retrieveMedia() {
        try {
            mCurrentState = State.INITIALIZING;

            Iterator<MediaMetadataCompat> tracks = mSource.iterator();
            while (tracks.hasNext()) {
                MediaMetadataCompat item = tracks.next();
                String musicId = item.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID);
                mMusicListById.put(musicId, new MutableMediaMetadata(musicId, item));
            }
            mCurrentState = State.INITIALIZED;
        } finally {
            if (mCurrentState != State.INITIALIZED) {
                // Something bad happened, so we reset state to NON_INITIALIZED to allow
                // retries (eg if the network connection is temporary unavailable)
                mCurrentState = State.NON_INITIALIZED;
            }
        }
    }

    public List<MediaBrowserCompat.MediaItem> getChildren(String mediaId, Resources resources) {
        List<MediaBrowserCompat.MediaItem> mediaItems = new ArrayList<>();

//        if (!MusicIDHelper.isBrowseable(mediaId)) {
//            return mediaItems;
//        }

//        if (MEDIA_ID_ROOT.equals(mediaId)) {
////            mediaItems.add(createBrowsableMediaItemForRoot(resources));
//
//        } else if (MEDIA_ID_MUSICS_BY_GENRE.equals(mediaId)) {
////            for (String genre : getGenres()) {
////                mediaItems.add(createBrowsableMediaItemForGenre(genre, resources));
////            }
//
//        } else if (mediaId.startsWith(MEDIA_ID_MUSICS_BY_GENRE)) {
//            String genre = MusicIDHelper.getHierarchy(mediaId)[1];
        for (MediaMetadataCompat metadata : getAllMusics()) {
            mediaItems.add(createMediaItem(metadata));
        }

//        } else {
////            LogHelper.w(TAG, "Skipping unmatched mediaId: ", mediaId);
//        }
        return mediaItems;
    }

    /**
     * Get an iterator over a shuffled collection of all songs
     * 获取 存储了所有音乐的列表 随机打乱顺序后的迭代器
     */
    public Iterable<MediaMetadataCompat> getShuffledMusic() {
        if (mCurrentState != State.INITIALIZED) {
            return Collections.emptyList();
        }
        List<MediaMetadataCompat> shuffled = new ArrayList<>(mMusicListById.size());
        for (MutableMediaMetadata mutableMetadata : mMusicListById.values()) {
            shuffled.add(mutableMetadata.metadata);
        }
        Collections.shuffle(shuffled);//打乱列表的顺序
        return shuffled;
    }

    private MediaBrowserCompat.MediaItem createMediaItem(MediaMetadataCompat metadata) {
        // Since mediaMetadata fields are immutable, we need to create a copy, so we
        // can set a hierarchy-aware mediaID. We will need to know the media hierarchy
        // when we get a onPlayFromMusicID call, so we can create the proper queue based
        // on where the music was selected from (by artist, by genre, random, etc)
        //我们可以基于在音乐类型的选择（由艺术家、流派、随机、等）构建适当的音乐队列
//        String genre = metadata.getString(MediaMetadataCompat.METADATA_KEY_GENRE);
//        String hierarchyAwareMediaID = MusicIDHelper.createMediaID(
//                metadata.getDescription().getMediaId(), MEDIA_ID_MUSICS_BY_GENRE, genre);
//        MediaMetadataCompat copy = new MediaMetadataCompat.Builder(metadata)
//                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, hierarchyAwareMediaID)
//                .build();
//        return new MediaBrowserCompat.MediaItem(copy.getDescription(),
//                MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);

        return new MediaBrowserCompat.MediaItem(metadata.getDescription(),
                MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);

    }
}
