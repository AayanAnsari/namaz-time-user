package com.applligent.namaztime.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("calendarByCity?")
    Call<Object> getDefaultTime(@Query("city") String city,@Query("country") String country);
}
