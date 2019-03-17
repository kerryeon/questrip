package com.levelup.Questrip.net;

/**
 * 서버에 매번 전송할 데이터들을 관리하는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 서버에 매번 전송할 데이터들을 저장하고 관리합니다.
 */
public final class ClientHeader {

    private static final String HOST = "감자리아.kr";
    private static final char PORT = 9494;

    /**
     * 서버의 도메인을 반환합니다.
     * @return 서버의 도메인
     */
    static String getHost() {
        return HOST;
    }

    /**
     * 서버의 포트 번호를 반환합니다.
     * @return 사용자의 토큰
     */
    static char getPort() {
        return PORT;
    }

}
