package com.levelup.Questrip.config;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.levelup.Questrip.R;

/**
 * 앱에 사용된 오픈소스 정보를 나열하는 액티비티입니다.
 * 단순히 오픈소스 정보를 줄줄이 보여주면 됩니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 앱에 사용된 오픈소스들의 라이선스 등 정보를 나열합니다.
 */
public final class OpenSourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source);
    }
}
