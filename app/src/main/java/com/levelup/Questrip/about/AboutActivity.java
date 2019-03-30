package com.levelup.Questrip.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.levelup.Questrip.R;

/**
 * 사용자 정보 보기 화면 액티비티입니다.
 * 사용자에 대한 정보를 간략히 보여줍니다.
 * 또한, 사용자가 이때껏 제출했던 제출물들을 리더보드 형태로 볼 수 있습니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 상단에는 사용자의 닉네임을 간략히 보여줍니다.
 *
 * 그 아래로는 사용자가 이때껏 제출했던 제출물들을 리더보드 형태로 보여주는데,
 * 다음의 요소들이 반드시 포함되어야 합니다.
 *
 * 퀘스트명
 * 결과 이미지
 * 제출일자
 * 추천수
 *
 * 또한, 추천순/최신순 보기를 선택할 수 있어야 합니다.
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
