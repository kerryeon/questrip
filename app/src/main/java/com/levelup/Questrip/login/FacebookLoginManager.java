package com.levelup.Questrip.login;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.levelup.Questrip.common.Bootstrapper;

import java.util.Collections;

/**
 * Facebook 로그인 프로세스를 담당하는 클래스입니다.
 * Facebook API 를 활용하여 로그인을 시도하여, 그 결과에 맞는 이벤트를 수행합니다.
 *
 * 담당자: 김호
 *
 * 역할: Facebook API 를 이용하여 로그인을 시도합니다.
 * 로그인에 성공한 경우, 사용자의 정보는 전역 변수로 관리합니다.
 */
public final class FacebookLoginManager extends LoginManagerBase {

    /**
     * Facebook API 에 로그인 요청을 합니다.
     * 획득한 엑세스 토큰은 서버에 전달하게 됩니다.
     * @param activity: 로그인에 성공하여 엑세스 토큰을 획득한 경우의 이벤트입니다.
     * @param success: 로그인에 성공한 경우의 이벤트입니다.
     * @param failure: 로그인에 실패한 경우의 이벤트입니다.
     */
    @Override
    public void tryLogin(Bootstrapper activity, Runnable success, OnFailure failure) {
        com.facebook.login.LoginManager manager = com.facebook.login.LoginManager.getInstance();
        manager.registerCallback(activity.getCallbackManager(), new FacebookCallback<LoginResult>() {
            /**
             * 로그인에 성공한 경우의 이벤트입니다.
             * @param loginResult: 사용자 정보 등 로그인 결과
             */
            @Override
            public void onSuccess(LoginResult loginResult) {
                success.run();
            }

            /**
             * 로그인을 취소한 경우의 이벤트입니다.
             */
            @Override
            public void onCancel() {
                failure.run(Failed.USER_CANCELED);
            }

            /**
             * 로그인에 실패한 경우의 이벤트입니다.
             * @param e: 실패 이유
             */
            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
                failure.run(Failed.LOGIN_FAILED);
            }
        });

        // 이미 로그인된 사용자인지 확인합니다.
        // 이미 로그인했다면, Facebook API 에 로그인 요청을 하지 않습니다.
        if (isLoggedIn()) {
            success.run();
            return;
        }

        // Facebook API 에 로그인 요청을 합니다.
        manager.logInWithReadPermissions(activity, Collections.singletonList("public_profile"));
    }

    /**
     * 사용자의 엑세스 토큰이 유효하여 현재 로그인된 상태인지 확인합니다.
     * @return 토큰이 유효하다면 true 를 반환합니다.
     */
    @Override
    protected boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();
    }

    /**
     * 사용자의 엑세스 토큰을 "강제로" 반환합니다.
     * @return 엑세스 토큰. 로그인한 상태가 아니라면 빈 문자열 ""을 반환합니다.
     */
    @Override
    public String getAccessToken() {
        return isLoggedIn() ? AccessToken.getCurrentAccessToken().getToken() : "";
    }

}
