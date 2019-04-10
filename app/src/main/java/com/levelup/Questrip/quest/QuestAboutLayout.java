package com.levelup.Questrip.quest;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.levelup.Questrip.R;
import com.levelup.Questrip.anim.Sliding;
import com.levelup.Questrip.data.Quest;

/**
 * 퀘스트 정보 레이아웃을 담당하는 클래스입니다.
 *
 * 담당자: 김호, 정홍기
 *
 * 역할: 퀘스트 정보 레이아웃의 동작을 구성합니다.
 */
public final class QuestAboutLayout {

    private View layout;

    private TextView mTitle;
    private TextView mDesc;
    private TextView mLocation;
    private TextView mPeriod;

    private boolean _isOpen;
    private Quest currentQuest;

    /**
     * 레이아웃 객체를 생성합니다.
     * @param activity 현재 액티비티
     */
    QuestAboutLayout(QuestMapActivity activity) {
        this.layout = activity.findViewById(R.id.quest_map_about);
        this._isOpen = false;
        this.currentQuest = null;
        init();
    }

    /**
     * 레이아웃을 초기화합니다.
     * 처음엔 레이아웃이 보이지 않게 합니다.
     */
    private void init() {
        // 필드
        mTitle = layout.findViewById(R.id.quest_about_field_title);
        mDesc = layout.findViewById(R.id.quest_about_field_desc);
        mLocation = layout.findViewById(R.id.quest_about_field_location);
        mPeriod = layout.findViewById(R.id.quest_about_field_period);
    }

    /**
     * 레이아웃을 띄우고 퀘스트 정보를 표현합니다.
     * @param quest 선택한 퀘스트
     */
    void show(final Quest quest) {
        currentQuest = quest;
        mTitle.setText(quest.getTitle());
        mDesc.setText(quest.getDescription());
        mLocation.setText(quest.getLocation());
        // 날짜
        final long dateEnd = quest.getDateEnd();
        final long dateYear = dateEnd / 10000;
        final long dateMonth = (dateEnd / 100) % 100;
        final long dateDay = dateEnd % 100;
        final String dateFormat = layout.getResources().getString(R.string.quest_map_field_period);
        mPeriod.setText(String.format(dateFormat, dateYear, dateMonth, dateDay));
        // 화면에 보이지 않는 경우에만 애니메이션을 실행합니다.
        if (! isOpen()) {
            _isOpen = true;
            Sliding.up(layout);
        }
    }

    /**
     * 레이아웃을 보이지 않게 합니다.
     */
    void hide() {
        // 화면에 보이는 경우에만 애니메이션을 실행합니다.
        if (isOpen()) {
            _isOpen = false;
            Sliding.down(layout);
        }
    }

    /**
     * 퀘스트 설명창이 보이는 지 검사합니다.
     * @return 보인다면 true 를 반환합니다.
     */
    boolean isOpen() {
        return _isOpen;
    }

    /**
     * 현재 표시중인 퀘스트 정보를 반환합니다.
     * @return 퀘스트 (null 일 수 있음)
     */
    @Nullable
    final Quest getCurrentQuest() {
        return currentQuest;
    }

    /**
     * 마커를 눌러 퀘스트 설명창을 띄워야 하는 경우의 이벤트입니다.
     *
     * 담당자: 김호
     */
    @FunctionalInterface
    interface OnInfo {
        /**
         * 퀘스트 설명창을 띄워야 하는 경우를 처리합니다.
         * @param quest: 퀘스트
         */
        void run(@Nullable final Quest quest);
    }

}
