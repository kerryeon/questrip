package com.levelup.Questrip.view;

import android.app.Activity;
import android.os.Bundle;

import com.levelup.Questrip.R;

/**
 * 리더보드의 각 결과물을 담당하는 레이아웃입니다.
 * 순위권에 드는 결과물은 왕관과 같은 특별한 표현을 하여 다른 결과물과 구분짓습니다.
 * 추천 횟수를 터치하여 추천할 수 있습니다. 이때, 추천했다는 Toast 메세지를 출력합니다.
 * 결과물 사진을 터치하면 전체화면으로 볼 수 있습니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 리더보드의 결과물을 표현합니다.
 *
 * 해당 레이아웃은 다음의 정보를 표현할 수 있어야 합니다.
 * 제출자의 닉네임
 * 랭킹 (순위권이면 특별한 표현 추가)
 * 결과물 (사진)
 * 퀘스트 시작일, 퀘스트 종료일
 * 추천 횟수 (추천 버튼의 역할도 함)
 */
public final class LeaderBoardContentLayout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_leader_board_content);
    }
}
