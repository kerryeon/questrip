package com.levelup.Questrip.intro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.levelup.Questrip.R;

/**
 * 회원가입 화면 액티비티입니다.
 * 새로운 사용자의 정보를 얻습니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 새로운 회원의 정보를 얻습니다.
 *
 * 입력 정보:
 * 닉네임 ( 문자열, 4자~12자, 한글 허용, 특수문자 금지, 예시: 감자리아1234 )
 * 생년월일 ( 날짜, yyyyMMdd, 예시: 19980904 )
 * 주소 ( 문자열, AddressManager 를 통해 획득 )
 * 약관 ( Boolean, 약관 설명 화면으로 이동할 수 있어야 합니다. )
 */
class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    /**
     * "가입" 버튼을 통해 회원가입을 시도합니다.
     */
    private void onSignUp() {
        // TODO to be implemented.
    }
}
