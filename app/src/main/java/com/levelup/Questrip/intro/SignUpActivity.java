package com.levelup.Questrip.intro;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
 * 생년월일 ( 날짜, yyyyMMdd, 예시: 19980904 ) - 캘린더를 이용한다.
 * 주소 ( 문자열, AddressManager 를 통해 획득 ) - Daum 주소입력창 API 를 이용한다.
 * 상세주소 ( 문자열 )
 */
public final class SignUpActivity extends AppCompatActivity {

    // 각종 값을 입력할 수 있는 필드
    private EditText mNickname;
    private EditText mDateYear;
    private EditText mDateMonth;
    private EditText mDateDay;
    private EditText mAddress;
    private EditText mAddressDetail;

    // 형식을 갖춘 값
    private long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    /**
     * 각종 변수 및 상태를 초기화합니다.
     */
    private void init() {
        // 변수를 초기화합니다.
        date = 0;
        // 필드를 가져옵니다.
        mNickname = findViewById(R.id.sign_up_nickname);
        mDateYear = findViewById(R.id.sign_up_year);
        mDateMonth = findViewById(R.id.sign_up_month);
        mDateDay = findViewById(R.id.sign_up_day);
        mAddress = findViewById(R.id.sign_up_address);
        mAddressDetail = findViewById(R.id.sign_up_address_detail);
        // 다음의 필드는 수동으로 입력할 수 없게 합니다.
        mDateYear.setKeyListener(null);
        mDateMonth.setKeyListener(null);
        mDateDay.setKeyListener(null);
        mAddress.setKeyListener(null);
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
     * @param builder: 회원가입 양식
     * @return 입력값에 문제가 있으면 true 를 반환합니다.
     */
    private boolean setNickname(Account.Builder builder) {
        String value = mNickname.getText().toString();
        // 입력값을 검사합니다.
        if (value.length() < 4 || value.length() > 12)
            return true;
        builder.setNickname(value);
        return false;
    }

    /**
     * 생년월일을 검사한 후 가져옵니다.
     * 생년월일은 반드시 yyyyMMdd 형식이어야 합니다.
     * @param builder: 회원가입 양식
     * @return 입력값에 문제가 있으면 true 를 반환합니다.
     */
    private boolean setBirthday(Account.Builder builder) {
        // 입력값을 검사합니다.
        if (date < 19000101 || date > 99991231)
            return true;
        builder.setBirthday(date);
        return false;
    }

    /**
     * 집주소를 검사한 후 가져옵니다.
     * @param builder: 회원가입 양식
     * @return 입력값에 문제가 있으면 true 를 반환합니다.
     */
    private boolean setAddress(Account.Builder builder) {
        String value = mAddress.getText().toString();
        // 입력값을 검사합니다.
        if (value.length() < 4)
            return true;
        builder.setAddress(value);
        return false;
    }

    /**
     * 세부주소를 검사한 후 가져옵니다.
     * @param builder: 회원가입 양식
     * @return 입력값에 문제가 있으면 true 를 반환합니다.
     */
    private boolean setAddressDetail(Account.Builder builder) {
        String value = mAddressDetail.getText().toString();
        // 입력값을 검사합니다.
        if (value.length() == 0)
            return true;
        builder.setAddressDetail(value);
        return false;
    }

    /**
     * 입력값이 잘못된 경우, 그 이유를 알리는 알림창을 띄웁니다.
     * @param messageId: 저장된 메세지입니다.
     */
    private void assertCheckField(int messageId) {
        CommonAlert.show(this, messageId);
    }

    /**
     * 사용자가 생년월일 정보를 캘린더를 통해 입력할 수 있게 합니다.
     * @param view: 터치한 객체
     */
    public void onClickDate(View view)
    {
        CommonAlert.date(this, this::onReceiveDate, date);
    }

    /**
     * 사용자가 생년월일 정보를 입력한 경우의 이벤트입니다.
     * 년, 월, 일에 해당하는 각 필드의 값을 갱신합니다.
     * @param date: yyyyMMdd 형식의 생년월일 정보
     */
    private void onReceiveDate(long date) {
        this.date = date;
        mDateYear.setText(String.valueOf(date / 10000));
        mDateMonth.setText(String.valueOf((date / 100) % 100));
        mDateDay.setText(String.valueOf(date % 100));
    }

    /**
     * 사용자가 집주소를 외부 API 를 통해 입력할 수 있게 합니다.
     * @param view 터치한 객체
     */
    public void onClickAddress(View view)
    {
        Intent intent = new Intent(this, AddressActivity.class);
        startActivityForResult(intent, getResources().getInteger(R.integer.CODE_INTENT_ADDRESS));
    }

    /**
     * 하위 액티비티로부터 결과를 수신받습니다.
     * @param requestCode: 요청 코드
     * @param resultCode: 응답 코드
     * @param data: 결과값을 포함한 객체
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 유효한 응답에만 반응합니다.
        if (resultCode != RESULT_OK) return;
        // 집주소를 입력받았을 경우의 이벤트입니다.
        if (requestCode == getResources().getInteger(R.integer.CODE_INTENT_ADDRESS)) {
            String value = data.getStringExtra("value");
            mAddress.setText(value);
        }
    }

    /**
     * 뒤로가기 버튼을 누른 경우, 앱을 종료할 것인지 물어봅니다.
     */
    @Override
    public void onBackPressed() {
        CommonAlert.closeApp(this);
    }

}
