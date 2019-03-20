package com.levelup.Questrip.net;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 서버와 직접적으로 통신하는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 서버와 비동기 통신하고, 그 결과를 넘깁니다.
 */
public final class ClientRequestAsync extends AsyncTask<
        ClientRequestAsync.ClientRequestFormat, Void,
        ClientRequestAsync.ClientResponseFormat> {

    private OnSuccess success;
    private OnFailure failure;

    /**
     * 객체를 생성합니다.
     * @param success: 요청에 성공한 경우의 이벤트입니다.
     * @param failure: 요청에 실패한 경우의 이벤트입니다.
     */
    ClientRequestAsync(OnSuccess success, OnFailure failure) {
        super();
        this.success = success;
        this.failure = failure;
    }

    /**
     * 서버에 데이터를 전송하고, 그 결과를 받습니다.
     * @param formats: 서버에 전송할 데이터 양식입니다.
     *               단 1개의 데이터만 유효합니다.
     * @return 서버로부터 수신받은 데이터입니다.
     */
    @Override
    protected ClientResponseFormat doInBackground(ClientRequestFormat... formats) {
        String data = formats[0].data;
        URL url = ClientHeader.composeURL(formats[0].path);

        try {
            // 연결을 시도합니다.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            ClientHeader.attachHeaderJson(connection);

            // 데이터를 송수신합니다.
            sendData(connection, data);
            String result = recvData(connection);

            // 수신받은 데이터를 검사합니다.
            if (result.length() == 0) return new ClientResponseFormat(Failed.INTERNAL);
            // 수신이 성공적으로 완료된 경우, 결과를 반환합니다.
            return new ClientResponseFormat(result);

        } catch (IOException e) {
            // 네트워크 연결이 불안정한 경우입니다.
            // TODO To be implemented.
            e.printStackTrace();
        } catch (NullPointerException e) {
            // URL 이 잘못된 경우 등입니다.
            // 실질적으로 발생할 가능성은 없습니다.
            e.printStackTrace();
        }
        return new ClientResponseFormat(Failed.NETWORK_FAILURE);
    }

    /**
     * 요청이 끝난 경우의 이벤트입니다.
     * @param result: 결과값. 실패한 경우 null 입니다.
     */
    @Override
    protected void onPostExecute(ClientResponseFormat result) {
        if (result.isSuccess()) success.run(result.data);
        else failure.run(result.failed);
    }

    /**
     * 서버에 RAW 데이터를 전송합니다.
     * @param connection: 연결을 담당하는 객체
     * @param data: 전송할 RAW 데이터
     * @throws IOException 네트워크 이상으로 전송에 실패한 경우
     */
    private static void sendData(HttpURLConnection connection, String data) throws IOException {
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(data);
        wr.flush();
        wr.close();
    }

    /**
     * 서버로부터 RAW 데이터를 수신받습니다.
     * @param connection: 연결을 담당하는 객체
     * @return 수신받은 RAW 데이터
     * @throws IOException 네트워크 이상으로 수신에 실패한 경우
     */
    private static String recvData(HttpURLConnection connection) throws IOException {
        // 자료형을 준비합니다.
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        StringBuilder result = new StringBuilder();
        // 읽어들입니다.
        String buffer;
        while ((buffer = reader.readLine()) != null) {
            result.append(buffer);
        }
        reader.close();
        // 읽어들인 값을 반환합니다.
        return result.toString();
    }

    /**
     * 서버에 전송할 데이터 양식입니다.
     *
     * 담당자: 김호
     */
    static class ClientRequestFormat {

        private String path;
        private String data;

        /**
         * 서버에 전송할 RAW 데이터 양식을 생성합니다.
         * @param path: 전송 URI
         * @param data: 전송 RAW 데이터
         */
        ClientRequestFormat(String path, String data) {
            this.path = path;
            this.data = data;
        }

        /**
         * 서버에 전송할 JSON 데이터 양식을 생성합니다.
         * @param path: 전송 URI
         * @param data: 전송 JSON 데이터
         */
        ClientRequestFormat(String path, JSONObject data) {
            this(path, ClientHeader.composeData(data).toString());
        }

    }

    /**
     * 서버로부터 수신받는 데이터 양식입니다.
     *
     * 담당자: 김호
     */
    static class ClientResponseFormat {

        private String data;
        private Failed failed;

        /**
         * 서버로부터 수신받는 RAW 데이터 양식을 생성합니다.
         * @param data: 수신받은 RAW 데이터
         */
        private ClientResponseFormat(String data) {
            this.data = data;
            this.failed = null;
        }

        /**
         * 서버로부터 수신받지 못했을 경우의 데이터 양식을 생성합니다.
         * @param failed: 실패 이유
         */
        private ClientResponseFormat(Failed failed) {
            this.data = null;
            this.failed = failed;
        }

        /**
         * 수신이 정상적이었는지 판별합니다.
         * @return 수신이 정상적이었다면 true 를 반환합니다.
         */
        private boolean isSuccess() {
            return this.data != null;
        }

    }

    /**
     * 전송에 성공하여 그 결과를 받았을 경우의 이벤트입니다.
     *
     * 담당자: 김호
     */
    @FunctionalInterface
    public interface OnSuccess {
        /**
         * 서버로부터 전달받은 결과값을 처리합니다.
         * @param response: 결과값
         */
        void run(String response);
    }

    /**
     * 전송에 실패한 경우를 나열합니다.
     *
     * 담당자: 김호
     */
    public enum Failed {
        // 요청이 거절당하거나, 처리할 수 없는 경우.
        INTERNAL,
        // 네트워크 연결이 불안정한 경우.
        NETWORK_FAILURE,
    }

    /**
     * 전송에 실패한 경우의 이벤트입니다.
     *
     * 담당자: 김호
     */
    @FunctionalInterface
    public interface OnFailure {
        /**
         * 전송에 실패한 경우를 처리합니다.
         * @param failed: 전송에 실패한 이유
         */
        void run(Failed failed);
    }

}
