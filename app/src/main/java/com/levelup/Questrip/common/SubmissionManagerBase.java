package com.levelup.Questrip.common;

import com.levelup.Questrip.data.Submission;
import com.levelup.Questrip.net.ClientRequestAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

/**
 * 퀘스트 제출물을 불러오고, 업로드하고, 추천하는 등의 기능을 전담하는 클래스입니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: 퀘스트 제출물을 불러오고, 업로드하고, 추천하거나 신고합니다.
 */
public abstract class SubmissionManagerBase {

    /**
     * 서버에 제출물 목록을 갱신해달라고 요청합니다.
     * @param onSuccess 요청이 성공한 경우의 이벤트입니다.
     * @param onFailure 요청이 실패한 경우의 이벤트입니다.
     */
    public abstract void updateList(onSuccess onSuccess, ClientRequestAsync.OnFailure onFailure);

    /**
     * 서버로부터 제출물 목록을 성공적으로 불러왔을 경우의 이벤트입니다.
     * @param response 제출물 목록
     * @param onSuccess 요청이 성공적인 경우의 이벤트입니다.
     */
    protected void onUpdateResponseSuccess(final JSONObject response, onSuccess onSuccess,
                                           ClientRequestAsync.OnFailure onFailure) {
        try {
            final JSONArray source = response.getJSONArray("list");
            // 퀘스트 목록을 가공합니다.
            Vector<Submission> submissions = new Vector<Submission>() {{
                for (int i = 0; i < source.length(); i++) {
                    final JSONObject point = source.getJSONObject(i);
                    add(new Submission.Builder()
                            .setID(point.getLong("_id"))
                            .setUserID(point.getLong("user_id"))
                            .setNickname(point.getString("nickname"))
                            .setImagePath(point.getString("name"))
                            .setDate(point.getLong("date"))
                            .setRating(point.getLong("rating"))
                            .create());
                }
            }};
            // 성공적으로 데이터를 수집한 경우, 그 결과를 알립니다.
            onSuccess.run(submissions);
            return;
        } catch (JSONException e) {
            // Unreachable
            e.printStackTrace();
        }
        onFailure.run(ClientRequestAsync.Failed.UNEXPECTED);
    }

    /**
     * 갱신에 성공한 경우의 이벤트입니다.
     *
     * 담당자: 김호
     */
    @FunctionalInterface
    public interface onSuccess {
        /**
         * 갱신에 성공한 경우를 처리합니다.
         * @param submissions: 제출물 목록
         */
        void run(Vector<Submission> submissions);
    }

}
