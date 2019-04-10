package com.levelup.Questrip.common;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.levelup.Questrip.R;
import com.levelup.Questrip.net.ClientRequestAsync;

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
     * [예시]는 다음과 같습니다.
     * @see com.levelup.Questrip.intro.SignUpActivity#assertCheckField
     */
    public static void show(Context context, int messageId) {
        show(context, messageId, () -> {});
    }

    /**
     * "확인" 버튼 하나 있고, 이벤트를 호출할 수 있는 알림창을 띄웁니다.
     * @param context: 현재 액티비티
     * @param messageId: 메세지 ID.
     * @param onConfirm: "확인" 버튼을 누르면 실행되는 이벤트입니다.
     * [예시]는 다음과 같습니다.
     * @see PermissionManager#onDeniedGetPermission
     */
    public static void show(Context context, int messageId, Runnable onConfirm) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.common_alert_title)
                .setMessage(messageId)
                .setPositiveButton(
                        R.string.common_alert_confirm,
                        (dialog, which) -> onConfirm.run()
                )
                .setOnCancelListener(
                        dialog -> onConfirm.run()
                )
                .create().show();
    }

    /**
     * "예" 과 "아니오" 버튼이 있는 일반적인 알림창을 띄웁니다.
     * @param context: 현재 액티비티
     * @param messageId: 메세지 ID.
     * @param onConfirm: "예" 버튼을 누르면 실행되는 이벤트입니다.
     * @param onDenied: "아니오" 버튼을 누르면 실행되는 이벤트입니다.
     * [예시]는 다음과 같습니다.
     * @see #closeApp(Activity)
     */
    public static void show(Context context, int messageId, Runnable onConfirm, Runnable onDenied) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.common_alert_title)
                .setMessage(messageId)
                .setPositiveButton(
                        R.string.common_alert_choose_yes,
                        (dialog, which) -> onConfirm.run()
                )
                .setNegativeButton(
                        R.string.common_alert_choose_no,
                        (dialog, which) -> onDenied.run()
                )
                .setOnCancelListener(
                        dialog -> onDenied.run()
                )
                .create().show();
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
        new AlertDialog.Builder(context)
                .setTitle(R.string.common_alert_title)
                .setMessage(message)
                .setPositiveButton(
                        R.string.common_alert_choose_yes,
                        (dialog, which) -> onConfirm.run()
                )
                .setNegativeButton(
                        R.string.common_alert_choose_no,
                        (dialog, which) -> onDenied.run()
                )
                .setOnCancelListener(
                        dialog -> onDenied.run()
                )
                .create().show();
    }

    /**
     * 여러 선택지가 있는 알림창을 띄웁니다.
     * @param context 현재 액티비티
     * @param titleId 제목 ID
     * @param listId 선택지 목록 ID
     * @param onChoose 선택지 중 하나를 선택한 경우의 이벤트입니다.
     *                 취소를 누른 경우 -1을 반환합니다.
     */
    public static void choose(Context context, int titleId, int listId, OnChoose onChoose) {
        new AlertDialog.Builder(context)
                .setTitle(titleId)
                .setItems(
                        context.getResources().getStringArray(listId),
                        (dialog, i) -> onChoose.run(i)
                )
                .setNegativeButton(R.string.common_alert_cancel,
                        (dialog, which) -> dialog.dismiss()
                )
                .create().show();
    }

    /**
     * 사용자에게 간단한 정보를 알려주는 Toast 를 띄웁니다.
     * @param context 현재 액티비티
     * @param messageId 메세지
     */
    public static void toast(Context context, int messageId) {
        Toast.makeText(context, messageId, Toast.LENGTH_LONG).show();
    }

    /**
     * 서버로부터 긍정적인 응답이 오지 않은 경우, 그 이유를 사용자에게 알립니다.
     * @param context: 현재 액티비티
     * @param failed: 실패 이유.
     */
    public static void failed(Context context, ClientRequestAsync.Failed failed) {
        failed(context, failed, R.string.common_failure_unknown, () -> {});
    }

    /**
     * 서버로부터 긍정적인 응답이 오지 않은 경우, 그 이유를 사용자에게 알립니다.
     * @param context: 현재 액티비티
     * @param failed: 실패 이유.
     * @param onConfirm: "확인" 버튼을 누르면 실행되는 이벤트입니다.
     * [예시]는 다음과 같습니다.
     * @see com.levelup.Questrip.quest.QuestMapActivity#onFailureFatal(ClientRequestAsync.Failed)
     */
    public static void failed(Context context, ClientRequestAsync.Failed failed,
                              Runnable onConfirm) {
        failed(context, failed, R.string.common_failure_unknown, onConfirm);
    }

    /**
     * 서버로부터 긍정적인 응답이 오지 않은 경우, 그 이유를 사용자에게 알립니다.
     * @param context: 현재 액티비티
     * @param failed: 실패 이유.
     * @param messageOnRejected: 요청이 거절당한 경우의 메세지.
     * [예시]는 다음과 같습니다.
     * @see com.levelup.Questrip.intro.TermsActivity#onFailure(ClientRequestAsync.Failed)
     */
    public static void failed(Context context, ClientRequestAsync.Failed failed,
                              int messageOnRejected) {
        int messageId;
        switch (failed) {
            case INTERNAL:
                messageId = R.string.common_failure_internal;
                break;
            case NETWORK_FAILURE:
                messageId = R.string.common_failure_network;
                break;
            case REJECTED:
                messageId = messageOnRejected;
                break;
            default:
                messageId = R.string.common_failure_unknown;
                break;
        }
        show(context, messageId, () -> {});
    }

    /**
     * 서버로부터 긍정적인 응답이 오지 않은 경우, 그 이유를 사용자에게 알립니다.
     * @param context: 현재 액티비티
     * @param failed: 실패 이유.
     * @param messageOnRejected: 요청이 거절당한 경우의 메세지.
     * @param onConfirm: "확인" 버튼을 누르면 실행되는 이벤트입니다.
     */
    private static void failed(Context context, ClientRequestAsync.Failed failed,
                              int messageOnRejected, Runnable onConfirm) {
        int messageId;
        switch (failed) {
            case INTERNAL:
                messageId = R.string.common_failure_internal;
                break;
            case NETWORK_FAILURE:
                messageId = R.string.common_failure_network;
                break;
            case REJECTED:
                messageId = messageOnRejected;
                break;
            default:
                messageId = R.string.common_failure_unknown;
                break;
        }
        show(context, messageId, onConfirm);
    }

    /**
     * 캘린더를 생성하여, 날짜 값을 입력받을 수 있게 합니다.
     * @param context: 현재 액티비티
     * @param onSuccess: 날짜 값을 선택한 경우의 이벤트입니다.
     * @param valueDefault: 날짜 기본값. 0 이하의 값을 넣으면 20000101 로 초기화됩니다.
     * [예시]는 다음과 같습니다.
     * @see com.levelup.Questrip.intro.SignUpActivity#onClickDate(android.view.View)
     */
    public static void date(Context context, OnSuccessCalender onSuccess, long valueDefault) {
        int year, month, day;
        // 비정상적인 입력값이 들어온 경우
        if (valueDefault <= 0 || valueDefault > 99991231) {
            year = 2000;
            month = 0;
            day = 1;
        }
        // 정상적인 입력값인 경우
        else {
            int valueDefaultInt = (int) valueDefault;
            year = valueDefaultInt / 10000;
            month = ((valueDefaultInt / 100) % 100) - 1;
            day = valueDefaultInt % 100;
        }
        // 캘린더를 생성합니다
        new DatePickerDialog(context,
                // 날짜를 고른 경우의 이벤트
                (picker, y, m, d) -> onReceiveDate(y, m, d, onSuccess),
                // 기본값
                year, month, day).show();
    }

    /**
     * 사용자가 캘린더에서 날짜 값을 입력하여 마친 경우의 이벤트입니다.
     * @param year: 년
     * @param month: 월 (0부터 시작)
     * @param day: 일
     * @param onSuccess: 날짜 값을 선택한 경우의 이벤트입니다.
     */
    private static void onReceiveDate(long year, long month, long day, OnSuccessCalender onSuccess) {
        // yyyyMMdd 형식으로 날짜 값을 가져옵니다.
        long value = year * 10000
                + (month + 1) * 100
                + day;
        // 구한 날짜 값을 반환합니다.
        onSuccess.run(value);
    }

    /**
     * 사용자에게 앱을 종료할 것인지 물어봅니다.
     * @param context: 현재 액티비티
     * [예시]는 다음과 같습니다.
     * @see com.levelup.Questrip.intro.SignUpActivity#onBackPressed()
     * @see com.levelup.Questrip.quest.QuestMapActivity#onBackPressed()
     */
    public static void closeApp(Activity context) {
        show(context, R.string.common_alert_field_close_app,
                context::finishAndRemoveTask, () -> {});
    }

    /**
     * 사용자가 여러 선택지 중에서 하나를 고른 경우의 이벤트입니다.
     */
    @FunctionalInterface
    public interface OnChoose {

        /**
         * 선택한 선택지의 인덱싱 번호를 반환합니다.
         * @param chosen: 선택한 선택지 번호. 0부터 시작합니다.
         *              아무것도 고르지 않은 경우, -1을 반환합니다.
         */
        void run(final int chosen);
    }

    /**
     * 캘린더에서 적절한 날짜를 입력받았을 경우의 이벤트입니다.
     */
    @FunctionalInterface
    public interface OnSuccessCalender {

        /**
         * 입력값을 반환합니다.
         * @param value: yyyyMMdd 형식의 날짜 데이터.
         */
        void run(final long value);
    }

}
