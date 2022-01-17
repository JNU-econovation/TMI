package com.example.honeybee.contract;

import com.example.honeybee.model.FeedContent;
import com.example.honeybee.model.dto.LikeUserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {
//    @GET("test-url/{id}")
//    Call<List<FeedContent>> getDatas(@Path("id") String id);

    @GET("users/{id}")
    Call<FeedContent> findById(@Path("id") String id);

    @GET("users")
    Call<List<FeedContent>> findAll();

    @POST("users")
    Call<FeedContent> postDatas(@Body FeedContent feedContent);

    @POST("users/{id}/like")
    Call<LikeUserDto> getLikeUser(@Path("id") String id);


}
