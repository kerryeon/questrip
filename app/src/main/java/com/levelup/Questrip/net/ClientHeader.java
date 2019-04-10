package com.levelup.Questrip.net;

import com.levelup.Questrip.common.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;

/**
 * 서버에 매번 전송할 데이터들을 관리하는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 서버에 매번 전송할 데이터들을 저장하고 관리합니다.
 */
public final class ClientHeader {

    private static final String PROTOCOL = "http";
    private static final String HOST = "203.255.3.181";
    private static final int PORT = 9494;

    /**
     * 전송할 데이터를 가공합니다.
     * @param input: 전송할 입력값입니다.
     * @return 실질적으로 전송할 데이터입니다.
     */
    static JSONObject composeData(final JSONObject input) {
        try {
            input.put("token", LoginManager.getAccessToken());
        } catch (JSONException e) {
            // 실질적으로 에러가 발생하는 경우는 없습니다.
            e.printStackTrace();
        }
        return input;
    }

    /**
     * 서버에 직접적으로 전송할 URL 을 생성합니다.
     * @param path: 요청 URI
     * @return 요청 URL
     */
    public static URL composeURL(String path) {
        try {
            return new URL(String.format(
                            Locale.US,
                            "%s://%s:%d%s",
                            PROTOCOL, HOST, PORT, path));
        } catch (MalformedURLException e) {
            // URL 형식이 잘못된 경우
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 패킷 헤더를 첨부합니다.
     * @param connection HTTP 커넥션
     */
    static void attachHeaderJson(HttpURLConnection connection) {
        try {
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
        } catch (ProtocolException e) {
            // Unreachable
            e.printStackTrace();
        }
    }

}
