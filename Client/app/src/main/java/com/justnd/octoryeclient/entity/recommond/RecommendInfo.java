package com.justnd.octoryeclient.entity.recommond;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JD
 * @ClassName: HotPostBean
 * @Description: TODO
 * @date 2019年1月7日 下午5:44:16
 */
public class RecommendInfo {
    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String style;
        private HeadBean head;
        private ArrayList<BodyBean> body;

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public HeadBean getHead() {
            return head;
        }

        public void setHead(HeadBean head) {
            this.head = head;
        }

        public ArrayList<BodyBean> getBody() {
            return body;
        }

        public void setBody(ArrayList<BodyBean> body) {
            this.body = body;
        }

        public static class HeadBean {
            private String param;
            @SerializedName("goto")
            private String gotoX;
            private String title;
            private int count;

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getGotoX() {
                return gotoX;
            }

            public void setGotoX(String gotoX) {
                this.gotoX = gotoX;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }

        public static class BodyBean implements Parcelable {
            private String style;
            @SerializedName("goto")
            private String gotoX;
            private int width;
            private int height;
            private String danmaku;
            private String up;
            @SerializedName("up_face")
            private String upFace;
            private int online;
            private String desc1;
            private int param;
            private String title;
            private String authorName;
            private String cover;
            private String extract;
            private int likeNum;
            private int commentNum;
            private int pageViewNum;
            private String audio_url;
            private String audio_platform;
            private String music_name;
            private String audio_platform_icon;
            private String audio_platform_name;
            private String audio_author;
            private String audio_album;
            private String audio_cover;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(audio_url);
                dest.writeString(audio_platform);
                dest.writeString(music_name);
                dest.writeString(audio_platform_icon);
                dest.writeString(audio_platform_name);
                dest.writeString(audio_author);
                dest.writeString(audio_album);
                dest.writeString(audio_cover);

            }

            public static final Parcelable.Creator<BodyBean> CREATOR = new Creator<BodyBean>() {
                @Override
                public BodyBean[] newArray(int size) {
                    return new BodyBean[size];
                }

                @Override
                public BodyBean createFromParcel(Parcel in) {
                    BodyBean body = new BodyBean();
                    body.audio_url = in.readString();
                    body.audio_platform = in.readString();
                    body.music_name = in.readString();
                    body.audio_platform_icon = in.readString();
                    body.audio_platform_name = in.readString();
                    body.audio_author = in.readString();
                    body.audio_album = in.readString();
                    body.audio_cover = in.readString();
                    return body;
                }

            };

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getParam() {
                return param;
            }

            public void setParam(int param) {
                this.param = param;
            }

            public String getGotoX() {
                return gotoX;
            }

            public void setGotoX(String gotoX) {
                this.gotoX = gotoX;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getPageViewNum() {
                return pageViewNum;
            }

            public void setPageViewNum(int play) {
                this.pageViewNum = play;
            }

            public String getDanmaku() {
                return danmaku;
            }

            public void setDanmaku(String danmaku) {
                this.danmaku = danmaku;
            }

            public String getUp() {
                return up;
            }

            public void setUp(String up) {
                this.up = up;
            }

            public String getUpFace() {
                return upFace;
            }

            public void setUpFace(String upFace) {
                this.upFace = upFace;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public String getDesc1() {
                return desc1;
            }

            public void setDesc1(String desc1) {
                this.desc1 = desc1;
            }

            /**
             * @return the like
             */
            public int getLikeNum() {
                return likeNum;
            }

            /**
             * @param like the like to set
             */
            public void setLikeNum(int like) {
                this.likeNum = like;
            }

            /**
             * @return the comment
             */
            public int getCommentNum() {
                return commentNum;
            }

            /**
             * @param comment the comment to set
             */
            public void setCommentNum(int comment) {
                this.commentNum = comment;
            }

            /**
             * @return the authorName
             */
            public String getAuthorName() {
                return authorName;
            }

            /**
             * @param authorName the authorName to set
             */
            public void setAuthorName(String authorName) {
                this.authorName = authorName;
            }

            /**
             * @return the extract
             */
            public String getExtract() {
                return extract;
            }

            /**
             * @param extract the extract to set
             */
            public void setExtract(String extract) {
                this.extract = extract;
            }

            /**
             * @return the audio_url
             */
            public String getAudio_url() {
                return audio_url;
            }

            /**
             * @param audio_url the audio_url to set
             */
            public void setAudio_url(String audio_url) {
                this.audio_url = audio_url;
            }

            /**
             * @return the audio_platform
             */
            public String getAudio_platform() {
                return audio_platform;
            }

            /**
             * @param audio_platform the audio_platform to set
             */
            public void setAudio_platform(String audio_platform) {
                this.audio_platform = audio_platform;
            }

            /**
             * @return the music_name
             */
            public String getMusic_name() {
                return music_name;
            }

            /**
             * @param music_name the music_name to set
             */
            public void setMusic_name(String music_name) {
                this.music_name = music_name;
            }

            /**
             * @return the audio_platform_icon
             */
            public String getAudio_platform_icon() {
                return audio_platform_icon;
            }

            /**
             * @param audio_platform_icon the audio_platform_icon to set
             */
            public void setAudio_platform_icon(String audio_platform_icon) {
                this.audio_platform_icon = audio_platform_icon;
            }

            /**
             * @return the audio_platform_name
             */
            public String getAudio_platform_name() {
                return audio_platform_name;
            }

            /**
             * @param audio_platform_name the audio_platform_name to set
             */
            public void setAudio_platform_name(String audio_platform_name) {
                this.audio_platform_name = audio_platform_name;
            }

            /**
             * @return the audio_author
             */
            public String getAudio_author() {
                return audio_author;
            }

            /**
             * @param audio_author the audio_author to set
             */
            public void setAudio_author(String audio_author) {
                this.audio_author = audio_author;
            }

            /**
             * @return the audio_album
             */
            public String getAudio_album() {
                return audio_album;
            }

            /**
             * @param audio_album the audio_album to set
             */
            public void setAudio_album(String audio_album) {
                this.audio_album = audio_album;
            }

            /**
             * @return the audio_cover
             */
            public String getAudio_cover() {
                return audio_cover;
            }

            /**
             * @param audio_cover the audio_cover to set
             */
            public void setAudio_cover(String audio_cover) {
                this.audio_cover = audio_cover;
            }
        }
    }
}

