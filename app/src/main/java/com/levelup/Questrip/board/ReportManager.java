package com.levelup.Questrip.board;

import com.levelup.Questrip.data.Submission;
import com.levelup.Questrip.net.ClientPath;
import com.levelup.Questrip.net.ClientRequest;
import com.levelup.Questrip.net.ClientRequestAsync;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 제출물의 신고 기능을 담당하는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 제출물의 신고 프로세스를 구현합니다.
 */
final class ReportManager {

    /**
     * 선택한 제출물에 대해 신고를 시도합니다.
     * @param submission 대상 제출물
     * @param reason 신고 이유
     * @param onSuccess 신고가 정상적으로 진행된 경우의 이벤트입니다.
     * @param onFailure 신고에 실패한 경우의 이벤트입니다.
     */
    static void tryReport(final Submission submission, final int reason,
                                 Runnable onSuccess, ClientRequestAsync.OnFailure onFailure) {
        ClientRequest.send(ClientPath.REPORT, getInput(submission.getID(), reason),
                onSuccess, onFailure);
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

}
