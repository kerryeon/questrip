package com.levelup.Questrip.intro;

import com.levelup.Questrip.data.Account;
import com.levelup.Questrip.net.ClientPath;
import com.levelup.Questrip.net.ClientRequest;
import com.levelup.Questrip.net.ClientRequestAsync;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 회원가입 프로세스를 담당하는 클래스입니다.
 * 회원가입 시도하여, 그 결과에 맞는 이벤트를 수행합니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: 로그인을 시도합니다.
 * 로그인에 성공한 경우, 사용자의 정보는 전역 변수로 관리합니다.
 */
final class SignUpManager {

    /**
     * 회원가입을 시도합니다.
     * @param account: 회원가입할 유저의 정보를 가지고 있습니다.
     * @param success: 회원가입에 성공한 경우의 이벤트입니다.
     * @param failure: 회원가입에 실패한 경우의 이벤트입니다.
     */
    static void trySignUp(final Account account, Runnable success,
                          ClientRequestAsync.OnFailure failure) {
        JSONObject object = new JSONObject();
        // 데이터를 가공합니다.
        try {
            object.put("nickname", account.getNickname());
            object.put("birthday", account.getBirthday());
            object.put("address", account.getAddress());
            object.put("address_detail", account.getAddressDetail());
        } catch (JSONException e) {
            // Unreachable
            e.printStackTrace();
        }
        // 서버로 전송합니다.
        ClientRequest.send(ClientPath.SIGN_UP, object,
                o -> onSignUpResponse(o, success, failure), failure);
    }

    /**
     * 회원가입 결과를 분류합니다.
     * @param response: 서버로부터 수신받은 JSON 데이터
     * @param success: 회원가입에 성공한 경우의 이벤트입니다.
     * @param failure: 회원가입에 실패한 경우의 이벤트입니다.
     */
    private static void onSignUpResponse(JSONObject response, Runnable success,
                                         ClientRequestAsync.OnFailure failure) {
        try {
            boolean accept = response.getBoolean("accept");
            // 회원가입에 성공한 경우
            if (accept) success.run();
            // 회원가입이 거절된 경우
            else failure.run(ClientRequestAsync.Failed.REJECTED);
        } catch (JSONException e) {
            // 수신받은 데이터에 이상이 있는 경우.
            e.printStackTrace();
            failure.run(ClientRequestAsync.Failed.INTERNAL);
        }
    }

}
