package com.example.honeybee.contract;

import com.example.honeybee.model.FeedContent;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("test-url/{id}")
    Call<FeedContent> getDatas(@Path("id") Long id);

    @POST("test-url")
    Call<FeedContent> postDatas(@Body FeedContent feedContent);
}
