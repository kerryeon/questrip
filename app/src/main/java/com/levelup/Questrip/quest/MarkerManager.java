package com.levelup.Questrip.quest;

import com.google.android.gms.maps.model.MarkerOptions;
import com.levelup.Questrip.data.Quest;

/**
 * 지도 위의 마커의 디자인을 전담하는 클래스입니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 지도 위에 띄울 마커를 예쁘게 디자인합니다.
 */
final class MarkerManager {

    /**
     * 마커를 디자인합니다.
     * @param style 마커의 디자인 템플릿
     * @param quest 퀘스트
     * @return 디자인이 적용된 마커 디자인 템플릿
     */
    static MarkerOptions setStyle(MarkerOptions style, Quest quest) {
        // TODO to be implemented.
        return style.title(quest.getTitle());
    }

}
