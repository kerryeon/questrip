package com.levelup.Questrip.board;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.levelup.Questrip.R;
import com.levelup.Questrip.common.CommonAlert;
import com.levelup.Questrip.common.SubmissionManagerBase;
import com.levelup.Questrip.data.Submission;
import com.levelup.Questrip.net.ClientRequestAsync;
import com.levelup.Questrip.view.ViewSubmissionManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/**
 * 리더보드 레이아웃입니다.
 * 정해진 순서에 맞추어 결과물들을 보여줍니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 정해진 순서에 맞추어 결과물을 나열합니다.
 */
public final class LeaderBoardLayout implements RadioGroup.OnCheckedChangeListener {

    private static int[] imgMedals = {
            R.drawable.ic_1st_medal,
            R.drawable.ic_2nd_medal,
            R.drawable.ic_3rd_medal,
    };

    private Vector<Submission> submissions;
    private SortMode mode;

    private Activity activity;
    private SubmissionManagerBase manager;

    private LinearLayout mContents;

    /**
     * 리더보드를 초기화합니다.
     * 정렬 기준은 추천순을 기본값으로 합니다.
     * @param activity 현재 액티비티
     * @param manager 제출물 관리자
     */
    public LeaderBoardLayout(Activity activity, SubmissionManagerBase manager) {
        this.activity = activity;
        this.manager = manager;
        this.mode = SortMode.Rating;
        this.submissions = null;
        init();
        loadList();
    }

    /**
     * 서버에 결과물을 제출합니다.
     * @param image 이미지
     * @param onSuccess 요청이 성공한 경우의 이벤트입니다.
     * @param onFailure 요청이 실패한 경우의 이벤트입니다.
     */
    public void trySubmit(final byte[] image, Runnable onSuccess,
                          ClientRequestAsync.OnFailure onFailure) {
        ((ViewSubmissionManager) manager).trySubmit(image, onSuccess, onFailure);
    }

    /**
     * 서버로부터 제출물 목록을 불러옵니다.
     */
    public void loadList() {
        manager.updateList(this::onSuccessUpdateSubmissions, this::onFailureFatal);
    }

    /**
     * 필드 및 변수값을 초기화합니다.
     */
    private void init() {
        // 필드
        mContents = activity.findViewById(R.id.leader_board_contents);

        // 이벤트
        RadioGroup sortMode = activity.findViewById(R.id.leader_board_radio_group);
        sortMode.setOnCheckedChangeListener(this);
    }

    /**
     * 리더보드를 다시 그립니다.
     */
    private void updateItems() {
        // 제출물을 아직 볼러오지 못한 경우, 정렬하지 않습니다.
        if (submissions == null) return;
        // 기존의 아이템들을 모두 지웁니다.
        mContents.removeAllViews();
        // 아이템을 추가합니다.
        for (int i = 0; i < submissions.size(); i++) {
            Submission submission = submissions.get(i);
            View item = LeaderBoardContentLayout.addItem(activity, mContents, i, submission,
                    manager.useButtons(), this::onTouchReport, this::onTouchVote);
            // 메달을 수여합니다.
            if (manager.useButtons() && mode == SortMode.Rating && i < 3) {
                ImageView view = item.findViewById(R.id.leader_board_medal);
                view.setVisibility(View.VISIBLE);
                view.setImageResource(imgMedals[i]);
            }
        }
    }

    /**
     * 사용자가 신고 버튼을 터치한 경우의 이벤트입니다.
     * @param index 제출물의 인덱싱 번호
     */
    private void onTouchReport(int index) {
        final Submission submission = submissions.get(index);
        // 신고 이유 목록을 불러온다.
        final String[] lists = activity.getResources().getStringArray(R.array.view_list_report);
        // 신고창을 띄운다.
        CommonAlert.choose(activity, R.string.view_title_report, lists,
                chosen -> ReportManager.tryReport(submission, chosen,
                        // 신고에 성공한 경우의 이벤트입니다.
                        () -> CommonAlert.toast(activity, R.string.view_alert_reported),
                        // 신고에 실패한 경우의 이벤트입니다.
                        (f) -> CommonAlert.failed(activity, f, R.string.view_alert_report_duplicate)));
    }

    /**
     * 사용자가 추천 버튼을 터치한 경우의 이벤트입니다.
     * @param index 제출물의 인덱싱 번호
     */
    private void onTouchVote(int index) {
        final Submission submission = submissions.get(index);
        // 알림창을 띄운다.
        CommonAlert.show(activity,
                String.format(activity.getString(R.string.view_alert_vote), submission.getNickname()),
                () -> VoteManager.tryVote(submission,
                        // 추천에 성공한 경우의 이벤트입니다.
                        () -> CommonAlert.toast(activity, R.string.view_alert_voted),
                        // 추천에 실패한 경우의 이벤트입니다.
                        (f) -> CommonAlert.failed(activity, f, R.string.view_alert_vote_duplicate)),
                () -> {});
    }

    /**
     * 선택한 정렬 모드에 맞추어 결과물을 정렬합니다.
     * 정렬 결과는 submissions 변수에 즉시 반영됩니다.
     * @see LeaderBoardLayout#submissions
     */
    private void sort() {
        // 제출물을 아직 볼러오지 못한 경우, 정렬하지 않습니다.
        if (submissions == null) return;
        // 정렬 연산 클래스를 선택합니다.
        Comparator<Submission> comparator;
        switch (mode) {
            case Rating:
                comparator = new Submission.SubmissionRatingComparator();
                break;
            case Newest:
            default:
                comparator = new Submission.SubmissionDateComparator();
                break;
        }
        // 결과물을 정렬합니다.
        Collections.sort(submissions, comparator);
    }

    /**
     * 서버로부터 성공적으로 제출물 목록을 불러온 경우의 이벤트입니다.
     * 추천순으로 정렬하여 보여줍니다.
     * @param submissions 제출물
     */
    private void onSuccessUpdateSubmissions(Vector<Submission> submissions) {
        this.submissions = submissions;
        sort();
        updateItems();
    }

    /**
     * 앱 구동에 필수적인 요청이 거절된 경우의 이벤트입니다.
     * 거절된 이유를 띄운 후, 이전 화면으로 복귀합니다.
     * @param failed 실패 이유
     */
    private void onFailureFatal(ClientRequestAsync.Failed failed) {
        CommonAlert.failed(activity, failed, activity::finish);
    }

    /**
     * 사용자가 정렬 버튼을 터치한 경우의 이벤트입니다.
     * 지정한 기준에 맞추어 정렬합니다.
     * @param group 버튼 그룹
     * @param checkedId 선택한 버튼 ID
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // 선택한 정렬 모드를 구합니다.
        switch (checkedId) {
            case R.id.learder_board_radio_rating:
                mode = SortMode.Rating;
                break;
            case R.id.learder_board_radio_date:
                mode = SortMode.Newest;
                break;
        }
        // 정렬합니다.
        sort();
        // 리더보드에 반영합니다.
        updateItems();
    }

    /**
     * 리더보드의 정렬 방식을 나열합니다.
     *
     * 담당자: 김호
     */
    private enum SortMode {
        Rating,  // 추천순
        Newest,  // 최신순
    }

}
