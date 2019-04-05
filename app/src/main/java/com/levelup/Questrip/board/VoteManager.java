package com.levelup.Questrip.board;

import com.levelup.Questrip.data.Submission;
import com.levelup.Questrip.net.ClientPath;
import com.levelup.Questrip.net.ClientRequest;
import com.levelup.Questrip.net.ClientRequestAsync;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 제출물의 추천 기능을 담당하는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 제출물의 추천 프로세스를 구현합니다.
 */
final class VoteManager {

    /**
     * 선택한 제출물에 대해 추천을 시도합니다.
     * @param submission 대상 제출물
     * @param onSuccess 추천이 정상적으로 진행된 경우의 이벤트입니다.
     * @param onFailure 추천에 실패한 경우의 이벤트입니다.
     */
    static void tryVote(final Submission submission, Runnable onSuccess,
                        ClientRequestAsync.OnFailure onFailure) {
        // 서버에 추천 요청을 보냅니다.
        ClientRequest.send(ClientPath.VOTE, getInput(submission.getID()), onSuccess, onFailure);
    }

    /**
     * 서버에 전송할 데이터를 생성합니다.
     * 데이터에는 추천할 퀘스트 ID를 포함시킵니다.
     * @return 전송할 데이터
     */
    private static JSONObject getInput(final long submission_id) {
        JSONObject input = new JSONObject();
        try {
            input.put("submission_id", submission_id);
        } catch (JSONException e) {
            // Unreachable
            e.printStackTrace();
        }
        return input;
    }

}
