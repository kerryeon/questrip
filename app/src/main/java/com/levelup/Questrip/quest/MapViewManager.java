package com.levelup.Questrip.quest;

import android.content.Intent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.levelup.Questrip.R;
import com.levelup.Questrip.common.CommonAlert;
import com.levelup.Questrip.common.LocationManager;
import com.levelup.Questrip.data.Quest;

import java.util.List;
import java.util.Vector;

/**
 * 지도와, 지도 위의 마커를 관리합니다.
 *
 * 담당자: 정홍기, 김호
 *
 * 역할: 지도 위에 마커를 띄우고, 지우고, 터치 시 메세지를 띄우는 등 여러 기능을 구현합니다.
 * 마커 (Marker)는 사용자의 위치에 따라서 달리 보여야 합니다.
 *
 * 예제는 다음과 같습니다.
 * @see <a href="https://developers.google.com/maps/documentation/android-sdk/intro" />
 * @see <a href="https://webnautes.tistory.com/647" />
 */
final class MapViewManager implements GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private QuestMapActivity activity;

    private GoogleMap googleMap;
    private CameraPosition prevCameraPosition;
    private LocationManager locationManager;

    private int limitList;
    private Runnable onReady;

    private static final float ZOOM_BY_DISTANCE = 8.25f;
    private static final float ZOOM_BY_RATING = 7.25f;

    /**
     * 지도를 초기화합니다.
     * @param activity 현재 액티비티
     * @param onReady 지도가 사용 가능한 경우의 이벤트입니다.
     */
    MapViewManager(QuestMapActivity activity, Runnable onReady) {
        this.activity = activity;
        this.onReady = onReady;
        this.limitList = activity.getResources()
                .getInteger(R.integer.CODE_ACTIVITY_QUEST_MAP_LIMIT_LIST);
        this.locationManager = new LocationManager(activity);
        // SupportMapFragment 객체를 획득한 후, 지도가 사용 가능한 지 검사합니다.
        SupportMapFragment mapFragment = (SupportMapFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.quest_map_map);
        if (mapFragment != null) mapFragment.getMapAsync(this);
        // Unreachable
        else CommonAlert.show(activity, R.string.common_failure_unknown,
                activity::finishAndRemoveTask);
    }

    /**
     * 마커를 새로 그립니다.
     */
    void updateMarkers() {
        // 기존의 마커는 모두 지웁니다,
        removeMarkers();
        // 마커를 새로 그립니다.
        for (Quest quest : take())
            addMarker(quest);
    }

    /**
     * 현재 위치로 카메라를 옮기고, 마커를 새로 그립니다.
     */
    void updateMarkersByDistance() {
        moveCamera(ZOOM_BY_DISTANCE);
        updateMarkers();
    }

    /**
     * 퀘스트를 현재 카메라를 기준으로 인기 순으로 정렬합니다.
     * @return 정렬된 퀘스트
     */
    private List<Quest> take() {
        // 아직 퀘스트 목록을 불러오지 않았다면 빈 배열을 반환합니다.
        if (Quest.getList() == null)
            return new Vector<>();
        // 현재 화면 영역 (사각형)을 구합니다.
        LatLngBounds bounds = googleMap.getProjection().getVisibleRegion().latLngBounds;
        // 현재 화면에 속하는 퀘스트를 추출합니다.
        List<Quest> inCamera = new Vector<Quest>(){{
            for (Quest quest : Quest.getList())
                if (bounds.contains(quest.getLatLng()))
                    add(quest);
        }};
        // 그중, 인기가 높은 몇몇 퀘스트만 추출합니다.
        return inCamera.size() > limitList ? inCamera.subList(0, limitList) : inCamera;
    }

    /**
     * 디자인을 적용한 마커를 하나 그립니다.
     * @param quest 퀘스트
     */
    private void addMarker(Quest quest) {
        Marker marker = googleMap.addMarker(MarkerManager.setStyle(
                new MarkerOptions().position(quest.getLatLng()), quest));
        // 퀘스트 정보를 마커에 저장합니다.
        setQuestToMarker(marker, quest);
    }

    /**
     * 마커를 모두 지웁니다.
     */
    private void removeMarkers() {
        googleMap.clear();
    }

    /**
     * 카메라를 현재 위치가 중앙으로 오게 옮깁니다.
     * @param zoomLevel 확대 수준. 0에 가까울수록 넓게 보임.
     */
    private void moveCamera(final float zoomLevel) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                locationManager.getMyLocation(), zoomLevel));
    }

    /**
     * 지도가 사용 가능할 때 발생하는 이벤트입니다.
     * 실질적으로 액티비티를 사용할 준비가 된 경우입니다.
     * @param googleMap 지도 객체
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.prevCameraPosition = googleMap.getCameraPosition();
        // 각종 초기화 작업을 진행합니다.
        initGoogleMapDesign();
        initGoogleMapListeners();
        // 카메라를 전국 지도가 보이게 잡습니다.
        moveCamera(ZOOM_BY_RATING);
        // 지도를 사용할 준비가 되었음을 알립니다.
        onReady.run();
        onReady = null;
    }

    /**
     * 각종 디자인 템플릿을 적용합니다.
     */
    private void initGoogleMapDesign() {
        googleMap.getUiSettings().setMapToolbarEnabled(false);
    }

    /**
     * 각종 이벤트 리스너들을 등록합니다.
     */
    private void initGoogleMapListeners() {
        googleMap.setOnCameraIdleListener(this);
        googleMap.setOnMarkerClickListener(this);
    }

    /**
     * 마커로부터 퀘스트 정보를 불러옵니다.
     * @param marker 마커
     * @return 퀘스트 정보
     */
    private Quest getQuestFromMarker(final Marker marker) {
        return (Quest) marker.getTag();
    }

    /**
     * 마커에 퀘스트 정보를 저장합니다.
     * @param marker 마커
     * @param quest 퀘스트 정보
     */
    private void setQuestToMarker(Marker marker, Quest quest) {
        marker.setTag(quest);
    }

    /**
     * 사용자가 어떤 마커를 터치한 경우의 이벤트입니다.
     * @param marker 터치한 마커
     * @return 터치 이벤트를 소비하려면 true 를 반환합니다.
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        // 마커로부터 퀘스트 정보를 불러옵니다.
        Quest quest = getQuestFromMarker(marker);
        // TODO to be implemented.
        CommonAlert.show(activity, R.string.debug_todo);
        return true;
    }

    /**
     * 카메라가 움직임을 멈춘 경우의 이벤트입니다.
     * 즉, 사용자가 화면을 이리저리 움직이다 마침내 멈춘 경우입니다.
     * 이때 마커를 갱신합니다.
     */
    @Override
    public void onCameraIdle() {
        // 카메라가 바뀌지 않았다면 갱신을 중단합니다.
        final CameraPosition cameraPosition = googleMap.getCameraPosition();
        if (cameraPosition.equals(prevCameraPosition)) return;
        // 카메라가 바뀐 경우에만 갱신을 시도합니다.
        prevCameraPosition = cameraPosition;
        updateMarkers();
    }

}
