package com.nearbyplaces.devsoftware.nearbyplaces;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    double latitude;
    double longitude;

    private int PROXIMITY_RADIUS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment.getMapAsync(this);
        onPermissionCheck(); // For Checking Location is enabled or not or the app has been given the permission and finding user current location
    }

    private void onPermissionCheck() {

        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                LatLng cureentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(cureentLocation)
                                        .title("Current Location")
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cureentLocation, 15));
                            }

                        }
                    });
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                    LatLng cureentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                    mMap.addMarker(new MarkerOptions().position(cureentLocation).title("Current Location"));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cureentLocation, 15));
                                }

                            }
                        });

            } else {
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                    LatLng cureentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                    mMap.addMarker(new MarkerOptions().position(cureentLocation).title("Current Location"));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cureentLocation, 15));
                                }

                            }
                        });
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ImageButton btnRestaurant = (ImageButton) findViewById(R.id.btnRestaurant);
        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            String Restaurant = "restaurant";
            @Override
            public void onClick(View v) {
                mMap.clear();
                onPermissionCheck();
                Toast.makeText(getApplicationContext(), R.string.finding_nearby_restaurants, Toast.LENGTH_SHORT).show();
                String url = getUrl(latitude, longitude, Restaurant);
                Object[] DataTransfer = new Object[3];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                DataTransfer[2] = Restaurant;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
            }
        });

        ImageButton btnHospital = (ImageButton) findViewById(R.id.btnHospital);

        btnHospital.setOnClickListener(new View.OnClickListener() {
            String Hospital = "hospital";


            @Override
            public void onClick(View v) {
                mMap.clear();
                onPermissionCheck();
                Toast.makeText(getApplicationContext(), R.string.finding_nearby_hospitals, Toast.LENGTH_SHORT).show();
                String url = getUrl(latitude, longitude, Hospital);
                Object[] DataTransfer = new Object[3];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                DataTransfer[2] = Hospital;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
            }
        });

        ImageButton btnSchool = (ImageButton) findViewById(R.id.btnSchool);

        btnSchool.setOnClickListener(new View.OnClickListener() {
            String School = "school";

            @Override
            public void onClick(View v) {
                mMap.clear();
                onPermissionCheck();
                Log.d("onClick", "Button is Clicked");
                Toast.makeText(getApplicationContext(), R.string.finding_nearby_schools, Toast.LENGTH_SHORT).show();
                String url = getUrl(latitude, longitude, School);
                Object[] DataTransfer = new Object[3];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                DataTransfer[2] = School;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);

            }
        });
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyA7LIcRZWMutM_22LRqCp1xWgC5j0aDj6I");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }
}
