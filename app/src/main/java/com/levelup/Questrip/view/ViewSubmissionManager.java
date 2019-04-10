package com.levelup.Questrip.view;

import android.util.Base64;

import com.levelup.Questrip.common.SubmissionManagerBase;
import com.levelup.Questrip.data.Quest;
import com.levelup.Questrip.net.ClientPath;
import com.levelup.Questrip.net.ClientRequest;
import com.levelup.Questrip.net.ClientRequestAsync;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 퀘스트 제출물을 불러오고, 업로드하고, 추천하는 등의 기능을 전담하는 클래스입니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: 퀘스트 제출물을 불러오고, 업로드하고, 추천하거나 신고합니다.
 */
public final class ViewSubmissionManager extends SubmissionManagerBase {

    private final Quest quest;

    /**
     * 객체를 생성합니다.
     * @param quest 퀘스트
     */
    ViewSubmissionManager(final Quest quest) {
        this.quest = quest;
    }

    /**
     * 서버에 전송할 데이터를 생성합니다.
     * 데이터에는 조회할 퀘스트 ID를 포함시킵니다.
     * @return 전송할 데이터
     */
    private JSONObject getInput() {
        JSONObject input = new JSONObject();
        try {
            input.put("quest_id", quest.getID());
        } catch (JSONException e) {
            // Unreachable
            e.printStackTrace();
        }
        return input;
    }

    /**
     * 서버에 전송할 데이터를 생성합니다.
     * 데이터에는 등록할 이미지와 퀘스트 ID를 포함시킵니다.
     * @param image_base64 전송할 이미지
     * @return 전송할 데이터
     */
    private JSONObject getInput(String image_base64) {
        JSONObject input = new JSONObject();
        try {
            input.put("quest_id", quest.getID());
            input.put("image", image_base64);
        } catch (JSONException e) {
            // Unreachable
            e.printStackTrace();
        }
        return input;
    }

    /**
     * 서버에 제출물 목록을 갱신해달라고 요청합니다.
     * @param onSuccess 요청이 성공한 경우의 이벤트입니다.
     * @param onFailure 요청이 실패한 경우의 이벤트입니다.
     */
    @Override
    public void updateList(onSuccess onSuccess, ClientRequestAsync.OnFailure onFailure) {
        ClientRequest.send(ClientPath.VIEW, getInput(),
                o -> onUpdateResponseSuccess(o, onSuccess, onFailure), onFailure);
    }

    /**
     * 서버에 결과물을 제출합니다.
     * @param image 이미지
     * @param onSuccess 요청이 성공한 경우의 이벤트입니다.
     * @param onFailure 요청이 실패한 경우의 이벤트입니다.
     */
    public void trySubmit(final byte[] image, Runnable onSuccess,
                          ClientRequestAsync.OnFailure onFailure) {
        ClientRequest.send(ClientPath.SUBMIT, getInput(Base64.encodeToString(image, Base64.NO_WRAP)),
                onSuccess, onFailure);
    }

    /**
     * 신고, 추천버튼을 사용하는 지 검사합니다.
     * @return 사용한다면 true 를 반환합니다.
     */
    @Override
    public boolean useButtons() {
        return true;
    }

}
