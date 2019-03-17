package com.levelup.Questrip.net;

public enum ClientOpCode {

    // 인트로 (로그인 및 회원가입)
    LOGIN(0x0010),
    SIGNUP(0x0011),

    // 옵코드 종료
    ;

    private final int value;

    /**
     * 실제 옵코드 값을 반환합니다.
     * @return 실제 옵코드
     */
    public final int getValue() {
        return value;
    }

    /**
     * 고유한 옵코드를 가진 객체를 생성합니다.
     * @param value: 고유한 옵코드.
     */
    ClientOpCode(int value) {
        this.value = value;
    }

}
