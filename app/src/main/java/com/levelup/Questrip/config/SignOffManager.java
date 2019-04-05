package com.levelup.Questrip.config;

import com.levelup.Questrip.net.ClientPath;
import com.levelup.Questrip.net.ClientRequest;
import com.levelup.Questrip.net.ClientRequestAsync;

/**
 * 사용자의 탈퇴 기능을 담당하는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 사용자의 탈퇴 프로세스를 구현합니다.
 */
final class SignOffManager {

    /**
     * 사용자의 회원탈퇴를 시도합니다.
     * @param onSuccess 탈퇴가 정상적으로 진행된 경우의 이벤트입니다.
     * @param onFailure 탈퇴에 실패한 경우의 이벤트입니다.
     */
    static void trySignOff(Runnable onSuccess, ClientRequestAsync.OnFailure onFailure) {
        // 서버에 탈퇴 요청을 보냅니다.
        ClientRequest.send(ClientPath.SIGN_OFF, onSuccess, onFailure);
    }

}
