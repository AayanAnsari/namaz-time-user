package com.applligent.namaztime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectLanguage extends AppCompatActivity {

    Button engBtn,hindiBtn,urduBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

        engBtn = findViewById(R.id.english_btn);
        hindiBtn = findViewById(R.id.hindi_btn);
        urduBtn = findViewById(R.id.urdu_btn);

        engBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectLanguage.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}