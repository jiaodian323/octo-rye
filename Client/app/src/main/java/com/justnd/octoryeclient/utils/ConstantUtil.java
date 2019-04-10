package com.justnd.octoryeclient.utils;

public class ConstantUtil {
    /**
     * @Fields STATUS_CODE_FAIL : TODO 失败信息状态码
     */
    public static final int STATUS_CODE_FAIL = 0;

    /**
     * @Fields STATUS_CODE_SUCCESS : TODO 成功信息状态码
     */
    public static final int STATUS_CODE_SUCCESS = 1;

    public static final String TYPE_ARTICLE = "Article";
    public static final String TYPE_MUSIC = "Music";
    public static final String TYPE_VIDEO = "Video";
    public static final String TYPE_AUDIO = "Audio";

    public static final String EXTRA_CONTENT_TYPE = "extra_content_type";
    public static final String EXTRA_CONTENT_ID = "extra_content_id";
    public static final String EXTRA_IMG_URL = "extra_img_url";

    /**
     * @Fields: 手机号校验正则表达式
     */
    public static final String PHONE_REGEX = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|" +
            "(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

    /**
     * @Fields: 密码合法性校验正则表达式
     */
    public static final String PASS_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$";
}
