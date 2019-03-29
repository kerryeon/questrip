package com.levelup.Questrip.config;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.levelup.Questrip.R;

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

    final Context context = this;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        button = (Button) findViewById(R.id.config_btn_logout);
    }

    public void onClickLogout(View v)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("로그아웃");

        alertDialogBuilder.setMessage("로그아웃 하시겠습니까?").setCancelable(false)
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginManager.getInstance().logOut();
                        finishAffinity();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public void onClickMyquest(View v)
    {
        /**
         * 아직 구현하지 않은 범위
         */
    }

    public void onClickOpensource(View v)
    {
        Intent intent = new Intent(getApplicationContext(), OpenSourceActivity.class);
        startActivity(intent);
    }
}
