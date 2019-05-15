package com.example.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.weather.Constant.AppConstants;
import com.example.weather.Utils.GpsUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class WaitingScreenActivity extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;
    private StringBuilder stringBuilder;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isGPS = false;
    private boolean sucess = false;
    private boolean isContinue = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_screen_xml);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });
        Log.d("waiting", "after turn on: " + this.isGPS);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.d("waiting", "null");
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        sucess = true;
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        Log.d("waiting", "in 1");
                        if (!isContinue) {
                            return;
                        }
//                        else {
//                            stringBuilder.append(wayLatitude);
//                            stringBuilder.append("-");
//                            stringBuilder.append(wayLongitude);
//                            stringBuilder.append("\n\n");
//                            txtContinueLocation.setText(stringBuilder.toString());
//                        }
                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }
                    else {
                        Log.d("waiting", "count");
                    }
                }
            }
        };
        if (!isGPS) {
            Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
            Log.d("waiting", "off");
        }
        isContinue = false;
        getLocation();
        count();

    }
    public void count() {
        Thread bamgio=new Thread(){
            public void run()
            {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    Log.d("waiting", "err: " + e.toString());
                }
                finally
                {
                    switchScreen();
                }
            }
        };
        bamgio.start();
    }
    public static String getCity(Context context, double lat, double lng) throws Exception {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
        String city = addresses.get(0).getAdminArea();
        if (TextUtils.isEmpty(city)) {
            city = addresses.get(0).getLocality();
        }
        return city;
    }
    public void switchScreen() {
        String city = "";
        if(sucess == true) {
            try {
                Intent intent = new Intent(WaitingScreenActivity.this,MainActivity.class);
                city = getCity(this, wayLatitude, wayLongitude);
                intent.putExtra("city",city);
                startActivity(intent);
            } catch (Exception e) {
                Log.d("waiting", "err get city: " + e.toString());
                e.printStackTrace();
            }
        }
        else {
            Intent intent = new Intent(WaitingScreenActivity.this,SearchActivity.class);
            Log.d("waiting", "city in sw: " + city);
            startActivity(intent);
        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(WaitingScreenActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(WaitingScreenActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WaitingScreenActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.LOCATION_REQUEST);

        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(WaitingScreenActivity.this, location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        sucess = true;
                        Log.d("waiting", "1: ");
                        count();
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (isContinue) {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    } else {
                        mFusedLocationClient.getLastLocation().addOnSuccessListener(WaitingScreenActivity.this, location -> {
                            if (location != null) {
                                wayLatitude = location.getLatitude();
                                wayLongitude = location.getLongitude();
                                Log.d("waiting", "2: ");
                                count();
                            } else {
                                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }
}


