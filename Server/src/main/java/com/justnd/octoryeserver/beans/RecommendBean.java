/**   
* @Title: HotPostBean.java 
* @Package com.justnd.octoryeserver.vo 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年1月7日 下午5:44:16  
*/
package com.justnd.octoryeserver.beans;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/** 
* @ClassName: HotPostBean 
* @Description: TODO
* @author JD
* @date 2019年1月7日 下午5:44:16 
*  
*/
public class RecommendBean {
	private int code;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String type;
        private HeadBean head;
        private List<BodyBean> body;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public HeadBean getHead() {
            return head;
        }

        public void setHead(HeadBean head) {
            this.head = head;
        }

        public List<BodyBean> getBody() {
            return body;
        }

        public void setBody(List<BodyBean> body) {
            this.body = body;
        }

        public static class HeadBean {
            private String param;
            @SerializedName("goto")
            private String gotoX;
            private String style;
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

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
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

        public static class BodyBean {
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
        }
    }
}
