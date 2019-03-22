package com.levelup.Questrip.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.levelup.Questrip.R;

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
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * 프로세스를 초기화합니다.
     */
    private void init() {
        initCallback();
        initPermission();
        initAPI();
    }

    /**
     * Callback 의 초기화 메소드를 구현합니다.
     */
    private void initCallback() {
        callbackManager = CallbackManager.Factory.create();
    }

    /**
     * 퍼미션 관리자의 초기화 메소드를 구현합니다.
     */
    private void initPermission() {
        permissionManager = new PermissionManager(this);
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
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this.getApplication());
    }

    /**
     * 앱에 필요한 권한들을 사용자에게 요청합니다.
     */
    protected void requirePermission() {
        permissionManager.requirePermission();
    }

    /**
     * 앱에 필요한 모든 권한들을 허가받았을 경우의 이벤트입니다.
     */
    protected abstract void onPermissionsGranted();

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

    /**
     * 사용자가 퍼미션 허가에 대한 답변을 주었을 경우의 이벤트입니다.
     * @param requestCode: 고유 응답 코드
     * @param permissions: 요청한 퍼미션들
     * @param grantResults: 허가에 대한 결과 피드백입니다.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 필요한 응답 코드에만 반응합니다.
        if (requestCode != getResources().getInteger(R.integer.CODE_PERMISSIONS_REQUEST)) return;
        // 퍼미션을 획득한 경우의 이벤트입니다.
        if (grantResults.length > 0 && permissionManager.isGranted()) onPermissionsGranted();
    }

    /**
     * 뒤로가기 버튼을 누른 경우, 앱을 종료합니다.
     */
    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }

}
