package com.levelup.Questrip.view;

import android.app.Activity;
import android.os.Bundle;

import com.levelup.Questrip.R;
import com.levelup.Questrip.data.Quest;

/**
 * 리더보드 레이아웃입니다.
 * 정해진 순서에 맞추어 결과물들을 보여줍니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 정해진 순서에 맞추어 결과물을 나열합니다.
 */
public final class LeaderBoardLayout extends Activity {

    private Quest[] quests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_leader_board);
    }
}
