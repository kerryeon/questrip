package com.levelup.Questrip.view;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.levelup.Questrip.R;
import com.levelup.Questrip.board.LeaderBoardLayout;
import com.levelup.Questrip.common.CommonAlert;
import com.levelup.Questrip.data.Quest;
import com.levelup.Questrip.common.ImageManager;

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
    ViewUploadLayout viewUploadLayout;

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
        final Quest quest = getQuest();
        viewUploadLayout = new ViewUploadLayout(this);

        // 필드
        leaderBoard = new LeaderBoardLayout(this, new ViewSubmissionManager(quest));

        // 제목
        ((TextView) findViewById(R.id.view_title)).setText(quest.getTitle());
    }

    /**
     * 사용자가 단말기에서 이미지를 선택한 경우의 이벤트입니다.
     * 이미지를 불러와 서버에 업로드합니다.
     * @param bitmap 이미지
     */
    private void onSelectImage(final Bitmap bitmap) {
        // 전송할 사진을 불러옵니다.
        final byte[] input = ImageManager.getBytes(bitmap);
        // 서버에 제출합니다.
        leaderBoard.trySubmit(input, this::onSuccessSubmit,
                (f) -> CommonAlert.failed(this, f));
    }

    /**
     * 서버에 결과물을 성공적으로 업로드한 경우의 이벤트입니다.
     * 사용자가 이를 알리고, 리더보드를 최신순으로 갱신합니다.
     */
    private void onSuccessSubmit() {
        CommonAlert.show(this, R.string.view_alert_submitted);
        // 리더보드를 갱신합니다.
        leaderBoard.loadList();
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
        viewUploadLayout.begin(this::onSelectImage);
    }

    /**
     * 업로드 레이아웃에 결과를 전달합니다.
     * @param requestCode 요청 코드
     * @param resultCode 결과 코드
     * @param data 전달 데이터
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

        if (requestCode == viewUploadLayout.CODE_INTENT_IMAGE_CAMERA)
            viewUploadLayout.showImage();
        else if (requestCode == viewUploadLayout.CODE_INTENT_IMAGE_GALLERY)
            viewUploadLayout.showImage(data.getData());
    }

}
