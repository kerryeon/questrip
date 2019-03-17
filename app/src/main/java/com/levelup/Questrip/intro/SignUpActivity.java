package com.levelup.Questrip.intro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.levelup.Questrip.R;
import com.levelup.Questrip.common.CommonAlert;
import com.levelup.Questrip.explore.MainActivity;

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
public final class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    /**
     * "가입" 버튼을 통해 회원가입을 시도합니다.
     */
    private void onSignUp() {
        SignUpManager.trySignUp(getFields(), this::onSuccess, this::onFailure);
    }

    /**
     * 회원가입에 성공했을 경우의 이벤트입니다.
     */
    private void onSuccess() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        // 다음 화면으로 이동합니다.
        startActivity(intent);
        // 회원가입 화면은 종료합니다.
        finish();
    }

    /**
     * 회원가입에 실패했을 경우의 이벤트입니다.
     * @param failed: 실패 사유
     */
    private void onFailure(SignUpManager.Failed failed) {
        // TODO to be implemented.
    }

    /**
     * 회원가입에 사용할 입력값을 가져옵니다.
     * @return 입력값이 정상인 경우, 정상적인 필드를 반환합니다.
     * 입력값이 비정상인 경우, null 을 반환합니다.
     */
    private SignUpModel getFields() {
        SignUpModel.Builder builder = new SignUpModel.Builder();
        // 닉네임
        if (builder.setNickname("nickname"))
            assertCheckField(R.string.sign_up_check_nickname);
        // 생년월일
        else if (builder.setBirthday(19980904))
            assertCheckField(R.string.sign_up_check_birthday);
        // 주소
        else if (builder.setAddress("경남 진주시 진주대로 501"))
            assertCheckField(R.string.sign_up_check_address);
        // 세부주소
        else if (builder.setAddressDetail("30동 308호"))
            assertCheckField(R.string.sign_up_check_address);
        // 약관
        else if (builder.setTerms(true))
            assertCheckField(R.string.sign_up_check_terms);
        // 모든 입력값이 정상인 경우, 정상 결과를 반환한다.
        else return builder.getResult();
        // 입력값이 비정상인 경우, null 을 반환한다.
        return null;
    }

    /**
     * 입력값이 잘못된 경우, 그 이유를 알리는 알림창을 띄웁니다.
     * @param messageId: 저장된 메세지입니다.
     */
    private void assertCheckField(int messageId) {
        CommonAlert.show(this, messageId, this::finish);
    }

}
