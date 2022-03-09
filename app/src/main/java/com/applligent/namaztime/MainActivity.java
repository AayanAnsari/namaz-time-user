package com.applligent.namaztime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView TB_title;
    Button near_by_btn;


    Dialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        TB_title = toolbar.findViewById(R.id.toolbar_title);
        near_by_btn = findViewById(R.id.near_by_masjid_btn);

        // sliding menu bar and item click----------->

        setSupportActionBar(toolbar);
//      TB_title.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.change_language_m:

                        Intent intent1 = new Intent(MainActivity.this,SelectLanguage.class);
                        startActivity(intent1);
                        break;

                    case R.id.rate_app_m:
                        Toast.makeText(MainActivity.this, "Rate App", Toast.LENGTH_SHORT).show();
                        drawerLayout.close();
                        break;

                    case R.id.share_app_m:
                        drawerLayout.close();
                        shareApp();
                        break;

                    case R.id.our_apps_m:
                        Toast.makeText(MainActivity.this, "Our Apps", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.contact_us_m:
                        Intent intent5 = new Intent(MainActivity.this,Contact_Us.class);
                        startActivity(intent5);
                        break;

                    case R.id.about_m:
//                        Toast.makeText(MainActivity.this, "About The App", Toast.LENGTH_SHORT).show();
                        drawerLayout.close();
                        showAboutAppDialog();
                        break;
                }
                return true;
            }
        });

        View header = navigationView.getHeaderView(0);
        ImageView closedrawer_btn = header.findViewById(R.id.black_menu_ic);
        closedrawer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.close();
            }
        });




        near_by_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,city_name.class);
                startActivity(intent);
            }
        });


        //Push Notification Code --->
        FirebaseMessaging.getInstance().subscribeToTopic("namaztime")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });



    }


    public void showAboutAppDialog() {

        customDialog = new Dialog(this);
        customDialog.setContentView(R.layout.about_app_dialogue);
        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        customDialog.setCancelable(false);

        Button Got_it_btn = customDialog.findViewById(R.id.got_it);
        Got_it_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });
        customDialog.show();

    }

    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "Download -https://play.google.com/store/apps/details?id=com.whatsapp&hl=en";
        String shareSub = "Whatsapp apk";

        shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);

        startActivity(Intent.createChooser(shareIntent,"Share Via"));

    }


}