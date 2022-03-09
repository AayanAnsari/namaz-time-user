package com.applligent.namaztime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {

    Button toChooseLocaation;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        toChooseLocaation = findViewById(R.id.location_btn);
        toChooseLocaation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this,Change_Location.class);
                startActivity(intent);
            }
        });

        skip = findViewById(R.id.skip_btn);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationActivity.this,SelectLanguage.class);
                startActivity(i);
            }
        });

    }
}