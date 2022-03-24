package com.applligent.namaztime.nearByMasjid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applligent.namaztime.R;

import java.util.List;

public class MasjidListAdapter extends RecyclerView.Adapter<MasjidListAdapter.ViewHolder> {

    private List<MasjidListModel> masjidList;
    private Context context;

    public MasjidListAdapter(List<MasjidListModel> masjidList, Context context) {
        this.masjidList = masjidList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.masjid_details_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String masjid_name = masjidList.get(position).getName();
        String fajt_time = masjidList.get(position).getFajar();
        String zuhar_time = masjidList.get(position).getZuhar();
        String asar_time = masjidList.get(position).getAsar();
        String maghrib_time = masjidList.get(position).getMagrib();
        String isha_time = masjidList.get(position).getIsha();
        String juma_time = masjidList.get(position).getJumaTime();

        holder.masjidname_TV.setText(masjid_name);
        holder.fajr_time_TV.setText(fajt_time);
        holder.zuhar_time_TV.setText(zuhar_time);
        holder.asar_time_TV.setText(asar_time);
        holder.maghrib_time_TV.setText(maghrib_time);
        holder.isha_time_TV.setText(isha_time);
        holder.juma_time_TV.setText(juma_time);




//
//        holder.masjidname_TV.setText(masjidList.get(position).getName());
//        holder.fajr_time_TV.setText(masjidList.get(position).getFajar());
//        holder.zuhar_time_TV.setText(masjidList.get(position).getZuhar());
//        holder.asar_time_TV.setText(masjidList.get(position).getAsar());
//        holder.maghrib_time_TV.setText(masjidList.get(position).getMagrib());
//        holder.isha_time_TV.setText(masjidList.get(position).getIsha());
//        holder.juma_time_TV.setText(masjidList.get(position).getJumaTime());

    }

    @Override
    public int getItemCount() {
        return masjidList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView masjidname_TV;
        TextView fajr_time_TV;
        TextView zuhar_time_TV;
        TextView asar_time_TV;
        TextView maghrib_time_TV;
        TextView isha_time_TV;
        TextView juma_time_TV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            masjidname_TV = itemView.findViewById(R.id.masjid_name);
            fajr_time_TV = itemView.findViewById(R.id.fajr_time);
            zuhar_time_TV = itemView.findViewById(R.id.zuhar_time);
            asar_time_TV = itemView.findViewById(R.id.asar_time);
            maghrib_time_TV = itemView.findViewById(R.id.maghrib_time);
            isha_time_TV = itemView.findViewById(R.id.isha_time);
            juma_time_TV = itemView.findViewById(R.id.juma_time);


        }
    }
}
