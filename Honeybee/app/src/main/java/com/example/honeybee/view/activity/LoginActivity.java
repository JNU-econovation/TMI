package com.example.honeybee.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.honeybee.R;
import com.example.honeybee.contract.RetrofitService;
import com.example.honeybee.model.dto.LoginData;
import com.example.honeybee.view.NetRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText id;
    private EditText pw;
    private Button loginButton;
    private TextView registerButton;
    private String uid, password;   // 서버와 동일해야하는 변수이

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("password", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        id = (EditText) findViewById(R.id.login_id);
        pw = (EditText) findViewById(R.id.login_pw);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (TextView) findViewById(R.id.registerButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid = id.getText().toString();
                password = pw.getText().toString();

                LoginData loginData = new LoginData(uid, password);

                RetrofitService retrofitService = NetRetrofit.getInstance().getRetrofitService();

                Call<String> call = retrofitService.userLogin(loginData);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("call 테스트", call.toString());
                        Log.e("요청 테스트", response.body().toString());

                        if (!response.isSuccessful()){
                            Log.e("원인",response.toString());
                            Log.e("연결이 비정상적: ", "error code: "+response.code());
                            return;
                        }
                        String token = response.body();
                        Log.d("연결이 성공적: ", response.body().toString());
                        editor.putString("token", token);
                        editor.commit();
                        Log.d("토큰 불러오기", sharedPreferences.getString("token",""));
                        startActivity(new Intent(LoginActivity.this, TestActivity.class));
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("연결 실패", t.getMessage());
                    }
                });
            }
        });

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