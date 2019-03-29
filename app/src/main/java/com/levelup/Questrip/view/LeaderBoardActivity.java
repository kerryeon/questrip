package com.levelup.Questrip.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.model.LatLngBounds;
import com.levelup.Questrip.R;
import com.levelup.Questrip.data.Quest;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

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
public final class LeaderBoardActivity extends AppCompatActivity {

    Button select_category;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

       /**
        * 생성되는 버튼에 따라 수정할 것
        *
        * rg = (RadioGroup) findViewById(R.id.라디오 그룹 ID);
        *
        * select_category = (Button) findViewById(R.id.라디오 버튼 id);
        */
       rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId) {
                   /**
                    * 추천순 or 최신순 선택에 따라서 퀘스트들을 보여주게함
                    *
                    * 추천순 -> 인기순으로 정렬한 퀘스트 가져오기
                    *           (이미 퀘스트를 불러올때 모든 퀘스트를 정렬하여 가져오므로 그대로 불러오면 됨)
                    *           -> Quest.getList()
                    *
                    * 최신순 -> 현재 날짜를 기준으로 퀘스트 리스트를 가져와서 정렬하기
                    *           (퀘스트를 시작 날짜를 기준으로 다시 정렬해야 한다.)
                    *            -> switch 구문의 default:에서 구현할 생각
                    *  -> vector
                    *
                    * 퀘스트 정렬 호출할 부분
                    */
                   default:
                       Vector<Quest> quests = new Vector<Quest>();
                       Comparator<Quest> questsData = new Comparator<Quest>() {
                           @Override
                           public int compare(Quest o1, Quest o2) {
                               if (o1.getDateBegin() < o2.getDateBegin())
                                   return -1;
                               else if (o1.getDateBegin() == o2.getDateBegin())
                                   return 0;
                               else
                                   return 1;
                           }
                       };
                       Collections.sort(quests, questsData);
                       break;
               }
           }
       });
    }
}
