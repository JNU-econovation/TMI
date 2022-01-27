package com.example.honeybee.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.honeybee.R;
import com.example.honeybee.model.UserData;
import com.example.honeybee.model.dto.FeedContentDto;
import com.example.honeybee.view.NetRetrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFeedContentActivity extends AppCompatActivity {
    private final String TAG = "DetailFeedContentActivity.class";

    private ImageView iv_profile;
    private TextView tv_name;
    private TextView tv_age;
    private ImageView iv_likeButton;
    private ImageView iv_chat;
    private TextView tv_college;
    private TextView tv_location;
    private TextView tv_mbti;
    private TextView tv_personality1;
    private TextView tv_personality2;
    private TextView tv_personality3;
    private TextView tv_personality4;
    private TextView tv_personality5;
    private TextView tv_personality6;
    private TextView tv_personality7;
    private TextView tv_introduce;
    private TextView tv_smoking;
    private TextView tv_drinking;

    private TextView tv_height;

    private ArrayList<String> user_image;
    private String nickname;
    private int age;
    private String department;
    private Integer location;
    private String mbti;
    private ArrayList<String> personalities;
    private String introduce;
    private String smoke;
    private String drink;
    private Integer height;

    private String feedUid;
    private String u_id="120";


    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume()");
        Call<UserData> getBeforeData = NetRetrofit.getInstance().getRetrofitService().userDataFindById(u_id);
        getBeforeData.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {

                UserData userData = response.body();
                if (userData != null) {
                    ArrayList<String> pick_person = userData.getPick_person();
                    if (!pick_person.contains(feedUid)) {
                        iv_likeButton.setImageResource(R.drawable.ic_wish_unclicked_detail);
                    } else {
                        iv_likeButton.setImageResource(R.drawable.ic_wish_clicked_detail);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {
                Log.d(TAG, "onResume() 에러메시지 : " + t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed_content);
        init();
        getFeedData();
        addUserIntoWishList();
    }

    private void addUserIntoWishList() {
        iv_likeButton = findViewById(R.id.iv_likeButton);
        iv_likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "iv_likeButton click event");

                Call<UserData> getBeforeData = NetRetrofit.getInstance().getRetrofitService().userDataFindById(u_id);
                getBeforeData.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                        UserData userData = response.body();
                        if(userData != null) {
                            ArrayList<String> pick_person = userData.getPick_person();

                            if (!pick_person.contains(feedUid)) {
                                pick_person.add(feedUid);
                                iv_likeButton.setImageResource(R.drawable.ic_wish_clicked_detail);
                            } else {
                                pick_person.remove(feedUid);
                                iv_likeButton.setImageResource(R.drawable.ic_wish_unclicked_detail);
                            }

                            Call<UserData> patchAfterData = NetRetrofit.getInstance().getRetrofitService().userDataUpdate(u_id, pick_person);
                            patchAfterData.enqueue(new Callback<UserData>() {
                                @Override
                                public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                                    Log.d(TAG, "pick_person" + response.body().getPick_person());
                                }

                                @Override
                                public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {
                                    Log.d(TAG, "patchAfterData error" + t.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {
                        Log.d(TAG, "getBeforeData error" + t.getMessage());
                    }
                });
            }
        });
    }

    public void init() {
        iv_profile = findViewById(R.id.iv_profile);
        tv_name = findViewById(R.id.tv_name);
        tv_age = findViewById(R.id.tv_age);

        iv_likeButton = findViewById(R.id.iv_likeButton);
        iv_chat = findViewById(R.id.iv_chat);
        tv_college = findViewById(R.id.tv_college);
        tv_location = findViewById(R.id.tv_location);
        tv_mbti = findViewById(R.id.tv_mbti);

        tv_personality1 = findViewById(R.id.tv_personality1);
        tv_personality2 = findViewById(R.id.tv_personality2);
        tv_personality3 = findViewById(R.id.tv_personality3);
        tv_personality4 = findViewById(R.id.tv_personality4);
        tv_personality5 = findViewById(R.id.tv_personality5);
        tv_personality6 = findViewById(R.id.tv_personality6);
        tv_personality7 = findViewById(R.id.tv_personality7);

        tv_introduce = findViewById(R.id.tv_introduce);
        tv_smoking = findViewById(R.id.tv_smoking);
        tv_height = findViewById(R.id.tv_height);
        tv_drinking = findViewById(R.id.tv_drinking);

        Intent intent = getIntent();
        FeedContentDto feedContentDto = (FeedContentDto) intent.getSerializableExtra("feedContentDto");

        user_image = feedContentDto.getUser_image();
        nickname = feedContentDto.getNickname();
        age = feedContentDto.getAge();
        department = feedContentDto.getDepartment();
        location = feedContentDto.getLocation();
        mbti = feedContentDto.getMbti();
        personalities = feedContentDto.getPersonality();
        introduce = feedContentDto.getIntroduce();
        smoke = feedContentDto.getSmoke();
        drink = feedContentDto.getDrink();
        height = feedContentDto.getHeight();

        feedUid = intent.getStringExtra("feedUid");

    }

    public void getFeedData() {
        /**
         * 이미지는 이 자리에 Glide로 처리해주면 됨
         */
        Glide.with(getBaseContext()).load(user_image.get(0)).into(iv_profile);
//        iv_profile.setImageResource(R.drawable.img_maenji);
        tv_name.setText(nickname);
        tv_age.setText(String.valueOf(age));
        tv_college.setText(department);
        tv_location.setText("5km");
        tv_mbti.setText(mbti);
        tv_personality1.setText(personalities.get(0));
        tv_personality2.setText(personalities.get(1));
        tv_personality3.setText(personalities.get(2));
        tv_personality4.setText(personalities.get(3));
        tv_personality5.setText(personalities.get(4));
        tv_personality6.setText(personalities.get(5));
        tv_personality7.setText(personalities.get(6));
        tv_introduce.setText(introduce);
        tv_smoking.setText(smoke);
        tv_drinking.setText(drink);
        tv_height.setText(height + "cm");

    }
}