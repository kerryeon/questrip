package com.levelup.Questrip.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public final class LocationManager implements OnFailureListener, OnSuccessListener<Location> {

    private Location myLocation;

    // 기본값 위경도
    private static final double DEFAULT_LATITUDE = 36.4163582;
    private static final double DEFAULT_LONGITUDE = 127.8121624;

    /**
     * 위치 조회 기능을 초기화시킵니다.
     * @param activity 현재 액티비티
     */
    @SuppressLint("MissingPermission")
    public LocationManager(Activity activity) {
        myLocation = null;
        // 위치 API 를 초기화합니다.
        LocationServices.getFusedLocationProviderClient(activity).getLastLocation()
                .addOnFailureListener(activity, this)
                .addOnSuccessListener(activity, this);
    }

    /**
     * 사용자의 위치 정보를 가지고 있는 지 알려줍니다.
     * @return 사용자의 위치 정보를 가지고 있다면 true 를 반환합니다.
     */
    public boolean hasMyLocation() {
        return myLocation != null;
    }

    /**
     * 사용자의 위치 정보를 가져옵니다.
     * 만약 위치 정보가 없다면, null 을 반환합니다.
     * @return 사용자의 위치 정보
     */
    public final LatLng getMyLocation() {
        // 위치 정보가 없는 경우
        if (! hasMyLocation())
            return new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
        // 위치 정보를 가지고 있는 경우
        return new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
    }

    /**
     * 사용자의 위치를 성공적으로 불러왔을 경우의 이벤트입니다.
     * @param location 사용자의 위치
     */
    @Override
    public void onSuccess(Location location) {
        if (location == null) return;
        myLocation = location;
    }

    /**
     * 사용자의 위치를 불러오지 못했을 경우의 이벤트입니다.
     * @param e 실패 이유
     */
    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
    }

}
