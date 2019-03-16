package com.levelup.Questrip.intro;

/**
 * 로그인 프로세스를 담당하는 클래스입니다.
 * 로그인을 시도하여, 그 결과에 맞는 이벤트를 수행합니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: 로그인을 시도합니다.
 * 로그인에 성공한 경우, 사용자의 정보는 전역 변수로 관리합니다.
 */
final class LoginManager {

    /** 로그인을 시도하여, 그 결과를 이벤트로 반환합니다.
     *
     * @param success: 로그인에 성공한 경우의 이벤트입니다.
     * @param new_user: 로그인에 성공했으나, 새로운 유저인 경우의 이벤트입니다.
     * @param fail: 로그인에 실패한 경우의 이벤트입니다.
     */
    static void tryLogin(Runnable success, Runnable new_user, Runnable fail) {
        // TODO to be implemented.
        success.run();
    }
}
