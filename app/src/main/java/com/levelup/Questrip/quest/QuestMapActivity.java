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
 * 메인화면 액티비티입니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: // TODO 설계중
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
