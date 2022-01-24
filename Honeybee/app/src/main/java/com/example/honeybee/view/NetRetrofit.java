package com.example.honeybee.view;

import com.example.honeybee.contract.RetrofitService;

import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetRetrofit {

    private static String url = "http://10.0.2.2:8080/";
    public static NetRetrofit outInstance = new NetRetrofit();
    public static NetRetrofit getInstance() {
        return outInstance;
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static RetrofitService retrofitService = retrofit.create(RetrofitService.class);

    public RetrofitService getRetrofitService() {
        return retrofitService;
    }
}
