package com.levelup.Questrip.common;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.levelup.Questrip.R;

/**
 * 앱에 필요한 권한 요청을 담당합니다.
 *
 * 담당자: 김호
 *
 * 역할: 사용자에게 퍼미션 허가를 요청합니다.
 * 허가가 되지 않으면, 앱을 강제로 종료합니다.
 * 이 클래스는 Bootstrapper 에 의해서만 접근할 수 있습니다.
 * @see Bootstrapper#requirePermission()
 */
final class PermissionManager {

    private Bootstrapper activity;
    private boolean granted;

    /**
     * 새로운 퍼미션 관리 객체를 생성합니다.
     * @param activity Bootstrapper 객체
     */
    PermissionManager(Bootstrapper activity) {
        this.activity = activity;
        this.granted = false;
    }

    /**
     * 사용자에게 퍼미션 허가를 요청합니다.
     */
    void requirePermission() {
        // 이미 허가된 경우, 이 과정을 건너뜁니다.
        if (granted) return;
        // 퍼미션 허가를 요청합니다.
        requestPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECEIVE_BOOT_COMPLETED
        );
    }

    /**
     * 모든 퍼미션들이 허가되었는 지 검사합니다.
     * @return 모든 퍼미션들이 허가되었다면 true 를 반환합니다.
     */
    boolean isGranted() {
        // 이미 허가된 경우, 이 과정을 건너뜁니다.
        if (granted) return true;
        // 각 퍼미션을 검사합니다.
        // 중간에 허가가 필요한 퍼미션이 있다면, 다음 검사를 중단합니다.
        boolean denied = getPermission(Manifest.permission.ACCESS_FINE_LOCATION, R.string.permission_location);
        denied = denied || getPermission(Manifest.permission.INTERNET, R.string.permission_internet);
        // 결과를 저장하고 반환합니다.
        granted = !denied;
        return granted;
    }

    /**
     * 각 퍼미션이 허가되었는 지 검사하고, 그렇지 않다면 허가를 권장하는 다이얼로그를 띄웁니다.
     * @param permission 요청하는 퍼미션
     * @param permissionMessage 허가를 권장하는 메세지
     * @return 퍼미션이 거절되었다면 true 를 반환합니다.
     */
    private boolean getPermission(final String permission, int permissionMessage) {
        // 퍼미션 요청이 필요한 버전에서만 수행합니다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = activity.checkSelfPermission(permission);
            // 퍼미션이 한번 거절된 경우입니다.
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                // 퍼미션을 다시 요청하기 위해서 특별한 다이얼로그를 띄워야 하는 경우입니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                    CommonAlert.show(activity, String.format(
                            // 메세지를 생성합니다.
                            activity.getString(R.string.permission_format),
                            activity.getString(permissionMessage)),
                            // "예" 버튼을 선택한 경우입니다.
                            () -> requestPermissions(permission),
                            // "아니오" 버튼을 선택한 경우입니다.
                            this::onDeniedGetPermission);
                // 퍼미션을 다시 요청하는 데에 아무 짓도 필요없는 경우입니다.
                else requestPermissions(permission);
                // 일단 한번 거절되었으니, 결과는 퍼미션 거부입니다.
                return true;
            }
        }
        // 퍼미션 검사가 완료된 경우입니다.
        return false;
    }

    /**
     * 퍼미션 허가를 요청하는 다이얼로그를 띄웁니다.
     * @param permissions 필요한 퍼미션들
     */
    private void requestPermissions(String... permissions) {
        // 퍼미션 요청이 필요한 버전에서만 수행합니다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permissions, activity.getResources().getInteger(
                    R.integer.CODE_PERMISSIONS_REQUEST));
        }
        // 퍼미션 요청이 필요없다면, 이 과정을 건너뜁니다.
        else granted = true;
    }

    /**
     * 사용자가 특별한 다이얼로그의 요청을 거부한 경우입니다.
     * 퍼미션 없이는 앱을 사용할 수 없다고 알린 후,
     * "확인" 버튼을 누르면 앱을 종료합니다.
     */
    private void onDeniedGetPermission() {
        CommonAlert.show(activity, R.string.permission_denied, activity::finish);
    }

}
