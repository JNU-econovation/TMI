package com.example.honeybee.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.honeybee.R;
import com.example.honeybee.model.UserData;
import com.example.honeybee.view.NetRetrofit;
import com.example.honeybee.view.fragment.ChatFragment;
import com.example.honeybee.view.fragment.FeedFragment;
import com.example.honeybee.view.fragment.ProfileFragment;
import com.example.honeybee.view.fragment.WishFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity.class";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateBottomNavigationView();
        getDataWithfindById();

    }

    private void getDataWithfindById() {
        String id = "김현지";
        Call<UserData> data = NetRetrofit.getInstance().getRetrofitService().userDataFindById(id);
        data.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                UserData body = response.body();
                Log.d(TAG, body.getIntroduce());
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {

            }
        });
    }

    private void activateBottomNavigationView() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, FeedFragment.newInstance())
                .commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_home:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, FeedFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.tab_wish:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, WishFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.tab_chat:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, ChatFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.tab_profile:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, ProfileFragment.newInstance())
                                .commit();
                        return true;
                    default:
                        return true;
                }
            }
        });
    }
}