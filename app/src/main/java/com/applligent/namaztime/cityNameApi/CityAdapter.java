package com.applligent.namaztime.cityNameApi;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applligent.namaztime.R;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends ArrayAdapter<RowItem> {

    private Context context;
    private List<RowItem> citynamelist = new ArrayList<>();


    public CityAdapter(@NonNull Context context, ArrayList<RowItem> list) {
        super(context,0,list);
        context = context;
        //citynamelist = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View listitem = convertView;
        if (listitem == null)
            listitem = LayoutInflater.from(context).inflate(R.layout.item_text_dd,parent,false);


        RowItem currentcity = citynamelist.get(position);

        TextView City_ID_TV = listitem.findViewById(R.id.city_id);
        City_ID_TV.setText(currentcity.getId());

        TextView City_Name = listitem.findViewById(R.id.city_name);
        City_Name.setText(currentcity.getName());

        return listitem;

    }


    public void submitList(List<RowItem> list) {
        citynamelist = list;
    }
}
