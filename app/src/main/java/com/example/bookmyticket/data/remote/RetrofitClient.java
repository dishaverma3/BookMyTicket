package com.example.bookmyticket.data.remote;

import com.example.bookmyticket.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit getInstance()
    {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static APIService getAPIService(){
        return getInstance().create(APIService.class);
    }
}
