package com.levelup.Questrip.config;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.levelup.Questrip.R;

/**
 * 환경설정 화면 액티비티입니다.
 * 사용자에 대한 정보를 간략히 보여줍니다.
 * 로그아웃, 회원탈퇴, 오픈소스 정보를 볼 수 있습니다.
 *
 * 담당자: 이동욱, 구본근, 정홍기
 *
 * 역할: 상단에는 사용자에 대한 정보를 간략히 보여줍니다.
 * 그 아래로는 다음에 해당하는 버튼을 보여주고, 이에 해당하는 기능을 수행하게 합니다.
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
}
