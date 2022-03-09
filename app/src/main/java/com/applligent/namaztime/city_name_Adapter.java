package com.applligent.namaztime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class city_name_Adapter extends RecyclerView.Adapter<city_name_Adapter.ViewHolder> {

    private List<city_name_ModelClass> masjidlist;
    private static Context context;


    public city_name_Adapter(List<city_name_ModelClass>masjidlist,Context context){
        this.masjidlist = masjidlist;
        this.context = context;
    }

    @NonNull
    @Override
    public city_name_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.masjid_details_design,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull city_name_Adapter.ViewHolder holder, int position) {

        String masjid_name = masjidlist.get(position).getMasjidname();
        String juma_details = masjidlist.get(position).getJumadetails();
        int star_logo = masjidlist.get(position).getStarlogo();
        String A_fajr = masjidlist.get(position).getFajr();
        String fajr_time = masjidlist.get(position).getFajrtime();
        String A_zuhar = masjidlist.get(position).getZuhar();
        String zuhar_time = masjidlist.get(position).getZuhartime();
        String A_asar = masjidlist.get(position).getAsar();
        String asar_time = masjidlist.get(position).getAsartime();
        String A_maghrib = masjidlist.get(position).getMaghrib();
        String maghrib_time = masjidlist.get(position).getMaghribtime();
        String A_isha = masjidlist.get(position).getIsha();
        String isha_time = masjidlist.get(position).getIshatime();

        holder.masjidnameTV.setText(masjid_name);
        holder.jumaTV.setText(juma_details);
        holder.starlogoIV.setImageResource(star_logo);
        holder.fajrTV.setText(A_fajr);
        holder.fajr_timeTV.setText(fajr_time);
        holder.zuharTV.setText(A_zuhar);
        holder.zuhar_timeTV.setText(zuhar_time);
        holder.asarTV.setText(A_asar);
        holder.asar_timeTV.setText(asar_time);
        holder.maghribTV.setText(A_maghrib);
        holder.maghrib_timeTV.setText(maghrib_time);
        holder.ishaTV.setText(A_isha);
        holder.isha_timeTV.setText(isha_time);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "clicked on -" + masjid_name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,AlarmScreen.class);
                    intent.putExtra("Fajr",fajr_time);
                    intent.putExtra("Zuhar",zuhar_time);
                    intent.putExtra("Asar",asar_time);
                    intent.putExtra("Maghrib",maghrib_time);
                    intent.putExtra("Isha",isha_time);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return masjidlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView masjidnameTV;
        private TextView jumaTV;
        private ImageView starlogoIV;
        private TextView fajrTV;
        private TextView fajr_timeTV;
        private TextView zuharTV;
        private TextView zuhar_timeTV;
        private TextView asarTV;
        private TextView asar_timeTV;
        private TextView maghribTV;
        private TextView maghrib_timeTV;
        private TextView ishaTV;
        private TextView isha_timeTV;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            masjidnameTV = itemView.findViewById(R.id.masjid_name);
            jumaTV = itemView.findViewById(R.id.juma_time);
            starlogoIV = itemView.findViewById(R.id.star_logo);
            fajrTV = itemView.findViewById(R.id.fajr_namaz);
            fajr_timeTV = itemView.findViewById(R.id.fajr_time);
            zuharTV = itemView.findViewById(R.id.zuhar_namaz);
            zuhar_timeTV = itemView.findViewById(R.id.zuhar_time);
            asarTV = itemView.findViewById(R.id.asar_namaz);
            asar_timeTV = itemView.findViewById(R.id.asar_time);
            maghribTV = itemView.findViewById(R.id.maghrib_namaz);
            maghrib_timeTV = itemView.findViewById(R.id.maghrib_time);
            ishaTV = itemView.findViewById(R.id.isha_namaz);
            isha_timeTV = itemView.findViewById(R.id.isha_time);
            cardView = itemView.findViewById(R.id.masjid_cardview);
        }
    }
}
