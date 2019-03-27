package com.levelup.Questrip.login;

import com.levelup.Questrip.common.Bootstrapper;

/**
 * 외부 API 를 통한 로그인 프로세스를 담당하는 클래스입니다.
 * 로그인을 시도하여, 그 결과에 맞는 이벤트를 수행합니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: 외부 API 를 통해 로그인을 시도합니다.
 * 로그인에 성공한 경우, 사용자의 정보는 전역 변수로 관리합니다.
 */
public abstract class LoginManagerBase {

    /**
     * 외부 API 를 통해 로그인 요청을 합니다.
     * 획득한 엑세스 토큰은 서버에 전달하게 됩니다.
     * @param activity: 앱을 시작하면 가장 먼저 실행되는 액티비티입니다.
     * @param success: 로그인에 성공한 경우의 이벤트입니다.
     * @param failure: 로그인에 실패한 경우의 이벤트입니다.
     */
    public abstract void tryLogin(Bootstrapper activity, Runnable success, OnFailure failure);

    /**
     * 사용자의 엑세스 토큰이 유효하여 현재 로그인된 상태인지 확인합니다.
     * @return 토큰이 유효하다면 true 를 반환합니다.
     */
    protected abstract boolean isLoggedIn();

    /**
     * 사용자의 엑세스 토큰을 "강제로" 반환합니다.
     * @return 엑세스 토큰. 로그인한 상태가 아니라면 빈 문자열 ""을 반환합니다.
     */
    public abstract String getAccessToken();

    /**
     * 로그인에 실패한 경우를 나열합니다.
     *
     * 담당자: 김호
     */
    public enum Failed {
        LOGIN_FAILED,       // 로그인에 실패한 경우.
        USER_CANCELED,      // 사용자가 로그인을 취소한 경우.
        NETWORK_FAILURE,    // 불안정한 네트워크.
    }

    /**
     * 로그인에 실패한 경우의 이벤트입니다.
     *
     * 담당자: 김호
     */
    @FunctionalInterface
    public interface OnFailure {
        void run(Failed failed);
    }

}
