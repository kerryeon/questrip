package com.levelup.Questrip.intro;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.levelup.Questrip.R;
import com.levelup.Questrip.explore.MainActivity;

/**
 * 인트로 화면 액티비티입니다.
 * 앱을 구동 시작하면 가장 먼저 보이는 화면입니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 인트로를 약 1초간 보여준 후, 로그인을 시도합니다.
 * 로그인 프로세스는 LoginActivity 에게 맡깁니다.
 * 로그인에 성공한다면, 메인화면으로 이동합니다.
 * 로그인에는 성공했으나 처음 사용하는 사용자라면, 회원가입 화면으로 이동합니다.
 * 로그인에 실패한다면, 로그인을 다시 시도해달라는 알림창을 한번 띄운 후, 앱을 종료합니다.
 */
public class IntroActivity extends AppCompatActivity {

    private static long waitTime = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        waitAndPass();
    }

    private void waitAndPass() {
        // 이벤트 핸들러
        Handler handler = new Handler();
        // 인트로를 지정한 시간만큼 보여줍니다.
        handler.postDelayed(this::tryLogin, waitTime);
    }

    private void tryLogin() {
        LoginManager.tryLogin(this::onLoginSuccess, this::onNewUser, this::onLoginFail);
    }

    /**
     * 로그인에 성공한 경우.
     * 메인화면으로 이동합니다.
     */
    private void onLoginSuccess() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        // 다음 화면으로 이동합니다.
        startActivity(intent);
        // 인트로 화면은 종료합니다.
        finish();
    }

    /**
     * 로그인에 성공했지만, 새로운 유저인 경우.
     * 회원가입 화면으로 이동합니다.
     */
    private void onNewUser() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        // 다음 화면으로 이동합니다.
        startActivity(intent);
        // 인트로 화면은 종료합니다.
        finish();
    }

    /**
     * 로그인에 실패한 경우.
     * 로그인을 다시 시도해달라는 알림창을 한번 띄운 후, 앱을 종료합니다.
     */
    private void onLoginFail() {
        // TODO to be implemented.
    }
}
