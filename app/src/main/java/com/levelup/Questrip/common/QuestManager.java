package com.levelup.Questrip.common;

import com.levelup.Questrip.data.Quest;
import com.levelup.Questrip.net.ClientPath;
import com.levelup.Questrip.net.ClientRequest;
import com.levelup.Questrip.net.ClientRequestAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Vector;

/**
 * 퀘스트 목록을 불러오고, 정렬하는 등의 기능을 전담하는 클래스입니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: 퀘스트 목록을 불러오고, 업로드하고, 정렬합니다.
 */
public final class QuestManager {

    /**
     * 서버에 퀘스트 목록을 갱신해달라고 요청합니다.
     * @param onSuccess 요청이 성공한 경우의 이벤트입니다.
     * @param onFailure 요청이 실패한 경우의 이벤트입니다.
     */
    public static void updateList(Runnable onSuccess, ClientRequestAsync.OnFailure onFailure) {
        ClientRequest.send(ClientPath.MAP,
                o -> onQuestListResponseSuccess(o, onSuccess, onFailure), onFailure);
    }

    /**
     * 서버로부터 퀘스트 목록을 성공적으로 불러왔을 경우의 이벤트입니다.
     * @param response 퀘스트 목록
     * @param onSuccess 요청이 성공적인 경우의 이벤트입니다.
     */
    private static void onQuestListResponseSuccess(final JSONObject response, Runnable onSuccess,
                                                   ClientRequestAsync.OnFailure onFailure) {
        try {
            final JSONArray source = response.getJSONArray("list");
            // 퀘스트 목록을 가공합니다.
            Vector<Quest> quests = new Vector<Quest>() {{
                for (int i = 0; i < source.length(); i++) {
                    final JSONObject point = source.getJSONObject(i);
                    add(new Quest.Builder()
                            .setID(point.getLong("_id"))
                            .setTitle(point.getString("title"))
                            .setDescription(point.getString("description"))
                            .setLocation(point.getString("location"))
                            .setLatitude(point.getDouble("latitude"))
                            .setLongitude(point.getDouble("longitude"))
                            .setDateBegin(point.getLong("date_begin"))
                            .setDateEnd(point.getLong("date_end"))
                            .setRating(point.getLong("rating"))
                            .setIsCleared(point.getBoolean("is_cleared"))
                            .create());
                }
            }};
            // 인기 순으로 정렬합니다.
            Collections.sort(quests, new Quest.QuestComparator());
            // 결과를 저장합니다.
            Quest.setList(quests.toArray(new Quest[0]));
        } catch (JSONException e) {
            // Unreachable
            e.printStackTrace();
            onFailure.run(ClientRequestAsync.Failed.UNEXPECTED);
            return;
        }
        // 성공적으로 데이터를 수집한 경우, 그 결과를 알립니다.
        onSuccess.run();
    }

}
