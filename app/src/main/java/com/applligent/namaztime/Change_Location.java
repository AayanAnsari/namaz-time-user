package com.applligent.namaztime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Change_Location extends AppCompatActivity {

    ImageView close_Btn;
    RelativeLayout setCurrentLocation;
    EditText input_location;


    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String DateTime;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_location);

        close_Btn = findViewById(R.id.close_location_screen);
        close_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Change_Location.this,LocationActivity.class);
                startActivity(intent);
            }
        });
        //        Time
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM hh:mm");
        DateTime = simpleDateFormat.format(calendar.getTime());


        input_location = findViewById(R.id.input_location);
        setCurrentLocation = findViewById(R.id.use_currrent_location);
        setCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        //        Location
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Change_Location.this);
                if (ActivityCompat.checkSelfPermission(Change_Location.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    boolean connected = false;
                    ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
                    {
                        connected = true;
                        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Change_Location.this);

                            alertDialog.setTitle("GPS is settings");
                            alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
                            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int which) {
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(intent);
                                }
                            });

                            // on pressing cancel button
                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            alertDialog.show();
                            // Toast.makeText(Change_Location.this, "Turn On Location", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            getLocation();
                        }
                    }else
                    {
                        Toast.makeText(Change_Location.this, "No Internet", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    ActivityCompat.requestPermissions
                            (Change_Location.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

                }

            }
        });


    }



    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null)
                {

                    try {
                        Geocoder geocoder = new Geocoder(Change_Location.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String country = addresses.get(0).getCountryName();

                        Toast.makeText(Change_Location.this,country +" "+ city+" "+DateTime, Toast.LENGTH_SHORT).show();
                        input_location.setText(city+","+country);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(Change_Location.this, "Detecting Location", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}