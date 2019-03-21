package com.levelup.Questrip.common;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.levelup.Questrip.net.ClientPath;
import com.levelup.Questrip.net.ClientRequest;
import com.levelup.Questrip.net.ClientRequestAsync;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

/**
 * 로그인 프로세스를 담당하는 클래스입니다.
 * 로그인을 시도하여, 그 결과에 맞는 이벤트를 수행합니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: 로그인을 시도합니다.
 * 로그인에 성공한 경우, 사용자의 정보는 전역 변수로 관리합니다.
 */
public final class LoginManager {

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

    /** 로그인을 시도하여, 그 결과를 이벤트로 반환합니다.
     *
     * @param activity: 현재 액티비티입니다.
     * @param success: 로그인에 성공한 경우의 이벤트입니다.
     * @param new_user: 로그인에 성공했으나, 새로운 유저인 경우의 이벤트입니다.
     * @param failure: 로그인에 실패한 경우의 이벤트입니다.
     */
    public static void tryLogin(Bootstrapper activity,
                         Runnable success, Runnable new_user, OnFailure failure) {
        tryLoginToFacebook(activity, success, new_user, failure);
    }

    /**
     * Facebook API 에 로그인 요청을 합니다.
     * 획득한 엑세스 토큰은 서버에 전달하게 됩니다.
     * @param activity: 로그인에 성공하여 엑세스 토큰을 획득한 경우의 이벤트입니다.
     * @param success: 로그인에 성공한 경우의 이벤트입니다.
     * @param new_user: 로그인에 성공했으나, 새로운 유저인 경우의 이벤트입니다.
     * @param failure: 로그인에 실패한 경우의 이벤트입니다.
     */
    private static void tryLoginToFacebook(Bootstrapper activity,
                                           Runnable success, Runnable new_user, OnFailure failure) {
        com.facebook.login.LoginManager manager = com.facebook.login.LoginManager.getInstance();
        manager.registerCallback(activity.getCallbackManager(), new FacebookCallback<LoginResult>() {
            /**
             * 로그인에 성공한 경우의 이벤트입니다.
             * @param loginResult: 사용자 정보 등 로그인 결과
             */
            @Override
            public void onSuccess(LoginResult loginResult) {
                tryLoginToServer(success, new_user, failure);
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
            tryLoginToServer(success, new_user, failure);
            return;
        }

        // Facebook API 에 로그인 요청을 합니다.
        manager.logInWithReadPermissions(activity, Collections.singletonList("public_profile"));
    }

    /**
     * 엑세스 토큰을 이용하여 서버에 로그인 시도를 합니다.
     * @param success: 로그인에 성공한 경우의 이벤트입니다.
     * @param new_user: 로그인에 성공했으나, 새로운 유저인 경우의 이벤트입니다.
     * @param failure: 로그인에 실패한 경우의 이벤트입니다.
     */
    private static void tryLoginToServer(Runnable success, Runnable new_user, OnFailure failure) {
        ClientRequest.send(ClientPath.SIGN_IN,
                o -> onLoginResponseSuccess(o, success, new_user, failure),
                e -> onLoginResponseFailure(e, failure));
    }

    /**
     * 로그인 시도가 성공하여, 그 결과를 받았을 경우의 이벤트입니다.
     * @param object: 결과 정보.
     * @param success: 로그인에 성공한 경우의 이벤트입니다.
     * @param new_user: 로그인에 성공했으나, 새로운 유저인 경우의 이벤트입니다.
     * @param failure: 응답이 잘못된 경우의 이벤트입니다.
     */
    private static void onLoginResponseSuccess(JSONObject object, Runnable success,
                                               Runnable new_user, OnFailure failure) {
        try {
            // 로그인에 성공한 경우
            if (object.getBoolean("sign_in")) {
                // 회원정보를 저장한다.
                JSONObject user_date = object.getJSONObject("data");
                new Account.Builder()
                        .setNickname(user_date.getString("nickname"))
                        .setAddress(user_date.getString("address"))
                        .setAddressDetail(user_date.getString("address_detail"))
                        .setTerms(user_date.getBoolean("terms"))
                        .create().setInstance();
                // 로그인이 성공했음을 알린다.
                success.run();
            }
            // 회원가입이 필요한 경우
            else if (object.getBoolean("sign_up"))
                new_user.run();
            // Unreachable
            else
                failure.run(Failed.LOGIN_FAILED);
        } catch (JSONException e) {
            // 응답이 깨진 경우
            e.printStackTrace();
            failure.run(Failed.LOGIN_FAILED);
        }
    }

    /**
     * 로그인 시도가 실패한 경우의 이벤트입니다.
     * @param object: 실패 이유.
     * @param failure: 액티비티로 넘기는 이벤트입니다.
     */
    private static void onLoginResponseFailure(ClientRequestAsync.Failed object,
                                               OnFailure failure) {
        switch (object) {
            case INTERNAL:
                failure.run(Failed.LOGIN_FAILED);
                break;
            case NETWORK_FAILURE:
                failure.run(Failed.NETWORK_FAILURE);
                break;
        }
    }

    /**
     * 사용자의 엑세스 토큰이 유효하여 현재 로그인된 상태인지 확인합니다.
     * @return 토큰이 유효하다면 true 를 반환합니다.
     */
    private static boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();
    }

    /**
     * 사용자의 엑세스 토큰을 "강제로" 반환합니다.
     * @return 엑세스 토큰. 로그인한 상태가 아니라면 빈 문자열 ""을 반환합니다.
     */
    public static String getAccessToken() {
        return isLoggedIn() ? AccessToken.getCurrentAccessToken().getToken() : "";
    }
}
