package com.applligent.namaztime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.applligent.namaztime.ChangeLanguage.LangCompat;
import com.applligent.namaztime.ChangeLanguage.LanguageManager;

public class SelectLanguage extends LangCompat {

    Button engBtn,hindiBtn,urduBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

        engBtn = findViewById(R.id.english_btn);
        hindiBtn = findViewById(R.id.hindi_btn);
        urduBtn = findViewById(R.id.urdu_btn);
        LanguageManager lang = new LanguageManager(this);

        engBtn.setOnClickListener(view -> {
            lang.updateResource("en");
            Intent i = new Intent(SelectLanguage.this,LocationActivity.class);
            startActivity(i);
        });

        hindiBtn.setOnClickListener(view -> {
            lang.updateResource("hi");
            Intent i = new Intent(SelectLanguage.this,LocationActivity.class);
            startActivity(i);
        });

        urduBtn.setOnClickListener(view -> {
            lang.updateResource("ur");
            Intent i = new Intent(SelectLanguage.this,LocationActivity.class);
            startActivity(i);
        });

        SharedPreferences prefs = getSharedPreferences("myPref",MODE_PRIVATE);
        boolean firstBoot = prefs.getBoolean("firstBoot",true);

        if (firstBoot)
        {
            getSharedPreferences("myPref",MODE_PRIVATE)
                    .edit()
                    .putBoolean("firstBoot",false)
                    .commit();
        }



    }
}