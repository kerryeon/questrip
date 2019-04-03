package com.levelup.Questrip.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.levelup.Questrip.R;
import com.levelup.Questrip.about.AboutSubmissionManager;
import com.levelup.Questrip.board.LeaderBoardLayout;
import com.levelup.Questrip.common.CommonAlert;
import com.levelup.Questrip.data.Quest;

/**
 * 리더보드 화면 액티비티입니다.
 * QuestMapActivity 로부터 선택된 퀘스트의 리더보드를 보여줍니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 선택된 퀘스트의 리더보드를 보여줍니다.
 * 상단에는 해당 퀘스트의 제목이 있어야 합니다.
 * 바로 밑에는 "최신 순", "추천 순"을 선택할 수 있는 라디오버튼 (혹은 이와 비슷한 것)들이 있어야 합니다.
 * 그 밑에는 리더보드가 있어, 순서에 맞추어 결과물들을 보여줍니다.
 */
public final class ViewActivity extends AppCompatActivity {

    LeaderBoardLayout leaderBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        init();
    }

    /**
     * 필드 및 변수값을 초기화합니다.
     */
    private void init() {
        leaderBoard = new LeaderBoardLayout(this, new ViewSubmissionManager(getQuest()));
    }

    /**
     * 사용자가 단말기에서 이미지를 선택한 경우의 이벤트입니다.
     * 이미지를 불러와 서버에 업로드합니다.
     * @param path 이미지 경로
     */
    private void onSelectImage(final String path) {
        // TODO to be implemented.
    }

    /**
     * 서버에 결과물을 성공적으로 업로드한 경우의 이벤트입니다.
     * 사용자가 이를 알리고, 리더보드를 최신순으로 갱신합니다.
     */
    private void onSuccessSubmit() {
        CommonAlert.show(this, R.string.view_alert_submitted);
        // TODO to be implemented.
    }

    /**
     * 퀘스트 정보를 불러옵니다.
     * @return 퀘스트
     */
    private Quest getQuest() {
        Intent intent = getIntent();
        return (Quest) intent.getSerializableExtra("quest");
    }

    /**
     * "도전하기" 버튼을 통해 결과물을 업로드하려는 경우의 이벤트입니다.
     * 단말기로부터 업로드할 이미지를 가져옵니다.
     *
     * @param view 도전하기 버튼
     */
    public void onSubmit(View view) {
        // TODO to be implemented.
        CommonAlert.show(this, R.string.debug_todo);
    }

}
