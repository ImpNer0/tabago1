package com.example.tabago;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothSocket;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

public class SubActivity3 extends AppCompatActivity implements OnMapReadyCallback, NaverMap.OnMapClickListener {


    private double lat, lon; // 위도 경도

    private NaverMap naverMap;
    private FusedLocationSource locationSource;
    private static final String COUNT_SIGNAL = "count"; // 'count' 신호 문자열
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    List<Marker> lstMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub3); // 수정된 부분

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map_sub3); // 수정된 부분
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_sub3, mapFragment).commit(); // 수정된 부분
        }
        mapFragment.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setOnMapClickListener(this);
        naverMap.setLocationSource(locationSource); // 현재 위치 표시

        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);
        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
            }
        });

        // 마커 추가
        addMarkers();

        // 마커 클릭 이벤트 리스너 등록
        for (Marker marker : lstMarkers) {
            marker.setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    // 마커 클릭 이벤트를 처리합니다.
                    // 클릭한 마커의 위치로 네이버 맵 애플리케이션을 실행하여 경로를 표시합니다.
                    try {
                        launchNaverMapsApp(marker.getPosition());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) {
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            } else {
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
        // 클릭 이벤트에 대한 처리를 수행합니다.
        Toast.makeText(this, "맵 클릭 위치: " + latLng.toString(), Toast.LENGTH_SHORT).show();
    }

    public void addMarkers() {
        Marker marker1 = new Marker();
        marker1.setPosition(new LatLng(37.210279, 126.975302)); // ICT대학
        marker1.setMap(naverMap); // 마커를 지도에 추가
        lstMarkers.add(marker1);

        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng(37.211645, 127.980015));//미혁관
        marker2.setMap(naverMap);
        lstMarkers.add(marker2);

        Marker marker3 = new Marker();
        marker3.setPosition(new LatLng(37.212269, 127.970126));//글로벌 경상관
        marker3.setMap(naverMap);
        lstMarkers.add(marker3);

        Marker marker4 = new Marker();
        marker4.setPosition(new LatLng(37.390935, 126.956417)); // 경기도 안양시 비산동 1110-2
        marker4.setMap(naverMap);
        lstMarkers.add(marker4);

        Marker marker5 = new Marker();
        marker5.setPosition(new LatLng(37.391317, 126.957767)); // 경기도 안양시 평촌동 934-1
        marker5.setMap(naverMap);
        lstMarkers.add(marker5);

        Marker marker6 = new Marker();
        marker6.setPosition(new LatLng(37.390935, 126.956417)); // 경기도 안양시 비산동 1110-2
        marker6.setMap(naverMap);
        lstMarkers.add(marker6);

        Marker marker8 = new Marker();
        marker8.setPosition(new LatLng(37.398283, 126.952497)); // 경기도 안양시 호계동 1045
        marker8.setMap(naverMap);
        lstMarkers.add(marker8);

        Marker marker9 = new Marker();
        marker9.setPosition(new LatLng(37.397808, 126.953833)); // 경기도 안양시 호계동 1039
        marker9.setMap(naverMap);
        lstMarkers.add(marker9);

        Marker marker10 = new Marker();
        marker10.setPosition(new LatLng(37.411986, 126.951515)); // 경기도 안양시 동안구 관양동 1755
        marker10.setMap(naverMap);
        lstMarkers.add(marker10);

    }

    public List<Marker> getLstMarkers() {
        return lstMarkers;
    }

    private void launchNaverMapsApp(LatLng markerPosition) throws IOException {
        if (lat != 0 && lon != 0) {
            Location currentLocation = new Location("");
            currentLocation.setLatitude(lat);
            currentLocation.setLongitude(lon);
            float distance = currentLocation.distanceTo(new Location("")) / 1000; // 거리를 킬로미터로 변환
            if (distance <= 0.1) { // 예시로 100m 이내로 설정
                // 현재 위치가 마커 근처에 있으면 아두이노 작동 중지 신호 전송
                CharArrayWriter connectedThread = null;
                connectedThread.write("stop");
            } else {
                String markerLocation = markerPosition.latitude + "," + markerPosition.longitude;
                String currentLocationStr = lat + "," + lon;

                String routeQuery = "nmap://route/walk?start=" + currentLocationStr +
                        "&dest=" + markerLocation + "&appname=com.example.tabago";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(routeQuery));
                intent.setPackage("com.nhn.android.nmap");
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "현재 위치 정보를 가져오는 중입니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
    /*private void launchNaverMapsApp(LatLng markerPosition) {
        if (lat != 0 && lon != 0) {
            String markerLocation = markerPosition.latitude + "," + markerPosition.longitude;
            String currentLocation = lat + "," + lon;

            String routeQuery = "nmap://route/walk?start=" + currentLocation +
                    "&dest=" + markerLocation + "&appname=com.example.tabago";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(routeQuery));
            intent.setPackage("com.nhn.android.nmap");
            startActivity(intent);
        } else {
            Toast.makeText(this, "현재 위치 정보를 가져오는 중입니다.", Toast.LENGTH_SHORT).show();
        }
    }//이건 만지면 안됨
}*/