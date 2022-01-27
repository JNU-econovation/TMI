package com.example.honeybee.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.honeybee.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("password", Context.MODE_PRIVATE);
        Log.d("Test Activity 토큰 불러오기", sharedPreferences.getString("token",""));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}