package com.levelup.Questrip.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.levelup.Questrip.R;

/**
 * 퀘스트 보기 화면 액티비티입니다.
 * QuestMapActivity 로부터 선택된 퀘스트를 자세히 보여줍니다.
 * 해당 액티비티는 아래서 위로 올라오면서 등장해야 합니다.
 * 또한, 위에서 아래로 내려가면서 사라져야 합니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 선택된 퀘스트의 내용을 자세히 알려줍니다.
 * 상단에는 해당 퀘스트의 제목이 있어야 합니다.
 * 바로 밑에는 "최신 순", "추천 순"을 선택할 수 있는 라디오버튼들이 있어야 합니다.
 * 그 밑에는 리더보드가 있어, 순서에 맞추어 결과물들을 보여줍니다.
 */
public final class LeaderBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
    }
}
