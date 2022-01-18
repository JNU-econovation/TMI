package com.example.honeybee.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.honeybee.R;

public class DetailFeedContentActivity extends AppCompatActivity {
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


    private String[] user_image;
    private String nickname;
    private int age;
    private String personality1;
    private String personality2;
    private String personality3;
    private String personality4;
    private String personality5;
    private String personality6;
    private String personality7;
    private String introduce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed_content);
        init();
        getFeedData();
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

        user_image = intent.getStringArrayExtra("user_image");
        nickname = intent.getStringExtra("nickname");
        age = intent.getIntExtra("age",0);
        personality1 = intent.getStringExtra("personality1");
        personality2 = intent.getStringExtra("personality2");
        personality3 = intent.getStringExtra("personality3");
        personality4 = intent.getStringExtra("personality4");
        personality5 = intent.getStringExtra("personality5");
        personality6 = intent.getStringExtra("personality6");
        personality7 = intent.getStringExtra("personality7");
        introduce = intent.getStringExtra("introduce");
    }

    public void getFeedData() {
        /**
         * 이미지는 이 자리에 Glide로 처리해주면 됨
         */
//        Glide.with(getBaseContext()).load(profileUrl).into(iv_profile);
        iv_profile.setImageResource(R.drawable.img_maenji);

        tv_name.setText(nickname);
        tv_age.setText(String.valueOf(age));
        tv_college.setText("산업공학과");
        tv_location.setText("5km");
        tv_mbti.setText("ENFJ");
        tv_personality1.setText(personality1);
        tv_personality2.setText(personality2);
        tv_personality3.setText(personality3);
        tv_personality4.setText(personality4);
        tv_personality5.setText(personality5);
        tv_personality6.setText(personality6);
        tv_personality7.setText(personality7);
        tv_introduce.setText(introduce);
        tv_smoking.setText("절연");
        tv_drinking.setText("주 5-6번");
        tv_height.setText("162cm");
    }
}