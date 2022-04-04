package com.applligent.namaztime;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.IslamicCalendar;
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
import com.applligent.namaztime.ChangeLanguage.LangCompat;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import net.time4j.SystemClock;
import net.time4j.calendar.HijriCalendar;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.format.expert.PatternType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends LangCompat {

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
    TextView timeLeft,timeGone;

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
        timeGone = findViewById(R.id.timeGone);
        timeLeft = findViewById(R.id.timeLeft);

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getHijriDate() {


        UmmalquraCalendar cal = new UmmalquraCalendar();
        int islYear = cal.get(Calendar.YEAR);
        int islMonth = cal.get(Calendar.MONTH);
        int islDate = cal.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat myDateFormat = new SimpleDateFormat("",Locale.ENGLISH);
        myDateFormat.setCalendar(cal);

        myDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
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

            @SuppressLint("ResourceAsColor")
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


                    String sunrise_str = finalObject.getString("Sunrise");
                    sunrise_str = sunrise_str.replaceAll("[ (IST)]","");


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
                    LocalTime sunriseTime = LocalTime.parse(sunrise_str);
                    LocalTime zuharTime = LocalTime.parse(zuhar_str);
                    LocalTime asarTime = LocalTime.parse(asar_str);
                    LocalTime maghribTime = LocalTime.parse(maghrib_str);
                    LocalTime ishaTime = LocalTime.parse(isha_str);
                    LocalTime midNIGHT = LocalTime.parse("23:59");



                    //Curretn TIME
                    calendar = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("HH:mm");
                    CurrentTime = simpleDateFormat.format(calendar.getTime());
                    LocalTime CTime = LocalTime.parse(CurrentTime);
                    Log.i("TAG", "onResponse: bgfh"+"CT -"+CTime);

//                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//                    Date CTforCompare = sdf.parse(String.valueOf(CTime));
//                    Date time2 = sdf.parse(asar_str);
//
//                    long diff = CTforCompare.getTime() - time2.getTime();
//
//                    int sec  = (int)(diff/ 1000) % 60 ;
//                    int min  = (int)((diff/ (1000*60)) % 60);
//                    int hr   = (int)((diff/ (1000*60*60)) % 24);
//
//                    Log.i("TAG", "onResponse: nbsanbs"+hr+":"+min);








                    if (CTime.isAfter(fajrTime) && CTime.isBefore(sunriseTime) || CTime.equals(fajrTime))
                    {
                        nowN.setText(R.string.fajr);
                        upcomingN.setText(R.string.zuhar);
                        rl1.setBackgroundResource(R.drawable.tv_greenborder);

                        getCurrentAndUpcoming(fajr_str,zuhar_str,CTime);
                    }
                    else if (CTime.isAfter(sunriseTime) && CTime.isBefore(zuharTime) || CTime.equals(sunriseTime))
                    {
                        upcomingN.setText(R.string.zuhar);
                        rl2.setBackgroundResource(R.drawable.tv_greenborder);

                        timeGone.setVisibility(View.INVISIBLE);
                        getCurrentAndUpcoming(fajr_str,zuhar_str,CTime);
                    }
                    else if (CTime.isAfter(zuharTime) && CTime.isBefore(asarTime) || CTime.equals(zuharTime))
                    {
                        nowN.setText(R.string.zuhar);
                        upcomingN.setText(R.string.asar);
                        rl2.setBackgroundResource(R.drawable.tv_greenborder);

                        getCurrentAndUpcoming(zuhar_str,asar_str,CTime);
                    }
                    else if(CTime.isAfter(asarTime) && CTime.isBefore(maghribTime) || CTime.equals(asarTime))
                    {
                        nowN.setText(R.string.asar);
                        upcomingN.setText(R.string.maghrib);
                        rl3.setBackgroundResource(R.drawable.tv_greenborder);

                        getCurrentAndUpcoming(asar_str,maghrib_str,CTime);
                    }
                    else if (CTime.isAfter(maghribTime) && CTime.isBefore(ishaTime) || CTime.equals(maghribTime))
                    {
                        nowN.setText(R.string.maghrib);
                        upcomingN.setText(R.string.isha);
                        rl4.setBackgroundResource(R.drawable.tv_greenborder);

                        getCurrentAndUpcoming(maghrib_str,isha_str,CTime);
                    }
//                    else if(CTime.isAfter(ishaTime) && CTime.isBefore(IshaToFajr) || CTime.equals(ishaTime))
//                    {
//                        nowN.setText(R.string.isha);
//                        upcomingN.setText(R.string.fajr);
//                        rl5.setBackgroundResource(R.drawable.tv_greenborder);
//                    }
                    else {
                        nowN.setText(R.string.isha);
                        upcomingN.setText(R.string.fajr);

                        SimpleDateFormat formattedString = new SimpleDateFormat("HH:mm");
                        Date CTforCompare = formattedString.parse(String.valueOf(CTime));
                        String midNightString = ("23:59");
                        String midNight1String = ("00:01");

                        Date midNight = formattedString.parse(midNightString);
//                        Log.i("TAG", "onResponse: fasdas"+midNight);
                        Date midNight1 = formattedString.parse(midNight1String);
//                        Log.i("TAG", "onResponse: fefdsa"+midNight1);
                        Date fajrMN = formattedString.parse(fajr_str);
                        Date ishaMN = formattedString.parse(isha_str);

                        if (CTime.isAfter(ishaTime) && CTime.isBefore(midNIGHT) )
                        {
                            long diffCTtoMidnight = midNight.getTime()-CTforCompare.getTime();
                            Log.i("TAG", "onResponse: abcdefgh"+diffCTtoMidnight);

                            long a = 86400000;

                            long diffMidnightToFajr = midNight.getTime()-fajrMN.getTime();
                            diffMidnightToFajr = (a-diffMidnightToFajr);
                            Log.i("TAG", "onResponse: abcdefg1"+diffMidnightToFajr);
//
                            long exactDifference = (diffCTtoMidnight + diffMidnightToFajr);
                            int min  = (int)((exactDifference/ (1000*60)) % 60);
                            int hr   = (int)((exactDifference/ (1000*60*60)) % 24);
                            String mins = Integer.toString(min);
                            String hrs = Integer.toString(hr);
                            String finalR = (hrs+"h"+":"+mins+"min");

                            Log.i("TAG", "onResponse: fsdfad"+finalR);
                            timeLeft.setText(finalR);
                            timeLeft.setTextColor(getResources().getColor(R.color.green));


//                            red---
                            long ishaToCT = CTforCompare.getTime() - ishaMN.getTime();
                            int min2  = (int)((ishaToCT/ (1000*60)) % 60);
                            int hr2   = (int)((ishaToCT/ (1000*60*60)) % 24);
                            String mins2 = Integer.toString(min2);
                            String hrs2 = Integer.toString(hr2);
                            String finalR2 = (hrs2+"h"+":"+mins2+"min");
                            timeGone.setText(finalR2);
                            timeGone.setTextColor(getResources().getColor(R.color.Red));
                        }
                        else
                        {
                            long diffIshaToMidNight = midNight.getTime() - ishaMN.getTime();

                            GregorianCalendar now = new GregorianCalendar();
                            GregorianCalendar start = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            long midNightToCT = now.getTimeInMillis() - start.getTimeInMillis();


                            long diffIshaToCT = diffIshaToMidNight + midNightToCT;
                            int min  = (int)((diffIshaToCT/ (1000*60)) % 60);
                            int hr   = (int)((diffIshaToCT/ (1000*60*60)) % 24);
                            String mins = Integer.toString(min);
                            String hrs = Integer.toString(hr);
                            String finalR = (hrs+"h"+":"+mins+"min");
                            Log.i("TAG", "onResponse: sadq"+finalR);
                            timeGone.setText(finalR);
                            timeGone.setTextColor(getResources().getColor(R.color.Red));

//                            Upcoming
                            long a = 86400000;
                            long diffMidnightToFajr = midNight.getTime()-fajrMN.getTime();
                            diffMidnightToFajr = (a-diffMidnightToFajr);

                            GregorianCalendar nowUpcoming = new GregorianCalendar();
                            GregorianCalendar startUpcoming = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            long ms = nowUpcoming.getTimeInMillis() - startUpcoming.getTimeInMillis();

                            long leftForFajr = diffMidnightToFajr - ms;
                            int min2  = (int)((leftForFajr/ (1000*60)) % 60);
                            int hr2   = (int)((leftForFajr/ (1000*60*60)) % 24);
                            String mins2 = Integer.toString(min2);
                            String hrs2 = Integer.toString(hr2);
                            String finalR2 = (hrs2+"h"+":"+mins2+"min");
                            timeLeft.setText(finalR2);
                            timeLeft.setTextColor(getResources().getColor(R.color.green));
                        }


                        rl5.setBackgroundResource(R.drawable.tv_greenborder);
                    }



                }catch (Exception e){
                    Log.i("tag",e.getMessage());
                }

            }

            private void getCurrentAndUpcoming(String firstN, String secondN, LocalTime CurrTime) throws ParseException {

                String firstNamaz = firstN;
                String secodNamaz = secondN;
                LocalTime currentTime = CurrTime;

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date CTforCompare = sdf.parse(String.valueOf(currentTime));
                Date firstStart = sdf.parse(firstNamaz);
                long firstDiff = CTforCompare.getTime()-firstStart.getTime();

                int min  = (int)((firstDiff/ (1000*60)) % 60);
                int hr   = (int)((firstDiff/ (1000*60*60)) % 24);
                String mins = Integer.toString(min);
                String hrs = Integer.toString(hr);
                String finalR = (hrs+"h"+":"+mins+"min");
                timeGone.setText(finalR);
                timeGone.setTextColor(Color.RED);
//
//                Log.i("TAG", "onResponse: rwer"+finalR);
//
                Date secondStart = sdf.parse(secodNamaz);
                long secondDiff = secondStart.getTime()-CTforCompare.getTime();
                int Smin  = (int)((secondDiff/ (1000*60)) % 60);
                int Shr   = (int)((secondDiff/ (1000*60*60)) % 24);
                String Smins = Integer.toString(Smin);
                String Shrs = Integer.toString(Shr);
                String finalM = (Shrs+"h"+":"+Smins+"min");
                timeLeft.setText(finalM);
                timeLeft.setTextColor(getResources().getColor(R.color.green));

            }


            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }



}
