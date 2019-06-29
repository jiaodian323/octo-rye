package com.justnd.octoryeclient.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

import com.bumptech.glide.Glide;

/**
 * @author Justiniano
 * @Description:
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/6/27 0027 上午 12:18
 */
public class ImageUtil {
    /**
     * @param
     * @return
     * @throws
     * @Description: 从URL地址获取其Bitmap对象
     * @author Justiniano
     */
    public static Bitmap genBitmapFromUrl(Context context, Uri url, int width, int height) {
        Bitmap bitmap;
        try {
            bitmap = Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(width, height)
                    .get();
        } catch (Exception e) {
            bitmap = null;
        }

        return bitmap;
    }

    /** 
    * @Description: 针对Bitmap做高斯模糊
    * @param
    * @return 
    * @throws 
    * @author Justiniano
    */
    public static Bitmap rsBlur(Context context,Bitmap source,int radius,float scale){

//        source = RGB565toARGB888(source);
        int width = Math.round(source.getWidth() * scale);
        int height = Math.round(source.getHeight() * scale);

//        Bitmap outBitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
//                Bitmap.Config.ARGB_8888);
        Bitmap outputBmp = Bitmap.createScaledBitmap(source,width,height,false);
        Log.i(DebugTagUtil.FULLSCREEN_ACTIVITY_TAG, "out Bitmap config:" + outputBmp.getConfig().name());
//        outputBmp.setConfig(Bitmap.Config.ARGB_8888);
//        outputBmp.reconfigure(width, height, Bitmap.Config.ARGB_8888);
//        outputBmp = RGB565toARGB888(outputBmp);

        RenderScript renderScript =  RenderScript.create(context);

        // Allocate memory for Renderscript to work with
        final Allocation input = Allocation.createFromBitmap(renderScript,source);
        final Allocation output = Allocation.createTyped(renderScript,input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);
        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);

        // Copy the output to the blurred bitmap
        output.copyTo(outputBmp);

        renderScript.destroy();
        return outputBmp;
    }

    /** 
    * @Description: 
    * @param 
    * @return 
    * @throws 
    * @author Justiniano
    */
    private static Bitmap RGB565toARGB888(Bitmap img) {
        int numPixels = img.getWidth()* img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    /**
    * @Description: 按宽/高缩放图片到指定大小并进行裁剪得到中间部分图片
    * @param bitmap 源bitmap
     *@param  w 缩放后指定的宽度
     *@param  h 缩放后指定的高度
    * @return
    * @throws
    * @author Justiniano
    */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth, scaleHeight;
        int x, y;
        Bitmap newbmp;
        Matrix matrix = new Matrix();
        if (width > height) {
            scaleWidth = ((float) h / height);
            scaleHeight = ((float) h / height);
            x = (width - w * height / h) / 2;// 获取bitmap源文件中x做表需要偏移的像数大小
            y = 0;
        } else if (width < height) {
            scaleWidth = ((float) w / width);
            scaleHeight = ((float) w / width);
            x = 0;
            y = (height - h * width / w) / 2;// 获取bitmap源文件中y做表需要偏移的像数大小
        } else {
            scaleWidth = ((float) w / width);
            scaleHeight = ((float) w / width);
            x = width * 3 / 8 ;
            y = height * 3 / 8;
        }
        matrix.postScale(scaleWidth, scaleHeight);
        try {
            newbmp = Bitmap.createBitmap(bitmap, x, y, (width - x * 2),  (height - y * 2), null, true);// createBitmap()方法中定义的参数x+width要小于或等于bitmap.getWidth()，y+height要小于或等于bitmap.getHeight()
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return newbmp;
    }
}
