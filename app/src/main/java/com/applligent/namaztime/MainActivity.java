package com.applligent.namaztime;

import android.app.Dialog;
import android.content.Intent;
import android.icu.util.IslamicCalendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.applligent.namaztime.Api.ApiClient;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView TB_title;
    Button near_by_btn;

    Dialog customDialog;

    TextView fajrTV;
    TextView zuharTV;
    TextView asarTV;
    TextView maghribTV;
    TextView ishaTV;
    TextView nowN;
    TextView upcomingN;
    RelativeLayout rl1;
    RelativeLayout rl2;
    RelativeLayout rl3;
    RelativeLayout rl4;
    RelativeLayout rl5;

    TextView monthAndYear,currentDateTV;
    TextView islamic_Date;
    TextView islamic_FullDate;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String CurrentTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                String NN = nowN.getText().toString();
                String UN = upcomingN.getText().toString();
                Intent intent = new Intent(MainActivity.this,city_name.class);
                intent.putExtra("nowNamazTime",NN);
                intent.putExtra("upcomingNamazTime",UN);

                startActivity(intent);
            }
        });

//        Current Date and Month
        currentDateTV = findViewById(R.id.currentDate);
        monthAndYear = findViewById(R.id.MandYname);
        getEnglishDate();

//        Hijri Calender
        islamic_Date = findViewById(R.id.islamic_date);
        islamic_FullDate = findViewById(R.id.islamic_fulldate);
        getHijriDate();




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



//        Retrofit Part
        fajrTV = findViewById(R.id.time_of_fajr);
        zuharTV = findViewById(R.id.time_of_zuhar);
        asarTV = findViewById(R.id.time_of_asar);
        maghribTV = findViewById(R.id.time_of_maghrib);
        ishaTV = findViewById(R.id.time_of_isha);

        nowN = findViewById(R.id.now_namaz_name);
        upcomingN = findViewById(R.id.upcoming_namaz_name);

        rl1 = findViewById(R.id.RL_1);
        rl2 = findViewById(R.id.RL_2);
        rl3 = findViewById(R.id.RL_3);
        rl4 = findViewById(R.id.RL_4);
        rl5 = findViewById(R.id.RL_5);


        getNamazTime();




    }

    private void getEnglishDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat forDate = new SimpleDateFormat("dd");
        String DD = forDate.format(calendar.getTime());
        currentDateTV.setText(DD);
        SimpleDateFormat forMonthYear = new SimpleDateFormat("MMMM yyyy");
        String MY = forMonthYear.format(calendar.getTime());
        monthAndYear.setText(MY);
    }

    private void getHijriDate() {

        UmmalquraCalendar cal = new UmmalquraCalendar();
        int islYear = cal.get(Calendar.YEAR);
        int islMonth = cal.get(Calendar.MONTH);
        int islDate = cal.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat myDateFormat = new SimpleDateFormat("",Locale.ENGLISH);
        myDateFormat.setCalendar(cal);

        myDateFormat.applyPattern("dd");
        String myIslamicDt = myDateFormat.format(cal.getTime());

        myDateFormat.applyPattern("MMMM yyyy");
        String myMonthYear = myDateFormat.format(cal.getTime());
        islamic_Date.setText(myIslamicDt);
        islamic_FullDate.setText(myMonthYear);

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


    private void getNamazTime() {
        Call<Object> call = ApiClient.getInstance()
                            .getApi()
                            .getDefaultTime("Ujjain","India");

        call.enqueue(new Callback<Object>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                try {
                    String res = new Gson().toJson(response.body());
                    JSONObject mainObject = new JSONObject(res);
                    JSONArray dataArray = mainObject.getJSONArray("data");
                    JSONObject finalObject = dataArray.getJSONObject(0).getJSONObject("timings");
//                    System.out.println("RESPONSE "+finalObject);

                    String fajr_str = finalObject.getString("Fajr");
                    fajr_str = fajr_str.replaceAll("[ (IST)]", "");
//                    fajrTV.setText(fajr_str + "AM");
//                    fajrTV.setText(finalObject.getString("Fajr"));
                    String[] A1 = fajr_str.split(":");
                    int hh1 = Integer.parseInt(A1[0]);
                    int h1 = Integer.parseInt(A1[0]);
                    String m1 = A1[1];
                    if (h1>12)
                    {
                        h1 = h1-12;
                        fajrTV.setText(h1+":"+m1+" PM");
                    }else {
                        if (h1<10)
                        {
                            fajrTV.setText("0"+h1+":"+m1+" AM");
                        }else {
                            fajrTV.setText(h1 + ":" + m1 + " AM");
                        }
                    }

                    String zuhar_str = finalObject.getString("Dhuhr");
                    zuhar_str = zuhar_str.replaceAll("[ (IST)]","");
                    String[] A2 = zuhar_str.split(":");
                    int hh2 = Integer.parseInt(A2[0]);
                    int h2 = Integer.parseInt(A2[0]);
                    String m2 = A2[1];
                    if (h2>12)
                    {
                        h2 = h2-12;
                        if (h2<10)
                        {
                            zuharTV.setText("0"+h2+":"+m2+" PM");
                        }else {
                            zuharTV.setText(h2+":"+m2+" PM");
                        }

                    }else {
                        zuharTV.setText(h2+":"+m2+" PM");
                    }

                    String asar_str = finalObject.getString("Asr");
                    asar_str = asar_str.replaceAll("[ (IST)]","");
                    String[] A3 = asar_str.split(":");
                    int hh3 = Integer.parseInt(A3[0]);
                    int h3 = Integer.parseInt(A3[0]);
                    String m3 = A3[1];
                    if (h3>12)
                    {
                        h3 = h3-12;
                        if (h3<10)
                        {
                            asarTV.setText("0"+h3+":"+m3+" PM");
                        }else {
                            asarTV.setText(h3+":"+m3+" PM");
                        }
                    }else {
                        asarTV.setText(h3+":"+m3+" AM");
                    }

                    String maghrib_str = finalObject.getString("Maghrib");
                    maghrib_str = maghrib_str.replaceAll("[ (IST)]","");
                    String[] A4 = maghrib_str.split(":");
                    int hh4 = Integer.parseInt(A4[0]);
                    int h4 = Integer.parseInt(A4[0]);
                    String m4 = A4[1];
                    if (h4>12)
                    {
                        h4 = h4-12;
                        if (h4<10)
                        {
                            maghribTV.setText("0"+h4+":"+m4+" PM");
                        }else {
                            maghribTV.setText(h4+":"+m4+" PM");
                        }
                    }else {
                        maghribTV.setText(h4+":"+m4+" AM");
                    }

                    String isha_str = finalObject.getString("Isha");
                    isha_str = isha_str.replaceAll("[ (IST)]","");
                    String[] A5 = isha_str.split(":");
                    int hh5 = Integer.parseInt(A5[0]);
                    int h5 = Integer.parseInt(A5[0]);
                    String m5 = A5[1];
                    if (h5>12)
                    {
                        h5 = h5-12;
                        if (h5<10)
                        {
                            ishaTV.setText("0"+h5+":"+m5+" PM");
                        }else {
                            ishaTV.setText(h5+":"+m5+" PM");
                        }
                    }else {
                        ishaTV.setText(h5+":"+m5+" AM");
                    }


                    LocalTime fajrTime = LocalTime.parse(fajr_str);
                    LocalTime zuharTime = LocalTime.parse(zuhar_str);
                    LocalTime asarTime = LocalTime.parse(asar_str);
                    LocalTime maghribTime = LocalTime.parse(maghrib_str);
                    LocalTime ishaTime = LocalTime.parse(isha_str);



                    //Curretn TIME
                    calendar = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("HH:mm");
                    CurrentTime = simpleDateFormat.format(calendar.getTime());
                    LocalTime CTime = LocalTime.parse(CurrentTime);
                    Log.i("TAG", "onResponse: bgfh"+"CT -"+CTime);


                    String[] AA = CurrentTime.split(":");
                    int CurrentH = Integer.parseInt(AA[0]);
                    int CurrentM = Integer.parseInt(AA[1]);
                    Log.i("TAG", "CurrentNamazTime: qrres"+CurrentM);

                    if (CTime.isAfter(fajrTime) && CTime.isBefore(zuharTime) || CTime.equals(fajrTime))
                    {
                        nowN.setText("Fajr");
                        upcomingN.setText("Zuhar");
                        rl1.setBackgroundResource(R.drawable.tv_greenborder);
                    }
                    else if (CTime.isAfter(zuharTime) && CTime.isBefore(asarTime) || CTime.equals(zuharTime))
                    {
                        nowN.setText("Zuhar");
                        upcomingN.setText("Asar");
                        rl2.setBackgroundResource(R.drawable.tv_greenborder);
                    }
                    else if(CTime.isAfter(asarTime) && CTime.isBefore(maghribTime) || CTime.equals(asarTime))
                    {
                        nowN.setText("Asar");
                        upcomingN.setText("Maghrib");
                        rl3.setBackgroundResource(R.drawable.tv_greenborder);
                    }
                    else if (CTime.isAfter(maghribTime) && CTime.isBefore(ishaTime) || CTime.equals(maghribTime))
                    {
                        nowN.setText("Maghrib");
                        upcomingN.setText("Isha");
                        rl4.setBackgroundResource(R.drawable.tv_greenborder);
                    }
                    else if(CTime.isAfter(ishaTime) && CTime.isBefore(LocalTime.parse("23:59")) || CTime.equals(ishaTime))
                    {
                        nowN.setText("Isha");
                        upcomingN.setText("Fajr");
                        rl5.setBackgroundResource(R.drawable.tv_greenborder);
                    }else {
                        nowN.setText("Isha");
                        upcomingN.setText("Fajr");
                        rl5.setBackgroundResource(R.drawable.tv_greenborder);
                    }



                }catch (Exception e){
                    Log.i("tag",e.getMessage());
                }

            }




            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }



}
