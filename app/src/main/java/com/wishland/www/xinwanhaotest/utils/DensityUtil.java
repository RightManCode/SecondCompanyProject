package com.wishland.www.xinwanhaotest.utils;

/**
 * Created by Administrator on 2016/11/30 0030.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 常用单位转换的辅助类
 */
public class DensityUtil {

    private DensityUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }


    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static int px2dp(Context context, int pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxVal / scale);
    }


    /**
     * 将图片等比例缩放 setAdjustViewBounds setMaxWidth setMaxWidth必须同时设置才有效
     *
     * @param context
     * @param view
     *            父容器
     * @param image
     *            图片控件
     * @param source
     *            图片资源
     */
    public static void setImageViewMathParent(Activity context, LinearLayout view,
                                              ImageView image, int source) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                source);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        float scalew = (float) displayMetrics.widthPixels
                / (float) bitmap.getWidth();
        image.setScaleType(ImageView.ScaleType.MATRIX);
        Matrix matrix = new Matrix();
        image.setAdjustViewBounds(true);
        if (displayMetrics.widthPixels < bitmap.getWidth()) {
            matrix.postScale(scalew, scalew);
        } else {
            matrix.postScale(1 / scalew, 1 / scalew);
        }
        image.setMaxWidth(displayMetrics.widthPixels);
        float ss = displayMetrics.heightPixels > bitmap.getHeight() ? displayMetrics.heightPixels
                : bitmap.getHeight();
        image.setMaxWidth((int) ss);
        view.addView(image);
        if (bitmap != null && bitmap.isRecycled()) {
            bitmap.recycle();
        }

    }
}
