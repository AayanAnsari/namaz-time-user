package com.applligent.namaztime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    Handler h = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        boolean first = getSharedPreferences("myPref",MODE_PRIVATE).getBoolean("firstBoot",true);

        if (first){
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this,SelectLanguage.class);
                    startActivity(intent);
                    finish();

                }
            },2000);
        }
        else
        {
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent1);
                    finish();

                }
            },2000);
        }







    }


}