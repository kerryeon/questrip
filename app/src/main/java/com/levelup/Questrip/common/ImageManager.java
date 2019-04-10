package com.levelup.Questrip.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.levelup.Questrip.net.ClientHeader;
import com.levelup.Questrip.net.ClientPath;

import java.io.ByteArrayOutputStream;

/**
 * 잡다한 이미지 연산을 처리합니다.
 *
 * 담당자: 김호
 *
 * 역할: 이미지 불러오기, 압축 등 이미지 관련된 연산을 수행합니다.
 */
public final class ImageManager {

    /**
     * 이미지를 JPEG 압축합니다.
     * @param bitmap 이미지
     * @return 압축한 이미지
     */
    public static byte[] getBytes(final Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * 이미지를 불러와 레이아웃에 띄웁니다.
     * @param activity 현재 액티비티
     * @param imageView 이미지 레이아웃
     * @param path 이미지 경로
     */
    public static void load(Activity activity, final ImageView imageView, final String path) {
        Glide.with(activity)
                .load(ClientHeader.composeURL(ClientPath.PATH_IMAGE + path).toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

}
