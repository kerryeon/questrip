package com.levelup.Questrip.net;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 서버와 통신하는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 서버와 통신하고, 그 결과를 받습니다.
 *
 * 입출력 형식: JSON
 * @see <a href="https://en.wikipedia.org/wiki/JSON" >Documentation</>
 * @see <a href="https://developer.mozilla.org/ko/docs/Learn/JavaScript/Objects/JSON" >Learn JSON</>
 * @see <a href="https://huskdoll.tistory.com/38" />
 */
public final class ClientRequest {

    /**
     * 서버에 입력값을 전송합니다.
     * @param path: 서버의 요청 URI.
     * @param input: 전송할 입력값입니다.
     * @param success: 서버로부터 성공적으로 결과값을 받은 경우의 이벤트입니다.
     * @param failure: 전송에 실패한 경우의 이벤트입니다.
     */
    public static void send(String path, final JSONObject input, OnSuccess success,
                            ClientRequestAsync.OnFailure failure) {
        // 양식을 작성합니다.
        ClientRequestAsync.ClientRequestFormat format =
                new ClientRequestAsync.ClientRequestFormat(path, input);
        // 요청을 시작합니다.
        new ClientRequestAsync(r -> onReceivedJSON(r, success, failure), failure).execute(format);
    }

    /**
     * 서버에 상태를 알립니다.
     * @param path: 서버의 요청 URI.
     * @param success: 서버로부터 성공적으로 결과값을 받은 경우의 이벤트입니다.
     * @param failure: 전송에 실패한 경우의 이벤트입니다.
     */
    public static void send(String path, OnSuccess success,
                            ClientRequestAsync.OnFailure failure) {
        send(path, new JSONObject(), success, failure);
    }

    /**
     * 서버에 사용자의 요청을 전달합니다.
     * @param path 서버의 요청 URI.
     * @param input 전송할 입력값입니다.
     * @param success 요청이 성공적으로 처리된 경우의 이벤트입니다.
     * @param failure 전송에 실패한 경우의 이벤트입니다.
     */
    public static void send(String path, final JSONObject input, Runnable success,
                            ClientRequestAsync.OnFailure failure) {
        send(path, input, response -> onReceivedState(response, success, failure), failure);
    }

    /**
     * 서버에 사용자의 요청을 전달합니다.
     * @param path 서버의 요청 URI.
     * @param success 요청이 성공적으로 처리된 경우의 이벤트입니다.
     * @param failure 전송에 실패한 경우의 이벤트입니다.
     */
    public static void send(String path, Runnable success,
                            ClientRequestAsync.OnFailure failure) {
        send(path, response -> onReceivedState(response, success, failure), failure);
    }

    /**
     * 수신받은 값을 해석하고, 결과를 반환합니다.
     * @param response: 수신받은 RAW 데이터
     * @param success: 가공한 데이터를 반환합니다.
     * @param failure: 가공에 실패한 경우의 이벤트입니다.
     */
    private static void onReceivedJSON(String response, OnSuccess success,
                                ClientRequestAsync.OnFailure failure) {
        try {
            success.run(new JSONObject(response));
        } catch (JSONException e) {
            failure.run(ClientRequestAsync.Failed.INTERNAL);
        }
    }

    /**
     * 요청 결과를 해석하고, 상황에 맞는 이벤트를 호출합니다.
     * @param response 수신받은 JSON 데이터
     * @param success 요청에 성공한 경우의 이벤트입니다.
     * @param failure 요청이 실패한 경우의 이벤트입니다.
     */
    private static void onReceivedState(final JSONObject response, Runnable success,
                                        ClientRequestAsync.OnFailure failure) {
        try {
            // 요청에 성공한 경우
            if (response.getBoolean("accept")) success.run();
            // 요청이 실패한 경우
            else failure.run(ClientRequestAsync.Failed.REJECTED);
        } catch (JSONException e) {
            // Unreachable
            e.printStackTrace();
            failure.run(ClientRequestAsync.Failed.INTERNAL);
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
         * @param response: JSON 결과값
         */
        void run(JSONObject response);
    }

}
