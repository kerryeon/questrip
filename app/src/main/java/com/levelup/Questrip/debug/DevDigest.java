package com.levelup.Questrip.debug;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.levelup.Questrip.common.Bootstrapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * [디버그] 개발 진단 도구입니다.
 * 개발 시 드문 확률로 발생하는 오류를 진단합니다.
 *
 * 담당자: 김호
 *
 * 역할: 개발 시 드문 확률로 발생하는 오류를 진단합니다.
 */
public final class DevDigest {

    /**
     * 프로젝트의 키해시 값을 반환합니다.
     * @param activity 부트스트랩 액티비티
     */
    @SuppressLint("PackageManagerGetSignatures")
    public static void printHashKey(Bootstrapper activity) {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest digest = MessageDigest.getInstance("SHA");
                digest.update(signature.toByteArray());
                Log.d("Debug.KeyHash", Base64.encodeToString(digest.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
