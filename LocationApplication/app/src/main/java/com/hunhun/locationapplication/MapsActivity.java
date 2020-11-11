package com.hunhun.locationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {

    private GoogleMap googleMap;
    double latitude, longitude;
    Button koordinat, posisi_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        koordinat = findViewById(R.id.koordinat);
        posisi_user = findViewById(R.id.posisi_user);

        koordinat.setOnClickListener(this);
        posisi_user.setOnClickListener(this);

        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = fm.getMap();

        if (latitude != 0 && longitude != 0) {
            Toast.makeText(getApplicationContext(), "Latitude : " + latitude + " Longitude : " + longitude, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT>=23){
            if (periksaIzinLokasi()){
                cekGPS();
                googleMap.setMyLocationEnabled(true);
            }
        }
    }

    public boolean periksaIzinLokasi(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 100:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    cekGPS();
                    googleMap.setMyLocationEnabled(true);
                }
                break;
        }
    }

    public void cekGPS() {
        try {

            /* Pengecekan GPS hidup / tidak */
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Info");
                builder.setMessage("Anda akan mengaktifkan GPS ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int witch) {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(i);
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int witch) {

                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // menampilkan status google play service
        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        } else {
            // Google Play Service Tersedia
            try {
                LocationManager locationManager = (LocationManager)
                        getSystemService(LOCATION_SERVICE);

                // Membuat Kriteria Untuk Penumpangan Provider
                Criteria criteria = new Criteria();

                // Mencari Provider Terbaik
                String provider = locationManager.getBestProvider(criteria, true);

                // Mendapatkan Lokasi Terakhir
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                Location location = locationManager.getLastKnownLocation(provider);

                if(location != null){
                    onLocationChanged(location);
                }
                locationManager.requestLocationUpdates(provider,5000,0, this);
            } catch (Exception e){
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //TODO Auto-generated method stub
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider){
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider){
        // TODO Auto-generated method stud
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){
        // TODO Auto-generated method stub
    }


    @Override
    public void onClick(View v) {
        if(v==koordinat){
            if(latitude!=0 && longitude!=0){
                Toast.makeText(getApplicationContext(), "Latitude : "+latitude+" Longitude : "+longitude, Toast.LENGTH_LONG).show();
            }
        } else if (v==posisi_user){
            LatLng user = new LatLng(latitude,longitude);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user, 12));
        }
    }
}