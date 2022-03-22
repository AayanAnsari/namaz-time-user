package com.applligent.namaztime.cityNameApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CityApiInterface {

    @GET("getAllCities")
    Call<List<RowItem>> getAllCity();

}
