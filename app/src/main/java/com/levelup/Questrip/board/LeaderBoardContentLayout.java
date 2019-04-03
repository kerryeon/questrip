package com.levelup.Questrip.board;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.levelup.Questrip.R;
import com.levelup.Questrip.data.Submission;

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
public final class LeaderBoardContentLayout {

    private static final int MARGIN_LEFT    = 0;
    private static final int MARGIN_RIGHT   = 0;
    private static final int MARGIN_TOP     = 0;
    private static final int MARGIN_BOTTOM  = 16;

    /**
     * 제출물 리스트에 제출물을 추가합니다.
     * @param activity 현재 액티비티
     * @param mContents 제출물 리스트
     * @param index 제출물의 인덱싱 번호
     * @param submission 제출물
     * @param useButtons 신고, 추천 버튼을 사용하면 true 를 입력합니다.
     * @param onReport 신고 버튼을 눌렀을 경우의 이벤트입니다.
     * @param onVote 투표 버튼을 눌렀을 경우의 이벤트입니다.
     */
    static void addItem(Activity activity, LinearLayout mContents, int index,
                        Submission submission, boolean useButtons,
                        OnChoose onReport, OnChoose onVote) {
        // 레이아웃을 생성합니다.
        View item = getItemView(activity, mContents);
        // 필드를 불러옵니다.
        TextView mTitle = item.findViewById(R.id.leader_board_title);
        TextView mRating = item.findViewById(R.id.leader_board_field_rating);
        // 필드에 값을 반영합니다.
        mTitle.setText(submission.getNickname());
        mRating.setText(String.valueOf(submission.getRating()));
        // 이벤트를 등록합니다.
        addEvent(item, index, useButtons, onReport, onVote);
    }

    /**
     * 제출물 레이아웃을 생성합니다.
     * @param activity 현재 액티비티
     * @param mContents 제출물 리스트
     * @return 제출물 레이아웃
     */
    @SuppressLint("InflateParams")
    private static View getItemView(Activity activity, LinearLayout mContents) {
        // 레이아웃을 생성합니다.
        View item = LayoutInflater.from(activity)
                .inflate(R.layout.layout_leader_board_content, null);
        // 레이아웃을 추가합니다.
        mContents.addView(item);
        // 크기 및 위치를 보정합니다.
        item.setLayoutParams(getLayoutParams());
        return item;
    }

    /**
     * 크기 및 위치 보정값을 불러옵니다.
     * @return 레이아웃 추가정보
     */
    private static LinearLayout.LayoutParams getLayoutParams() {
        // 크기를 조정합니다.
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        // 위치를 조정합니다.
        params.setMargins(MARGIN_LEFT, MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM);
        return params;
    }

    /**
     * 각종 이벤트를 추가합니다.
     * @param item 아이템 레이아웃
     * @param index 제출물의 인덱싱 번호
     * @param useButtons 신고, 추천 버튼을 사용하면 true 를 입력합니다.
     * @param onReport 신고 버튼을 눌렀을 경우의 이벤트입니다.
     * @param onVote 투표 버튼을 눌렀을 경우의 이벤트입니다.
     */
    private static void addEvent(View item, int index, boolean useButtons,
                                 OnChoose onReport, OnChoose onVote) {
        ImageButton mBtnReport = item.findViewById(R.id.leader_board_btn_report);
        ImageButton mBtnVote = item.findViewById(R.id.leader_board_btn_vote);
        // 신고 및 추천 버튼을 사용하는 경우, 각각에 대한 이벤트를 추가합니다.
        if (useButtons) {
            mBtnReport.setOnClickListener((v) -> onReport.run(index));
            mBtnVote.setOnClickListener((v) -> onVote.run(index));
        }
        // 신고 및 추천 버튼을 사용하지 않는 경우, 보이지 않게 합니다.
        else {
            mBtnReport.setVisibility(View.GONE);
            mBtnVote.setVisibility(View.GONE);
        }
    }

    /**
     * 사용자가 여러 제출물 중에서 하나를 터치한 경우의 이벤트입니다.
     */
    @FunctionalInterface
    public interface OnChoose {

        /**
         * 선택한 제출물의 인덱싱 번호를 반환합니다.
         * @param chosen: 선택한 제출물 번호. 0부터 시작합니다.
         */
        void run(int chosen);
    }

}
