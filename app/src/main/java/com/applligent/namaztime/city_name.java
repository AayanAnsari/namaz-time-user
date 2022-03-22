package com.applligent.namaztime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.applligent.namaztime.cityNameApi.CityAdapter;
import com.applligent.namaztime.cityNameApi.CityApi;
import com.applligent.namaztime.cityNameApi.CityApiInterface;
import com.applligent.namaztime.cityNameApi.RowItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class city_name extends AppCompatActivity {

    // Drawer layout sliding menu bar

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView TB_title;


  //    dropdown-->
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

//    ArrayList<String> arrayList_city;
//    ArrayAdapter<String> arrayAdapter_city;

    List<RowItem> citynamelist = new ArrayList<>();
    CityAdapter cityArrayAdapter;

    // Recycler View for Masjid Details ---->
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<city_name_ModelClass> masjidlist;
    city_name_Adapter adapter;
    Spinner citySpinnner;


    String deviceToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_name);

        drawerLayout = findViewById(R.id.city_drawerlayout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        TB_title = toolbar.findViewById(R.id.toolbar_title);
        CardView cardView1 = findViewById(R.id.card_view);
        cardView1.setBackgroundResource(R.drawable.top_corner_radius);

        //toggle toolbar
        setSupportActionBar(toolbar);

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
                        Intent intent1 = new Intent(city_name.this,SelectLanguage.class);
                        startActivity(intent1);
                        break;

                    case R.id.rate_app_m:
                        Toast.makeText(city_name.this, "Rate App", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.share_app_m:
                        drawerLayout.close();
                        shareApp();
                        break;

                    case R.id.our_apps_m:
                        Toast.makeText(city_name.this, "Our Apps", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.contact_us_m:
                        Intent intent5 = new Intent(city_name.this,Contact_Us.class);
                        startActivity(intent5);
                        break;

                    case R.id.about_m:
//                        Toast.makeText(city_name.this, "About App", Toast.LENGTH_SHORT).show();
                        drawerLayout.close();
                        showAboutAppDialog();
                        break;

                }
                return true;
            }
        });
        //drawer closer button
        View header = navigationView.getHeaderView(0);
        ImageView closedrawer_btn = header.findViewById(R.id.black_menu_ic);
        closedrawer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.close();
            }
        });



//        //dropDown
        textInputLayout = findViewById(R.id.drop_down_menu);
        autoCompleteTextView = findViewById(R.id.drop_citynames);

        getCityName();


//        arrayList_city = new ArrayList<>();
//        arrayList_city.add("Ujjain");
//        arrayList_city.add("Ratlam");
//        arrayList_city.add("Dewas");
//        arrayList_city.add("Indore");
//        arrayList_city.add("Bhopal");
//        arrayList_city.add("Mahidpur");
//        arrayList_city.add("Agar");
//        arrayList_city.add("other");

        cityArrayAdapter = new CityAdapter(this, (ArrayList<RowItem>) citynamelist);
        autoCompleteTextView.setAdapter(cityArrayAdapter);
        autoCompleteTextView.setThreshold(1);

//        arrayAdapter_city = new ArrayAdapter<>(getApplicationContext(),R.layout.item_text_dd,arrayList_city);
//        autoCompleteTextView.setAdapter(arrayAdapter_city);
//        autoCompleteTextView.setThreshold(1);
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//               initData();
//               initRecyclerview();
//            }
//        });

        //Recycler View ----



//        get device id and fcm token

        String deviceId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        Log.i("TAG", "onCreate: sdasd"+deviceId);


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()){
                    deviceToken = task.getResult();
                    Log.i("TAG", "onComplete: abdgs"+deviceToken);
                }
            }
        });




//        getCityName();



    }



    private void initData() {

        masjidlist = new ArrayList<city_name_ModelClass>();

        city_name_ModelClass masjid1 = new city_name_ModelClass();

        masjid1.setMasjidname("Jama Masjid");
        masjid1.setJumadetails("Juma 01:00 PM");
        masjid1.setStarlogo(R.drawable.empty_star);
        masjid1.setFajr("Fajr");
        masjid1.setFajrtime("05:00 AM");
        masjid1.setZuhar("Zuhar");
        masjid1.setZuhartime("01:00 PM");
        masjid1.setAsar("Asar");
        masjid1.setAsartime("05:00 PM");
        masjid1.setMaghrib("Maghrib");
        masjid1.setMaghribtime("06:30 PM");
        masjid1.setIsha("Isha");
        masjid1.setIshatime("08:00 PM");
        masjidlist.add(masjid1);

        city_name_ModelClass masjid2 = new city_name_ModelClass();

        masjid2.setMasjidname("Fateh Masjid");
        masjid2.setJumadetails("Juma 01:00 PM");
        masjid2.setStarlogo(R.drawable.empty_star);
        masjid2.setFajr("Fajr");
        masjid2.setFajrtime("05:00 AM");
        masjid2.setZuhar("Zuhar");
        masjid2.setZuhartime("01:00 PM");
        masjid2.setAsar("Asar");
        masjid2.setAsartime("05:00 PM");
        masjid2.setMaghrib("Maghrib");
        masjid2.setMaghribtime("06:30 PM");
        masjid2.setIsha("Isha");
        masjid2.setIshatime("08:00 PM");
        masjidlist.add(masjid2);

        city_name_ModelClass masjid3 = new city_name_ModelClass();

        masjid3.setMasjidname("Masjid Abubakr");
        masjid3.setJumadetails("Juma 01:00 PM");
        masjid3.setStarlogo(R.drawable.empty_star);
        masjid3.setFajr("Fajr");
        masjid3.setFajrtime("05:00 AM");
        masjid3.setZuhar("Zuhar");
        masjid3.setZuhartime("01:00 PM");
        masjid3.setAsar("Asar");
        masjid3.setAsartime("05:00 PM");
        masjid3.setMaghrib("Maghrib");
        masjid3.setMaghribtime("06:30 PM");
        masjid3.setIsha("Isha");
        masjid3.setIshatime("08:00 PM");
        masjidlist.add(masjid3);

        city_name_ModelClass masjid4 = new city_name_ModelClass();

        masjid4.setMasjidname("Masjid Noor-e-Ismile");
        masjid4.setJumadetails("Juma 01:00 PM");
        masjid4.setStarlogo(R.drawable.empty_star);
        masjid4.setFajr("Fajr");
        masjid4.setFajrtime("05:00 AM");
        masjid4.setZuhar("Zuhar");
        masjid4.setZuhartime("01:00 PM");
        masjid4.setAsar("Asar");
        masjid4.setAsartime("05:00 PM");
        masjid4.setMaghrib("Maghrib");
        masjid4.setMaghribtime("06:30 PM");
        masjid4.setIsha("Isha");
        masjid4.setIshatime("08:00 PM");
        masjidlist.add(masjid4);


        city_name_ModelClass masjid5 = new city_name_ModelClass();

        masjid5.setMasjidname("Madina Masjid");
        masjid5.setJumadetails("Juma 01:00 PM");
        masjid5.setStarlogo(R.drawable.empty_star);
        masjid5.setFajr("Fajr");
        masjid5.setFajrtime("05:00 AM");
        masjid5.setZuhar("Zuhar");
        masjid5.setZuhartime("01:00 PM");
        masjid5.setAsar("Asar");
        masjid5.setAsartime("05:00 PM");
        masjid5.setMaghrib("Maghrib");
        masjid5.setMaghribtime("06:30 PM");
        masjid5.setIsha("Isha");
        masjid5.setIshatime("08:00 PM");
        masjidlist.add(masjid5);




    }



    private void initRecyclerview() {

        recyclerView = findViewById(R.id.masjiddetails_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new city_name_Adapter(masjidlist,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

//    About App Dialog

    private void showAboutAppDialog() {
        Dialog customDialog = new Dialog(this);
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

//    share App
    private void shareApp() {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "Download -https://play.google.com/store/apps/details?id=com.whatsapp&hl=en";
        String shareSub = "Whatsapp apk";

        shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);

        startActivity(Intent.createChooser(shareIntent,"Share Via"));

    }


    private void getCityName() {

        CityApiInterface apiInterface = CityApi.getRetrofitInstance().create(CityApiInterface.class);

        Call<List<RowItem>> call = apiInterface.getAllCity();

        call.enqueue(new Callback<List<RowItem>>() {
            @Override
            public void onResponse(Call<List<RowItem>> call, Response<List<RowItem>> response) {


                try {
                    String res = new Gson().toJson(response.body());
                    JSONArray mainArray = new JSONArray(res);
                    citynamelist = new ArrayList<>();

                    for (int i=0; i<mainArray.length();i++)
                    {
                        JSONObject finalObject = mainArray.getJSONObject(i);
                        System.out.println("RESPONCE "+finalObject);
                        String IDD = finalObject.getString("id");
                        int toint = Integer.parseInt(IDD);
                        String str = finalObject.getString("name");

                        citynamelist.add(new RowItem(toint,str));
                        Log.i("TAG", "onResponse: sasasa"+citynamelist);
                    }
                    cityArrayAdapter.submitList(citynamelist);
                    cityArrayAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<RowItem>> call, Throwable t) {

            }
        });

    }

//    private void initCityName() {

//        citynamelist = new ArrayList<>();
//        citynamelist.add(new RowItem(1,"ujjain"));
//        citynamelist.add(new RowItem(2,"Indore"));
//        citynamelist.add(new RowItem(3,"Agar"));
//        citynamelist.add(new RowItem(4,"Bhopal"));
//    }





}