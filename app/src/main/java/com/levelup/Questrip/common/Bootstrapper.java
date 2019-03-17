package com.levelup.Questrip.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * 첫 실행시 실행되는 액티비티를 Bootstrapper 라고 합니다.
 * Bootstrapper 는 앱을 첫 구동했을 때에 필요한 메소드들을 미리 구현해놓은 추상 클래스입니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: Bootstrap 을 구현합니다.
 * 여러 API 에 대한 초기화 메소드들을 구현합니다.
 *
 */
public abstract class Bootstrapper extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * 프로세스를 초기화합니다.
     */
    private void init() {
        initAPI();
    }

    /**
     * 여러 API 들의 초기화 메소드들을 구현합니다.
     */
    private void initAPI() {
        initAPIFacebook();
    }

    /**
     * Facebook API 의 초기화 메소드를 구현합니다.
     */
    private void initAPIFacebook() {
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this.getApplication());
    }

    /**
     * 새로운 콜백 관리자를 반환합니다.
     * @return 콜백 관리자
     */
    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    /**
     * 새로운 콜백 관리자에게도 결과를 알립니다.
     * @param requestCode: 요청 코드
     * @param resultCode: 결과 코드
     * @param data: 전달 데이터
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
