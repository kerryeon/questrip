package com.levelup.Questrip.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import java.io.InputStream;
import java.net.URL;

public final class ImageManager {

    /**
     * 서버로부터 이미지를 다운로드합니다.
     * @param path 서버의 요청 URI.
     * @param onSuccess 다운로드에 성공한 경우의 이벤트입니다.
     * @param onFailure 다운로드에 실패한 경우의 이벤트입니다.
     */
    public static void load(String path, OnSuccess onSuccess,
                            ClientRequestAsync.OnFailure onFailure) {
        URL url = ClientHeader.composeURL(ClientPath.PATH_IMAGE + path);
        new ImageManagerAsync(onSuccess, onFailure).execute(url);
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
     * 서버에서 직접적으로 이미지를 불러오는 클래스입니다.
     *
     * 담당자: 김호
     *
     * 역할: 서버와 비동기 통신하고, 그 결과를 넘깁니다.
     */
    private static class ImageManagerAsync extends AsyncTask<URL, Void, Bitmap> {

        OnSuccess onSuccess;
        ClientRequestAsync.OnFailure onFailure;

        /**
         * 객체를 생성합니다.
         * @param onSuccess 요청에 성공한 경우의 이벤트입니다.
         * @param onFailure 요청에 실패한 경우의 이벤트입니다.
         */
        private ImageManagerAsync(OnSuccess onSuccess, ClientRequestAsync.OnFailure onFailure) {
            this.onSuccess = onSuccess;
            this.onFailure = onFailure;
        }

        /**
         * 서버로부터 이미지를 다운로드합니다.
         * @param urls 이미지 경로
         * @return 서버로부터 수신받은 이미지입니다.
         */
        protected Bitmap doInBackground(URL... urls) {
            URL imageURL = urls[0];
            Bitmap bitmap;
            try {
                InputStream in = imageURL.openStream();
                bitmap = BitmapFactory.decodeStream(in);
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

    }

}
