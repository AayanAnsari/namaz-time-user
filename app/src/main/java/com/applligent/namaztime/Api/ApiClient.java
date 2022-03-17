package com.applligent.namaztime.Api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    String url = "https://api.aladhan.com/v1/";
    private static ApiClient clientObject;
    private static Retrofit retrofit;
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    ApiClient(){
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient= new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
    public static synchronized ApiClient getInstance(){
        if (clientObject == null){
            clientObject = new ApiClient();
        }
        return clientObject;
    }

    public ApiInterface getApi()
    {
        return retrofit.create(ApiInterface.class);
    }

}
