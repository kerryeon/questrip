package com.levelup.Questrip.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

import java.io.InputStream;
import java.net.URL;

public final class ImageManager {

    /**
     * 서버로부터 이미지를 다운로드합니다.
     * 만약 이미 다운로드한 이미지라면, 캐시에서 이를 불러옵니다.
     * @param path 서버의 요청 URI.
     * @param onSuccess 다운로드에 성공한 경우의 이벤트입니다.
     * @param onFailure 다운로드에 실패한 경우의 이벤트입니다.
     */
    public static void load(String path, OnSuccess onSuccess,
                            ClientRequestAsync.OnFailure onFailure) {
        new ImageDownloaderAsync(onSuccess, onFailure).execute(path);
    }

    /**
     * 이미지를 성공적으로 다운로드한 경우의 이벤트입니다.
     *
     * 담당자: 김호
     */
    @FunctionalInterface
    public interface OnSuccess {
        /**
         * 서버로부터 전달받은 이미지를 처리합니다.
         * @param bitmap: 이미지
         */
        void run(Bitmap bitmap);
    }

    /**
     * 서버에서 직접적으로 이미지를 불러오고 캐시에 저장하는 클래스입니다.
     *
     * 담당자: 김호
     *
     * 역할: 서버와 비동기 통신하고, 그 결과를 넘깁니다.
     * 또한, 이미지 캐시를 관리합니다.
     */
    private static class ImageDownloaderAsync extends AsyncTask<String, Void, Bitmap> {

        private static LruCache<String, Bitmap> cache;

        private OnSuccess onSuccess;
        ClientRequestAsync.OnFailure onFailure;

        /**
         * 객체를 생성합니다.
         * @param onSuccess 요청에 성공한 경우의 이벤트입니다.
         * @param onFailure 요청에 실패한 경우의 이벤트입니다.
         */
        private ImageDownloaderAsync(OnSuccess onSuccess, ClientRequestAsync.OnFailure onFailure) {
            this.onSuccess = onSuccess;
            this.onFailure = onFailure;
            initCache();
        }

        /**
         * 캐시를 초기화합니다.
         */
        private void initCache() {
            // 캐시가 이미 초기화되었다면, 다시 진행하지 않습니다.
            if (cache != null) return;

            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;

            cache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(@NonNull String key, @NonNull Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    return bitmap.getByteCount() / 1024;
                }
            };
        }

        /**
         * 서버로부터 이미지를 다운로드합니다.
         * @param paths 이미지 경로 URI.
         * @return 서버로부터 수신받은 이미지입니다.
         */
        protected Bitmap doInBackground(String... paths) {
            final String imagePath = paths[0];
            final URL imageURL = ClientHeader.composeURL(ClientPath.PATH_IMAGE + imagePath);
            // 먼저, 캐시에서 이미지를 찾습니다.
            Bitmap bitmap = getBitmapFromCache(imagePath);
            if (bitmap != null) return bitmap;
            // 캐시에 이미지가 없는 경우, 다운로드를 시작합니다.
            try {
                InputStream in = imageURL.openStream();
                bitmap = BitmapFactory.decodeStream(in);
                // 캐시에 이미지를 저장합니다.
                addBitmapToCache(imagePath, bitmap);
            } catch (Exception e) {
                // 다운로드 실패
                e.printStackTrace();
                onFailure.run(ClientRequestAsync.Failed.NETWORK_FAILURE);
                return null;
            }
            return bitmap;
        }

        /**
         * 요청이 끝난 경우의 이벤트입니다.
         * @param result: 결과값. 실패한 경우 null 입니다.
         */
        protected void onPostExecute(@Nullable Bitmap result) {
            if (result != null) onSuccess.run(result);
        }

        /**
         * 캐시로부터 이미지를 찾습니다.
         * @param key 이미지 식별키
         * @return 이미지. 찾을 수 없으면 null 을 반환합니다.
         */
        @Nullable
        private Bitmap getBitmapFromCache(String key) {
            return cache.get(key);
        }

        /**
         * 캐시에 이미지를 추가합니다.
         * @param key 이미지 식별키
         * @param bitmap 이미지
         */
        private void addBitmapToCache(String key, Bitmap bitmap) {
            if (getBitmapFromCache(key) == null)
                cache.put(key, bitmap);
        }

    }

}
