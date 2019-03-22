package com.levelup.Questrip.intro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.levelup.Questrip.R;
import com.levelup.Questrip.common.CommonAlert;
import com.levelup.Questrip.data.Account;
import com.levelup.Questrip.net.ClientRequestAsync;
import com.levelup.Questrip.quest.QuestMapActivity;

/**
 * 약관 화면 액티비티입니다.
 * 사용자에게 약관을 보여줍니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 약관을 보여줍니다.
 * "동의"한다는 체크박스를 만들어, 약관을 다 읽었음을 알려야 합니다.
 */
public final class TermsActivity extends AppCompatActivity {

    // 모든 버튼 입력을 무시합니다.
    private boolean onBreak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        onBreak = false;
    }

    /**
     * "동의" 버튼을 누르면, 약관 읽기를 종료하고 회원가입 절차를 진행합니다.
     */
    public void onAgree(View view) {
        // 모든 버튼 입력을 비활성화합니다.
        if (onBreak) return;
        onBreak = true;
        // 약관에 동의하고, 회원가입 절차를 진행합니다.
        Account.Builder builder = getFields();
        builder.setTerms(true);
        onSignUp(builder.create().setInstance());
    }

    /**
     * "가입" 버튼을 통해 회원가입을 시도합니다.
     *
     */
    private void onSignUp(final Account account) {
        // 입력값에 오류가 있는 경우, 가입 절차를 진행하지 않습니다.
        if (account == null) return;
        // 입력값에 문제가 없는 경우, 가입 절차를 계속 진행합니다.
        SignUpManager.trySignUp(account, this::onSuccess, this::onFailure);
    }

    /**
     * 회원가입에 성공했을 경우의 이벤트입니다.
     */
    private void onSuccess() {
        CommonAlert.show(this, R.string.terms_alert_welcome, this::gotoMain);
    }

    /**
     * 메인화면으로 이동합니다.
     */
    private void gotoMain() {
        Intent intent = new Intent(getApplicationContext(), QuestMapActivity.class);
        // 이전의 모든 화면을 종료하도록 합니다.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        // 다음 화면으로 이동합니다.
        startActivity(intent);
    }

    /**
     * 회원가입에 실패했을 경우의 이벤트입니다.
     * @param failed: 실패 사유
     */
    private void onFailure(ClientRequestAsync.Failed failed) {
        ((CheckBox) findViewById(R.id.terms_btn_agree)).setChecked(false);
        CommonAlert.failed(this, failed);
        onBreak = false;
    }

    /**
     * 회원가입에 사용할 입력값을 가져옵니다.
     * @return 이전 액티비티로부터 전달받은 입력값
     */
    private Account.Builder getFields() {
        Intent intent = getIntent();
        return (Account.Builder) intent.getSerializableExtra("fields");
    }

}
