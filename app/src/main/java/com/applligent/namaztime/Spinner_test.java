package com.applligent.namaztime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Spinner_test extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    ArrayList<String> arrayList_city;
    ArrayAdapter<String> arrayAdapter_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_test);

        textInputLayout = findViewById(R.id.drop_down);
        autoCompleteTextView = findViewById(R.id.drop_items);

        arrayList_city = new ArrayList<>();
        arrayList_city.add("Ujjain");
        arrayList_city.add("Indore");
        arrayList_city.add("Agar");
        arrayList_city.add("Mahidpur");
        arrayList_city.add("Bhopal");
        arrayList_city.add("Ratlam");
        arrayList_city.add("Dewas");

        arrayAdapter_city = new ArrayAdapter<>(getApplicationContext(), R.layout.item_text_dd,arrayList_city);
        autoCompleteTextView.setAdapter(arrayAdapter_city);

        autoCompleteTextView.setThreshold(1);


    }
}