package com.example.cgv.gps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button b1, b2, b3, b4;
    double lat, lng;
    Location loc1 = new Location("");
    Location loc2 = new Location("");
    private LocationManager manager;
    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //TextView latitude = (TextView) findViewById(R.id.lat1);
                //TextView longitude = (TextView) findViewById(R.id.long1);

                //latitude.setText("" + location.getLatitude());
                //longitude.setText("" + location.getLongitude());
                lat = location.getLatitude();
                lng = location.getLongitude();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);

            }
        };
        configure_button();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
            //return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                manager.requestLocationUpdates("gps", 0, 0, listener);

            }
        });

        b2 = (Button) findViewById(R.id.d1);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppendValue();
            }
        });


        b3 = (Button) findViewById(R.id.d2);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Append2Value();
            }
        });

        b4 = (Button) findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Distance();
            }
        });
    }

    private void AppendValue() {
        TextView latitude = (TextView) findViewById(R.id.lat1);
        TextView longitude = (TextView) findViewById(R.id.long1);

        latitude.setText("" + lat);
        longitude.setText("" + lng);

        //Location loc1 = new Location("");
        loc1.setLatitude(lat);
        loc1.setLongitude(lng);
    }

    private void Append2Value() {
        TextView latitude2 = (TextView) findViewById(R.id.lat2);
        TextView longitude2 = (TextView) findViewById(R.id.long2);

        latitude2.setText("" + lat);
        longitude2.setText("" + lng);

        //Location loc2 = new Location("");
        loc2.setLatitude(lat);
        loc2.setLongitude(lng);
    }

    private void Distance() {

        float d = loc1.distanceTo(loc2);
        TextView Distance = (TextView) findViewById(R.id.dist);
        Distance.setText("" + d);

    }

}
