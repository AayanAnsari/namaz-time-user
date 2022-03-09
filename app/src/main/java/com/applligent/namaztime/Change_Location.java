package com.applligent.namaztime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Change_Location extends AppCompatActivity {

    ImageView close_Btn;

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
//                finish();
            }
        });

    }
}