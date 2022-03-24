package com.applligent.namaztime.cityNameApi;



import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import com.applligent.namaztime.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class CityAdapter extends ArrayAdapter<RowItem> {

    public CityAdapter(Context context, List<RowItem> objects) {
        super(context, android.R.layout.simple_list_item_2, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return initView(position, convertView);
    }

    private View initView(int position, View convertView) {

        if (convertView == null)
            convertView = View.inflate(getContext(), R.layout.item_text_dd, null);

        TextView tvText1 = (TextView) convertView.findViewById(R.id.city_id);
        TextView tvText2 = (TextView) convertView.findViewById(R.id.city_name);

        tvText1.setText(getItem(position).getId());
        tvText1.setVisibility(View.INVISIBLE);

        tvText2.setText(getItem(position).getName());

        return convertView;


    }
}




