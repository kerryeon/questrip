package com.levelup.Questrip.common;

import com.levelup.Questrip.data.Submission;
import com.levelup.Questrip.net.ClientPath;
import com.levelup.Questrip.net.ClientRequest;
import com.levelup.Questrip.net.ClientRequestAsync;

import org.json.JSONException;
import org.json.JSONObject;

public final class ReportManager {

    /**
     * 선택한 제출물에 대해 신고를 시도합니다.
     * @param submission 대상 제출물
     * @param reason 신고 이유
     * @param onSuccess 신고가 정상적으로 진행된 경우의 이벤트입니다.
     * @param onFailure 신고에 실패한 경우의 이벤트입니다.
     */
    public static void tryReport(final Submission submission, final int reason,
                                 Runnable onSuccess, ClientRequestAsync.OnFailure onFailure) {
        // 유효한 신고 이유만 허가한다.
        if (reason < 0) return;
        // 서버에 신고 요청을 보냅니다.
        ClientRequest.send(ClientPath.REPORT, getInput(submission.getID(), reason),
                o -> onResponseSuccess(o, onSuccess, onFailure), onFailure);
    }

    /**
     * 서버에 전송할 데이터를 생성합니다.
     * 데이터에는 신고할 퀘스트 ID와 신고 이유를 포함시킵니다.
     * @return 전송할 데이터
     */
    private static JSONObject getInput(final long submission_id, final int reason) {
        JSONObject input = new JSONObject();
        try {
            input.put("submission_id", submission_id);
            input.put("reason", reason);
        } catch (JSONException e) {
            // Unreachable
            e.printStackTrace();
        }
        return input;
    }

    /**
     * 서버로부터 신고 결과를 수신한 경우의 이벤트입니다.
     * @param response 신고 결과
     * @param onSuccess 신고가 정상적으로 진행된 경우의 이벤트입니다.
     * @param onFailure 신고에 실패한 경우의 이벤트입니다.
     */
    private static void onResponseSuccess(JSONObject response, Runnable onSuccess,
                                          ClientRequestAsync.OnFailure onFailure) {
        try {
            boolean accept = response.getBoolean("accept");
            // 신고에 성공한 경우
            if (accept) onSuccess.run();
            // 신고에 실패한 경우
            else onFailure.run(ClientRequestAsync.Failed.UNEXPECTED);
        } catch (JSONException e) {
            // Unreachable
            e.printStackTrace();
            onFailure.run(ClientRequestAsync.Failed.INTERNAL);
        }
    }

}
