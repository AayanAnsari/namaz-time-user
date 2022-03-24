package com.applligent.namaztime.nearByMasjid;

import com.applligent.namaztime.Api.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MasjidLIstRetrofit {

    private static String BASE_URL = "https://applligent.com/projects/namaz_time/api/Masjidlist/";
    private static Retrofit retrofit =null;

    public static Retrofit getMasjidListRetrofit(){
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }
        return retrofit;
    }

}
