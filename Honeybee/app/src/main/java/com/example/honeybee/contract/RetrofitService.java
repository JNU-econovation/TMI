package com.example.honeybee.contract;

import com.example.honeybee.model.AiRequestData;
import com.example.honeybee.model.AiResponseData;
import com.example.honeybee.model.UserData;
import com.example.honeybee.model.dto.FeedContentDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("users")
    Call<List<UserData>> userDatafindAll();

    @PATCH("users/{u_id}/datas")
    Call<UserData> userDataUpdate(@Path("u_id") String u_id,@Body ArrayList<String> pick_person);

    @GET("users/{u_id}/datas")
    Call<UserData> userDataFindById(@Path("u_id") String u_id);

    @POST("https://zqtx5ohilf.execute-api.ap-northeast-2.amazonaws.com/default/request-list")
    Call<AiResponseData> userScoreFindById(@Body AiRequestData aiRequestData);


}
