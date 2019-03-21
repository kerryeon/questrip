package com.levelup.Questrip.common;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.levelup.Questrip.R;

/**
 * 일반적인 알림창을 띄워주는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 일반적인 알림창을 띄웁니다.
 */
public final class CommonAlert {

    /**
     * "확인" 버튼 하나 있는 일반적인 알림창을 띄웁니다.
     * @param context: 현재 액티비티
     * @param messageId: 메세지 ID.
     * @param onConfirm: "확인" 버튼을 누르면 실행되는 이벤트입니다.
     * [예시]는 다음과 같습니다.
     * @see com.levelup.Questrip.intro.SignUpActivity#assertCheckField
     * @see PermissionManager#onDeniedGetPermission
     */
    public static void show(Context context, int messageId, Runnable onConfirm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.common_alert_title);
        builder.setMessage(messageId);
        builder.setPositiveButton(
                R.string.common_alert_confirm,
                (dialog, which) -> onConfirm.run()
        );
        builder.setOnCancelListener(
                dialog -> onConfirm.run()
        );
        builder.create().show();
    }

    /**
     * "예" 과 "아니오" 버튼이 있는 일반적인 알림창을 띄웁니다.
     * @param context: 현재 액티비티
     * @param messageId: 메세지 ID.
     * @param onConfirm: "예" 버튼을 누르면 실행되는 이벤트입니다.
     * @param onDenied: "아니오" 버튼을 누르면 실행되는 이벤트입니다.
     */
    public static void show(Context context, int messageId, Runnable onConfirm, Runnable onDenied) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.common_alert_title);
        builder.setMessage(messageId);
        builder.setPositiveButton(
                R.string.common_alert_choose_yes,
                (dialog, which) -> onConfirm.run()
        );
        builder.setNegativeButton(
                R.string.common_alert_choose_no,
                (dialog, which) -> onDenied.run()
        );
        builder.setOnCancelListener(
                dialog -> onDenied.run()
        );
        builder.create().show();
    }

    /**
     * "예" 과 "아니오" 버튼이 있는 일반적인 알림창을 띄웁니다.
     * @param context: 현재 액티비티
     * @param message: 메세지.
     * @param onConfirm: "예" 버튼을 누르면 실행되는 이벤트입니다.
     * @param onDenied: "아니오" 버튼을 누르면 실행되는 이벤트입니다.
     * [예시]는 다음과 같습니다.
     * @see PermissionManager#getPermission(String, int)
     */
    public static void show(Context context, String message, Runnable onConfirm, Runnable onDenied) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.common_alert_title);
        builder.setMessage(message);
        builder.setPositiveButton(
                R.string.common_alert_choose_yes,
                (dialog, which) -> onConfirm.run()
        );
        builder.setNegativeButton(
                R.string.common_alert_choose_no,
                (dialog, which) -> onDenied.run()
        );
        builder.setOnCancelListener(
                dialog -> onDenied.run()
        );
        builder.create().show();
    }

}
