package com.levelup.Questrip.intro;

/**
 * 회원가입 프로세스를 담당하는 클래스입니다.
 * 회원가입 시도하여, 그 결과에 맞는 이벤트를 수행합니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: 로그인을 시도합니다.
 * 로그인에 성공한 경우, 사용자의 정보는 전역 변수로 관리합니다.
 */
final class SignUpManager {

    /**
     * 회원가입에 실패한 경우를 나열합니다.
     *
     * 담당자: 김호
     */
    enum Failed {
        WRONG_NICKNAME,     // 닉네임이 잘못된 경우.
        WRONG_BIRTHDAY,     // 생년월일이 잘못된 경우.
        WRONG_ADDRESS,      // 주소가 잘못된 경우.
        INTERNAL,           // 서버 내부 오류.
        NETWORK_FAILURE,    // 네트워크 오류. WIFI, LTE 등에 접속되지 않은 경우.
    }

    /**
     * 로그인에 실패한 경우의 이벤트입니다.
     *
     * 담당자: 김호
     */
    @FunctionalInterface
    interface OnFailure {
        /**
         * 로그인에 실패한 경우를 처리합니다.
         * @param failed: 로그인에 실패한 이유
         */
        void run(Failed failed);
    }

    /**
     * 회원가입을 시도합니다.
     * @param model: 회원가입할 유저의 정보를 가지고 있습니다.
     * @param success: 회원가입에 성공한 경우의 이벤트입니다.
     * @param failure: 회원가입에 실패한 경우의 이벤트입니다.
     */
    static void trySignUp(final SignUpModel model, Runnable success, OnFailure failure) {
        // TODO to be implemented.
        success.run();
    }

}
