package com.levelup.Questrip.intro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.levelup.Questrip.R;

/**
 * 약관 화면 액티비티입니다.
 * 사용자에게 약관을 보여줍니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 약관을 보여줍니다.
 * "확인" 버튼을 만들어, 약관을 다 읽었음을 알려야 합니다.
 */
public final class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
    }

    /**
     * "확인" 버튼을 누르면, 약관 읽기를 종료합니다.
     */
    private void onRead() {
        finish();
    }
}
