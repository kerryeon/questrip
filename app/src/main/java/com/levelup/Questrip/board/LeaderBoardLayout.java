package com.levelup.Questrip.board;

import android.app.Activity;
import android.os.Bundle;

import com.levelup.Questrip.R;
import com.levelup.Questrip.data.Submission;

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
public final class LeaderBoardLayout extends Activity {

    private Vector<Submission> submissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_leader_board);
    }

    /**
     * 선택한 정렬 모드에 맞추어 결과물을 정렬합니다.
     * 정렬 결과는 submissions 변수에 즉시 반영됩니다.
     * @see LeaderBoardLayout#submissions
     * @param mode 정렬 모드
     */
    private void sortWith(SortMode mode) {
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
     * 리더보드의 정렬 방식을 나열합니다.
     */
    private enum SortMode {
        Rating,  // 추천순
        Newest,  // 최신순
    }

}
