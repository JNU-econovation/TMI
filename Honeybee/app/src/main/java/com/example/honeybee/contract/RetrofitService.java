package com.example.honeybee.contract;

import com.example.honeybee.model.TmiData;
import com.example.honeybee.model.dto.FeedContentDto;
import com.example.honeybee.model.dto.LikeUserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {
//    @GET("test-url/{id}")
//    Call<List<FeedContent>> getDatas(@Path("id") String id);

    @GET("users/{id}")
    Call<TmiData> findById(@Path("id") String id);

    @GET("test")
    Call<List<TmiData>> findAll();
}
