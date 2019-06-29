package com.justnd.octoryeclient.music.model;

import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Justiniano
 * @Description:
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/6/14 0014 下午 9:38
 */
public class SimpleMusicProviderSource implements MusicProviderSource {

//    @Override
//    public Iterator<MediaMetadataCompat> iterator() {
////            int slashPos = CATALOG_URL.lastIndexOf('/');
////            String path = CATALOG_URL.substring(0, slashPos + 1);
////            JSONObject jsonObj = fetchJSONFromUrl(CATALOG_URL);//下载JSON文件
////            Log.i("Json", jsonObj.toString());
//        ArrayList<MediaMetadataCompat> tracks = new ArrayList<>();
//        tracks.add(buildFromBodyBean(mBodyBean));
//        return tracks.iterator();
//    }
//
//    private MediaMetadataCompat buildFromBodyBean(RecommendInfo.ResultBean.BodyBean body) {
//        String id = String.valueOf(body.getAudio_url().hashCode());
//        String source = body.getAudio_url();
//        String album = body.getAudio_album();
//        String artist = body.getAudio_author();
//        String iconUrl = body.getAudio_cover();
//        String title = body.getMusic_name();
//
//        // Adding the music source to the MediaMetadata (and consequently using it in the
//        // mediaSession.setMetadata) is not a good idea for a real world music app, because
//        // the session metadata can be accessed by notification listeners. This is done in this
//        // sample for convenience only.
//        //noinspection ResourceType
//        // 在现实的music app中往MediaMetadata添加music source（因此在mediaSession.setMetadata中使用）并非是一个好主意
//        // 因为session metadata可以被notification监听者访问
//        // 为了方便起见，请按本示例进行操作
//        return new MediaMetadataCompat.Builder()
//                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
//                .putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, source)
//                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
//                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
//                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, iconUrl)
//                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
//                .build();
//    }

//    private List<MediaMetadataCompat> mData = new ArrayList<>();
    private  static SimpleMusicProviderSource sourceInstance;

    private SimpleMusicProviderSource() {}

    /**
     * @Description: 设置为单例模式，提供唯一数据访问对象，以防止MusicService服务被意外
     *              销毁后，丢失之前的音乐库信息
     * @param
     * @return
     * @throws
     * @author Justiniano
     */
    public static SimpleMusicProviderSource getInstance() {
        if (sourceInstance == null) {
            sourceInstance = new SimpleMusicProviderSource();
        }

        return  new SimpleMusicProviderSource();
    }

    private Map<String, MediaMetadataCompat> mData = new HashMap<>();

    public void add(String title, String album, String artist, String genre, String source,
                    String iconUrl, String duration) {
        String id = String.valueOf(source.hashCode());
        long durationMs = Long.valueOf(duration) * 1000;

        if (!mData.containsKey(id)) {
            mData.put(id, new MediaMetadataCompat.Builder()
                    .putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, source)
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                    .putString(MediaMetadataCompat.METADATA_KEY_GENRE, genre)
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, iconUrl)
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, durationMs)
                    .build());
        }
    }

    @Override
    public Iterator<MediaMetadataCompat> iterator() {
        return mData.values().iterator();
    }

    @Override
    public int sourceSize() {
        return mData.size();
    }

    @Override
    public boolean isSourceExist(String audioURL) {
        String mediaId = String.valueOf(audioURL.hashCode());
        return mData.containsKey(mediaId);
    }
}
