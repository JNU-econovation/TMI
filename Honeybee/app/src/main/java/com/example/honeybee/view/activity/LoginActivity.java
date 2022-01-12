package com.example.honeybee.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.honeybee.R;
import com.example.honeybee.view.fragment.AuthenticationFragment;
import com.example.honeybee.view.fragment.EmailFragment;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void registerClicked(View view){

        EditText id = findViewById(R.id.login_id);
        EditText pw = findViewById(R.id.login_pw);
        String userId = id.getText().toString();
        String userPw = pw.getText().toString();

        // System.out.println("아이디"+userId);
        // System.out.println("비밀번호"+userPw);

        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
    }


}