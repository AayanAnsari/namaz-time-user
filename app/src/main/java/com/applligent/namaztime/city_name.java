package com.applligent.namaztime;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class city_name extends AppCompatActivity {

    // Drawer layout sliding menu bar

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView TB_title;

  //    dropdown-->
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    ArrayList<String> arrayList_city;
    ArrayAdapter<String> arrayAdapter_city;

    // Recycler View for Masjid Details ---->
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<city_name_ModelClass> masjidlist;
    city_name_Adapter adapter;




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

        //dropDown
        textInputLayout = findViewById(R.id.drop_down_menu);
        autoCompleteTextView = findViewById(R.id.drop_citynames);

        arrayList_city = new ArrayList<>();
        arrayList_city.add("Ujjain");
        arrayList_city.add("Ratlam");
        arrayList_city.add("Dewas");
        arrayList_city.add("Indore");
        arrayList_city.add("Bhopal");
        arrayList_city.add("Mahidpur");
        arrayList_city.add("Agar");
        arrayList_city.add("other");

        arrayAdapter_city = new ArrayAdapter<>(getApplicationContext(),R.layout.item_text_dd,arrayList_city);
        autoCompleteTextView.setAdapter(arrayAdapter_city);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               initData();
               initRecyclerview();
            }
        });

        //Recycler View ----







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
        masjid1.setAsartime("05:00 pm");
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
        masjid2.setAsartime("05:00 pm");
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
        masjid3.setAsartime("05:00 pm");
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
        masjid4.setAsartime("05:00 pm");
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
        masjid5.setAsartime("05:00 pm");
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



}