package com.levelup.Questrip.config;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.levelup.Questrip.R;
import com.levelup.Questrip.common.CommonAlert;

/**
 * 환경설정 화면 액티비티입니다.
 * 로그아웃, 회원탈퇴, 오픈소스 정보 보기 등의 기능을 제공합니다.
 *
 * 담당자: 이동욱, 구본근, 정홍기
 *
 * 역할: 다음에 해당하는 버튼을 보여주고, 이에 해당하는 기능을 수행하게 합니다.
 * 로그아웃
 * 회원탈퇴
 * 오픈소스 (다음의 액티비티를 표시)
 * @see OpenSourceActivity
 */
public final class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
    }

    /**
     * 사용자가 "로그아웃" 버튼을 통해 로그아웃을 시도하는 경우의 이벤트입니다.
     * @param v 버튼
     */
    public void onLogout(View v)
    {
        CommonAlert.show(this, R.string.config_alert_logout, this::tryLogout, () -> {});
    }

    /**
     * 사용자가 "회원탈퇴" 버튼을 통해 영구적으로 탈퇴하려는 경우의 이벤트입니다.
     * @param v 버튼
     */
    public void onSignOut(View v)
    {
        CommonAlert.show(this, R.string.config_alert_sign_out, this::trySignOut, () -> {});
    }

    /**
     * 사용자가 "오픈소스 약관" 버튼을 통해, 앱에 사용된 오픈소스들의 라이선스를 조회하려는 경우의 이벤트입니다.
     * 오픈소스 보기 액티비티로 이동합니다.
     * @param v 버튼
     */
    public void onShowOpenSource(View v)
    {
        Intent intent = new Intent(getApplicationContext(), OpenSourceActivity.class);
        startActivity(intent);
    }

    /**
     * 사용자의 로그아웃을 시도합니다.
     * 로그아웃한 후에는 앱을 종료합니다.
     */
    private void tryLogout() {
        CommonAlert.show(this, R.string.debug_todo, this::exitProcess);
    }

    /**
     * 사용자의 회원탈퇴를 시도합니다.
     * 회원탈퇴한 후에는, 그동안 감사했다는 메세지를 띄운 후, 앱을 종료합니다.
     */
    private void trySignOut() {
        CommonAlert.show(this, R.string.debug_todo, this::exitProcess);
    }

    /**
     * 프로세스를 안전하고 완전하게 종료합니다.
     */
    private void exitProcess() {
        finishAndRemoveTask();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
