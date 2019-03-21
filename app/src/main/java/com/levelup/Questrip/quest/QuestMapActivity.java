package com.levelup.Questrip.quest;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.levelup.Questrip.R;

/**
 * 지도 위에 퀘스트를 표시해주는 액티비티입니다.
 * 퀘스트를 한번 터치하면 해당 퀘스트에 대한 간략한 정보를 표시합니다.
 * 퀘스트를 두번 터치하면 해당 퀘스트의 리더보드를 보여줍니다.
 * @see com.levelup.Questrip.view.LeaderBoardActivity
 * 한편, 상단 우측에 = 같은 모양의 버튼을 두어, 환경설정 액티비티로 이동할 수 있게 합니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 지도 위에 퀘스트를 표시합니다.
 * 퀘스트는 MarkerManager 를 통해 표시합니다.
 */
public final class QuestMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_map);
        // SupportMapFragment 객체를 획득한 후, 지도가 사용 가능한 지 검사합니다.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_quest_map);
        mapFragment.getMapAsync(this);
    }

    /**
     * 지도가 사용 가능할 때 발생하는 이벤트입니다.
     * 실질적으로 액티비티를 사용할 준비가 된 경우입니다.
     * @param googleMap 지도 객체
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng city = new LatLng(35.154492, 128.098045);
        googleMap.addMarker(new MarkerOptions().position(city).title("Marker in Jinju"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(city));
    }
}
