package com.levelup.Questrip.net;

import com.levelup.Questrip.utils.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 서버와 직접적으로 통신하는 클래스입니다.
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
     * @param opCode: 어떤 전송인지를 알리는 옵코드.
     * @param input: 전송할 입력값입니다.
     * @param success: 서버로부터 성공적으로 결과값을 받은 경우의 이벤트입니다.
     * @param failure: 전송에 실패한 경우의 이벤트입니다.
     */
    public static void send(ClientOpCode opCode, final JSONObject input,
                            OnSuccess success, OnFailure failure) {
        JSONObject data = getData(opCode, input);
    }

    /**
     * 전송할 데이터를 가공합니다.
     * @param opCode: 어떤 전송인지를 알리는 옵코드.
     * @param input: 전송할 입력값입니다.
     * @return 실질적으로 전송할 데이터입니다.
     */
    private static JSONObject getData(ClientOpCode opCode, final JSONObject input) {
        JSONObject data = new JSONObject();
        try {
            data.put("op_code", opCode.getValue());
            data.put("token", LoginManager.getAccessToken());
            data.put("input", input);
        } catch (JSONException e) {
            // 실질적으로 에러가 발생하는 경우는 없습니다.
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 전송에 성공하여 그 결과를 받았을 경우의 이벤트입니다.
     *
     * 담당자: 김호
     */
    @FunctionalInterface
    interface OnSuccess {
        /**
         * 서버로부터 전달받은 결과값을 처리합니다.
         * @param response: 결과값
         */
        void run(ClientResponse response);
    }

    /**
     * 전송에 실패한 경우를 나열합니다.
     *
     * 담당자: 김호
     */
    enum Failed {
        INTERNAL,
        NETWORK_FAILURE,
    }

    /**
     * 전송에 실패한 경우의 이벤트입니다.
     *
     * 담당자: 김호
     */
    @FunctionalInterface
    interface OnFailure {
        /**
         * 전송에 실패한 경우를 처리합니다.
         * @param failed: 전송에 실패한 이유
         */
        void run(Failed failed);
    }

}
