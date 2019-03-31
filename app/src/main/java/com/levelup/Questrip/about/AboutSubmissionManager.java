package com.levelup.Questrip.about;

import com.levelup.Questrip.common.SubmissionManagerBase;
import com.levelup.Questrip.net.ClientPath;
import com.levelup.Questrip.net.ClientRequest;
import com.levelup.Questrip.net.ClientRequestAsync;

/**
 * 퀘스트 제출물을 불러오고, 업로드하고, 추천하는 등의 기능을 전담하는 클래스입니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: 퀘스트 제출물을 불러오고, 업로드하고, 추천하거나 신고합니다.
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

}
