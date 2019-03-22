package com.levelup.Questrip.intro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.levelup.Questrip.R;
import com.levelup.Questrip.data.Account;
import com.levelup.Questrip.common.CommonAlert;

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
     * "다음" 버튼을 통해 약관 동의 화면으로 넘어갑니다.
     */
    public void onNextStep(View view) {
        Intent intent = new Intent(getApplicationContext(), TermsActivity.class);
        Account.Builder builder = getFields();
        // 모든 입력값이 정상이면 다음 과정으로 이동합니다.
        if (builder == null) return;
        intent.putExtra("fields", builder);
        startActivity(intent);
    }

    /**
     * 회원가입에 사용할 입력값을 가져옵니다.
     * @return 입력값이 정상인 경우, 정상적인 필드를 반환합니다.
     * 입력값이 비정상인 경우, null 을 반환합니다.
     */
    private Account.Builder getFields() {
        Account.Builder builder = new Account.Builder();
        // 닉네임
        if (setNickname(builder))
            assertCheckField(R.string.sign_up_check_nickname);
        // 생년월일
        else if (setBirthday(builder))
            assertCheckField(R.string.sign_up_check_birthday);
        // 주소
        else if (setAddress(builder))
            assertCheckField(R.string.sign_up_check_address);
        // 세부주소
        else if (setAddressDetail(builder))
            assertCheckField(R.string.sign_up_check_address);
        // 모든 입력값이 정상인 경우, 결과값을 저장한 후 반환한다.
        else return builder;
        // 입력값이 비정상인 경우, null 을 반환한다.
        return null;
    }

    /**
     * 닉네임을 검사한 후 가져옵니다.
     * @param builder 회원가입 양식
     * @return 입력값에 문제가 있으면 true 를 반환합니다.
     */
    private boolean setNickname(Account.Builder builder) {
        String value = ((TextView) findViewById(R.id.sign_up_nickname)).getText().toString();
        builder.setNickname(value);
        return false;
    }

    /**
     * 생년월일을 검사한 후 가져옵니다.
     * 생년월일은 반드시 yyyyMMdd 형식이어야 합니다.
     * @param builder 회원가입 양식
     * @return 입력값에 문제가 있으면 true 를 반환합니다.
     */
    private boolean setBirthday(Account.Builder builder) {
        long value = 19870123;
        builder.setBirthday(value);
        return false;
    }

    /**
     * 집주소를 검사한 후 가져옵니다.
     * @param builder 회원가입 양식
     * @return 입력값에 문제가 있으면 true 를 반환합니다.
     */
    private boolean setAddress(Account.Builder builder) {
        String value = ((TextView) findViewById(R.id.sign_up_address)).getText().toString();
        builder.setAddress(value);
        return false;
    }

    /**
     * 세부주소를 검사한 후 가져옵니다.
     * @param builder 회원가입 양식
     * @return 입력값에 문제가 있으면 true 를 반환합니다.
     */
    private boolean setAddressDetail(Account.Builder builder) {
        String value = ((TextView) findViewById(R.id.sign_up_address_detail)).getText().toString();
        builder.setAddressDetail(value);
        return false;
    }

    /**
     * 입력값이 잘못된 경우, 그 이유를 알리는 알림창을 띄웁니다.
     * @param messageId: 저장된 메세지입니다.
     */
    private void assertCheckField(int messageId) {
        CommonAlert.show(this, messageId, this::finish);
    }

    /**
     * 뒤로가기 버튼을 누른 경우, 앱을 종료할 것인지 물어봅니다.
     */
    @Override
    public void onBackPressed() {
        CommonAlert.closeApp(this);
    }

}
