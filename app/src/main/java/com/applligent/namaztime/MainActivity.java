package com.applligent.namaztime;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.applligent.namaztime.Api.ApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    TextView islamic_Date;

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
                Intent intent = new Intent(MainActivity.this,city_name.class);
                startActivity(intent);
            }
        });

//        Current Date and Month

        TextView monthAndYear,currentDateTV;
        currentDateTV = findViewById(R.id.currentDate);
        monthAndYear = findViewById(R.id.MandYname);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat forDate = new SimpleDateFormat("dd");
        String DD = forDate.format(calendar.getTime());
        currentDateTV.setText(DD);
        SimpleDateFormat forMonthYear = new SimpleDateFormat("MMMM yyyy");
        String MY = forMonthYear.format(calendar.getTime());
        monthAndYear.setText(MY);

//        Hijri Calender


        islamic_Date = findViewById(R.id.islamic_fulldate);
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

        getNamazTime();




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

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                try {
                    String res = new Gson().toJson(response.body());
                    JSONObject mainObject = new JSONObject(res);
                    JSONArray dataArray = mainObject.getJSONArray("data");
                    JSONObject finalObject = dataArray.getJSONObject(0).getJSONObject("timings");
//                    System.out.println("RESPONSE "+finalObject);

                    String fajr_str = finalObject.getString("Fajr");
                    fajr_str = fajr_str.replaceAll("[(IST)]", "");
//                    fajrTV.setText(fajr_str + "AM");
//                    fajrTV.setText(finalObject.getString("Fajr"));
                    String[] A1 = fajr_str.split(":");
                    int h1 = Integer.parseInt(A1[0]);
                    String m1 = A1[1];
                    if (h1>12)
                    {
                        h1 = h1-12;
                        fajrTV.setText(h1+":"+m1+"PM");
                    }else {
                        if (h1<10)
                        {
                            fajrTV.setText("0"+h1+":"+m1+"PM");
                        }else {
                            fajrTV.setText(h1 + ":" + m1 + "AM");
                        }
                    }

                    String zuhar_str = finalObject.getString("Dhuhr");
                    zuhar_str = zuhar_str.replaceAll("[(IST)]","");
                    String[] A2 = zuhar_str.split(":");
                    int h2 = Integer.parseInt(A2[0]);
                    String m2 = A2[1];
                    if (h2>12)
                    {
                        h2 = h2-12;
                        if (h2<10)
                        {
                            zuharTV.setText("0"+h2+":"+m2+"PM");
                        }else {
                            zuharTV.setText(h2+":"+m2+"PM");
                        }

                    }else {
                        zuharTV.setText(h2+":"+m2+"PM");
                    }

                    String asar_str = finalObject.getString("Asr");
                    asar_str = asar_str.replaceAll("[(IST)]","");
                    String[] A3 = asar_str.split(":");
                    int h3 = Integer.parseInt(A3[0]);
                    String m3 = A3[1];
                    if (h3>12)
                    {
                        h3 = h3-12;
                        if (h3<10)
                        {
                            asarTV.setText("0"+h3+":"+m3+"PM");
                        }else {
                            asarTV.setText(h3+":"+m3+"PM");
                        }
                    }else {
                        asarTV.setText(h3+":"+m3+"AM");
                    }

                    String maghrib_str = finalObject.getString("Maghrib");
                    maghrib_str = maghrib_str.replaceAll("[(IST)]","");
                    String[] A4 = maghrib_str.split(":");
                    int h4 = Integer.parseInt(A4[0]);
                    String m4 = A4[1];
                    if (h4>12)
                    {
                        h4 = h4-12;
                        if (h4<10)
                        {
                            maghribTV.setText("0"+h4+":"+m4+"PM");
                        }else {
                            maghribTV.setText(h4+":"+m4+"PM");
                        }
                    }else {
                        maghribTV.setText(h4+":"+m4+"AM");
                    }

                    String isha_str = finalObject.getString("Isha");
                    isha_str = isha_str.replaceAll("[(IST)]","");
                    String[] A5 = isha_str.split(":");
                    int h5 = Integer.parseInt(A5[0]);
                    String m5 = A5[1];
                    if (h5>12)
                    {
                        h5 = h5-12;
                        if (h5<10)
                        {
                            ishaTV.setText("0"+h5+":"+m5+"PM");
                        }else {
                            ishaTV.setText(h5+":"+m5+"PM");
                        }
                    }else {
                        ishaTV.setText(h5+":"+m5+"AM");
                    }
//                    Log.i("TAG","onResponse: asff"+hour);



//                    Log.i("TAG","onResponse: abaab" + isha_str);
//                    String ss = "04:42";
//                    String[] saa = ss.split(":");
//                    Log.i("TAG", "onResponse: dfdsjkfsl "+saa[0]+"  "+saa[1]);
//                    int kk = Integer.parseInt(saa[0]) ;
//                    Log.i("TAG", "onResponse: dfjsdkfk "+kk);


                }catch (Exception e){
                    Log.i("tag",e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getHijriDate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd MMMM yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String Dates = date.format(calendar.getTime());
//        int int_date = Integer.parseInt(Date1);
//        SimpleDateFormat month = new SimpleDateFormat("mm");
//        String Month1 = month.format(calendar.getTime());
//        int int_month = Integer.parseInt(Month1);
//        SimpleDateFormat year = new SimpleDateFormat("yyyy");
//        String Year1 = year.format(calendar.getTime());
//        int int_Year = Integer.parseInt(Year1);

        LocalDate localDate = LocalDate.parse(Dates, dateTimeFormatter);
        HijrahDate islamicDate = HijrahDate.from(localDate);
        String ist = islamicDate.toString();
        islamic_Date.setText(ist);



        Log.i("TAG", "getHijriDate: abcgsa"+islamicDate);





    }




}