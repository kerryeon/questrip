package com.levelup.Questrip.about;

import com.levelup.Questrip.common.SubmissionManagerBase;
import com.levelup.Questrip.net.ClientPath;
import com.levelup.Questrip.net.ClientRequest;
import com.levelup.Questrip.net.ClientRequestAsync;

/**
 * 사용자가 제출한 퀘스트들을 조회하는 기능을 전담하는 클래스입니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: 사용자가 제출한 퀘스트들을 조회합니다.
 */
public final class AboutSubmissionManager extends SubmissionManagerBase {

    /**
     * 서버에 제출물 목록을 갱신해달라고 요청합니다.
     * @param onSuccess 요청이 성공한 경우의 이벤트입니다.
     * @param onFailure 요청이 실패한 경우의 이벤트입니다.
     */
    @Override
    public void updateList(onSuccess onSuccess, ClientRequestAsync.OnFailure onFailure) {
        ClientRequest.send(ClientPath.ABOUT_BOARD,
                o -> onUpdateResponseSuccess(o, onSuccess, onFailure), onFailure);
    }

    /**
     * 신고, 추천버튼을 사용하는 지 검사합니다.
     * @return 사용한다면 true 를 반환합니다.
     */
    @Override
    public boolean useButtons() {
        return false;
    }

}
