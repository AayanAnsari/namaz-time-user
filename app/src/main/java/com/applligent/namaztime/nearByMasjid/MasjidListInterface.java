package com.applligent.namaztime.nearByMasjid;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.POST;

public interface MasjidListInterface {

    @POST("Masjidlist/getAllMasjid")
    Call<Object> getAllMasjidsDetails(@Body HashMap<String, Object> request);

    @POST("User/userFavoriteMasjid")
    Call<Object> addFavouriteMasjid(@Body HashMap<String, Object> reqFavourites);
}
