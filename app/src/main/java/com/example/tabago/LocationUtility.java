package com.example.tabago;

import android.app.Activity;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.List;

public class LocationUtility {
    public static boolean isSmokingZone(Activity activity, SubActivity3 subActivity3) {
        List<Marker> lstMarkers = subActivity3.getLstMarkers(); // lstMarkers 변수에 접근
        FusedLocationSource fusedLocationSource = new FusedLocationSource(activity, SubActivity3.LOCATION_PERMISSION_REQUEST_CODE);
        LatLng currentLocation = new LatLng(fusedLocationSource.getLastLocation().getLatitude(),
                fusedLocationSource.getLastLocation().getLongitude());

        for (Marker marker : lstMarkers) {
            LatLng markerLocation = marker.getPosition();
            double distance = calculateDistance(currentLocation, markerLocation);

            // 일정 거리 이내에 마커가 존재하면 SmokingZone으로 판단
            if (distance < 100) { // 예시로 100 미터 이내로 설정
                return true;
            }
        }

        return false;
    }

    private static double calculateDistance(LatLng point1, LatLng point2) {
        double lat1 = point1.latitude;
        double lon1 = point1.longitude;
        double lat2 = point2.latitude;
        double lon2 = point2.longitude;

        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515 * 1.609344 * 1000; // 거리를 미터로 변환

        return dist;
    }
}
