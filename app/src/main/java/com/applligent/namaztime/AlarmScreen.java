package com.applligent.namaztime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AlarmScreen extends AppCompatActivity {
    TextView FajrAlarm;
    TextView ZuharAlarm;
    TextView AsarAlarm;
    TextView MaghribAlarm;
    TextView IshaAlarm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_screen);

        FajrAlarm = findViewById(R.id.alarm_fajr_time);
        ZuharAlarm = findViewById(R.id.alarm_zuhar_time);
        AsarAlarm = findViewById(R.id.alarm_asar_time);
        MaghribAlarm = findViewById(R.id.alarm_maghrib_time);
        IshaAlarm = findViewById(R.id.alarm_isha_time);

        String forFajr = getIntent().getExtras().getString("Fajr");
        String forZuhar = getIntent().getExtras().getString("Zuhar");
        String forAsar = getIntent().getExtras().getString("Asar");
        String forMaghrib = getIntent().getExtras().getString("Maghrib");
        String forIsha = getIntent().getExtras().getString("Isha");

        FajrAlarm.setText(forFajr);
        ZuharAlarm.setText(forZuhar);
        AsarAlarm.setText(forAsar);
        MaghribAlarm.setText(forMaghrib);
        IshaAlarm.setText(forIsha);


    }
}