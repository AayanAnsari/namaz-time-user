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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.applligent.namaztime.cityNameApi.CityAdapter;
import com.applligent.namaztime.cityNameApi.CityApi;
import com.applligent.namaztime.cityNameApi.CityApiInterface;
import com.applligent.namaztime.cityNameApi.RowItem;
import com.applligent.namaztime.nearByMasjid.FavInterface;
import com.applligent.namaztime.nearByMasjid.MasjidLIstRetrofit;
import com.applligent.namaztime.nearByMasjid.MasjidListAdapter;
import com.applligent.namaztime.nearByMasjid.MasjidListInterface;
import com.applligent.namaztime.nearByMasjid.MasjidListModel;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class city_name extends AppCompatActivity implements AdapterView.OnItemSelectedListener, FavInterface {


    TextView nowNamaz;
    TextView upcomingNamaz;

    // Drawer layout sliding menu bar

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView TB_title;


  //    dropdown-->
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView ;

//    ArrayList<String> arrayList_city;
//    ArrayAdapter<String> arrayAdapter_city;

    List<RowItem> citynamelist;
    CityAdapter cityArrayAdapter;
    Spinner spinner;

    // Recycler View for Masjid Details ---->
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<MasjidListModel> masjidlist;
    MasjidListAdapter adapter;
    ProgressBar progressBar;


    String cityID;
    String deviceId;
    String deviceToken;

    TextView islamicDate;
    TextView islamicFullDate;
    TextView monthAndYear,currentDateTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_name);

        // get current and upcoming namaz time
        nowNamaz = findViewById(R.id.now_namaz_name2);
        upcomingNamaz = findViewById(R.id.upcoming_namaz_name2);
        String forNowNamaz = getIntent().getExtras().getString("nowNamazTime");
        String forUpcomingNamaz = getIntent().getExtras().getString("upcomingNamazTime");
        nowNamaz.setText(forNowNamaz);
        upcomingNamaz.setText(forUpcomingNamaz);

        //get English Date
        currentDateTV = findViewById(R.id.date);
        monthAndYear = findViewById(R.id.full_date);
        getEnglishDate();

        //get IslamicDate
        islamicDate = findViewById(R.id.islamic_date1);
        islamicFullDate = findViewById(R.id.islamic_fulldate1);
        getIslamicDate();

        progressBar = findViewById(R.id.progressBar);

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
//        textInputLayout = findViewById(R.id.drop_down_menu);
//        autoCompleteTextView = findViewById(R.id.drop_citynames);

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


//        arrayAdapter_city = new ArrayAdapter<>(getApplicationContext(),R.layout.item_text_dd,arrayList_city);
//        autoCompleteTextView.setAdapter(arrayAdapter_city);
//        autoCompleteTextView.setThreshold(1);
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                autoCompleteTextView.setText(citynamelist.get(i).getName());
//               initData();
//               initRecyclerview();
//            }
//        });





//        get device id and fcm token

        deviceId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
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



    }

    //get English Date
    private void getEnglishDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat forDate = new SimpleDateFormat("dd");
        String DD = forDate.format(calendar.getTime());
        currentDateTV.setText(DD);
        SimpleDateFormat forMonthYear = new SimpleDateFormat("MMMM yyyy");
        String MY = forMonthYear.format(calendar.getTime());
        monthAndYear.setText(MY);
    }

    //get Islamic Date
    private void getIslamicDate() {
        UmmalquraCalendar Ucal = new UmmalquraCalendar();
        int islYear = Ucal.get(Calendar.YEAR);
        int islMonth = Ucal.get(Calendar.MONTH);
        int islDate = Ucal.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat myDateFormat = new SimpleDateFormat("", Locale.ENGLISH);
        myDateFormat.setCalendar(Ucal);

        myDateFormat.applyPattern("dd");
        String myIslamicDt = myDateFormat.format(Ucal.getTime());

        myDateFormat.applyPattern("MMMM yyyy");
        String myMonthYear = myDateFormat.format(Ucal.getTime());
        islamicDate.setText(myIslamicDt);
        islamicFullDate.setText(myMonthYear);
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
                    citynamelist.add(new RowItem("0","Select City"));

                    for (int i=0; i<mainArray.length();i++)
                    {
                        JSONObject finalObject = mainArray.getJSONObject(i);
//                        System.out.println("RESPONCE "+finalObject);
                        String IDD = finalObject.getString("id");
                        String str = finalObject.getString("name");

                        citynamelist.add(new RowItem(IDD,str));
                        Log.i("TAG", "onResponse: sasasera"+citynamelist);
                    }


                    spinner = (Spinner)findViewById(R.id.CitySpinner) ;
                    cityArrayAdapter = new CityAdapter(city_name.this,citynamelist);
                    cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(cityArrayAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            cityID = adapterView.getItemAtPosition(i).toString();
                            if (i!=0)
                            {
//                                Toast.makeText(city_name.this, "Selected = "+cityID, Toast.LENGTH_SHORT).show();
                                getMasjidlist();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });



//                    cityArrayAdapter = new CityAdapter(city_name.this,R.layout.item_text_dd,citynamelist);
//                    autoCompleteTextView.setAdapter(cityArrayAdapter);
//                    autoCompleteTextView.setThreshold(1);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<RowItem>> call, Throwable t) {



            }
        });

    }

    private void getMasjidlist() {

        progressBar.setVisibility(View.VISIBLE);

        HashMap<String,Object> request = new HashMap<>();
        request.put("masjidCityId",cityID);
        request.put("deviceId",deviceId);
        request.put("deviceToken",deviceToken);

        MasjidListInterface masjidListInterface = MasjidLIstRetrofit.getMasjidListRetrofit().create(MasjidListInterface.class);

        Call<Object> masjidListcall = masjidListInterface.getAllMasjidsDetails(request);
        masjidListcall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful() && response.body()!=null)
                {
                    try {
                        String obj = new Gson().toJson(response.body());
                        System.out.println("RESPONCE"+obj);
                        JSONObject mainObject = new JSONObject(obj);
                        JSONArray dataArray = mainObject.getJSONArray("data");
                        masjidlist = new ArrayList<MasjidListModel>();
                        for(int i=0; i<dataArray.length(); i++)
                        {
                            JSONObject jsonObj = dataArray.getJSONObject(i);
                            MasjidListModel masjid = new MasjidListModel();
                            String masjidName = jsonObj.getString("name");
                            masjid.setName(masjidName);

                            String masjidID = jsonObj.getString("masjidId");
                            masjid.setMasjidID(masjidID);

                            String fajarTime = jsonObj.getString("fajar");
                            masjid.setFajar(fajarTime);

                            String zuharTime = jsonObj.getString("zuhar");
                            masjid.setZuhar(zuharTime);

                            String asarTime = jsonObj.getString("asar");
                            masjid.setAsar(asarTime);

                            String maghribTime = jsonObj.getString("magrib");
                            masjid.setMagrib(maghribTime);

                            String ishaTime = jsonObj.getString("isha");
                            masjid.setIsha(ishaTime);

                            String juma_Time = jsonObj.getString("jumaTime");
                            masjid.setJumaTime(juma_Time);

                            Double LAT = jsonObj.getDouble("lat");
                            masjid.setLat(LAT);

                            Double LNG = jsonObj.getDouble("lng");
                            masjid.setLng(LNG);

                            boolean IsFavourite = jsonObj.getBoolean("isFavourite");
                            masjid.setIsFavourite(IsFavourite);

                            masjidlist.add(masjid);

                        }

                        initRecyclerview();
                        progressBar.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void initRecyclerview() {

        recyclerView = findViewById(R.id.masjiddetails_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MasjidListAdapter(masjidlist,this,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClickStar(MasjidListModel masjidListModel) {
//        Toast.makeText(this, "clicked on"+masjidListModel.getName(), Toast.LENGTH_SHORT).show();
//        String masjidID = masjidListModel.getMasjidID();

        HashMap<String,Object> reqFavourites = new HashMap<>();
        reqFavourites.put("deviceId",deviceId);
        reqFavourites.put("masjidId",masjidListModel.getMasjidID());
        
        MasjidListInterface masjidListInterface = MasjidLIstRetrofit.getMasjidListRetrofit().create(MasjidListInterface.class);
        Call<Object> favouritesCall = masjidListInterface.addFavouriteMasjid(reqFavourites);
        favouritesCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                try {
                    String newObj = new Gson().toJson(response.body());
                    JSONObject mainObject = new JSONObject(newObj);
//                    System.out.println("RESPONCE "+mainObject);
                    String msg = mainObject.getString("message");
                    Toast.makeText(city_name.this, msg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }

}