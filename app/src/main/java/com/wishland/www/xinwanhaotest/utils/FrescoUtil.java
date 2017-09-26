package com.wishland.www.xinwanhaotest.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;

/**
 * fresco工具类
 */
public class FrescoUtil {
    private static ImagePipeline imagePipeline = Fresco.getImagePipeline();

    public static ImagePipeline getImagePipeline() {
        return imagePipeline;
    }

    /**
     * 加载网络gif图片
     *
     * @param simpleDraweeView
     * @param gifUrl
     */
    public static void loadGifPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String gifUrl) {
        String replace = gifUrl.replace(" ", "%20").replace("&", "_");
        Uri uri = Uri.parse(replace);
        simpleDraweeView.setImageURI(uri);

        AbstractDraweeController build = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setAutoPlayAnimations(true)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(build);
    }


    /**
     * 加载网络非动图
     *
     * @param simpleDraweeView
     * @param imgUrl
     */
    public static void loadPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String imgUrl) {
        simpleDraweeView.setImageURI(imgUrl);
    }
}
