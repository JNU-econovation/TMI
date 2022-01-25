package com.example.honeybee.view;

import com.example.honeybee.contract.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

// 레트로핏 객체
public class NetRetrofit {

    private static String url = "http://10.0.2.2:8080/";
    //private static String url="http://localhost:8000/";
    public static NetRetrofit outInstance = new NetRetrofit();
    public static NetRetrofit getInstance() {
        return outInstance;
    }

    Gson gson = new GsonBuilder().setLenient().create();

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(getInstance().gson))
            .build();

    public static RetrofitService retrofitService = retrofit.create(RetrofitService.class);

    public RetrofitService getRetrofitService() {
        return retrofitService;
    }
}
