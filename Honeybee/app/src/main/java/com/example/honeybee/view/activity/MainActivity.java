package com.example.honeybee.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.honeybee.R;
import com.example.honeybee.model.FeedContent;
import com.example.honeybee.view.NetRetrofit;
import com.example.honeybee.view.fragment.ChatFragment;
import com.example.honeybee.view.fragment.FeedFragment;
import com.example.honeybee.view.fragment.ProfileFragment;
import com.example.honeybee.view.fragment.WishFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import okhttp3.ResponseBody;
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


//        testGetMappingRetrofit();
//        testPostMappingRetrofit();
    }

    private void getDataWithfindById() {

        // 테스트로 사용할 id = 61e02da1283b566ada5fc98e
        String id = "61e3f0c2aa45187a63ab63bf";
        Call<FeedContent> data = NetRetrofit.getInstance().getRetrofitService().findById(id);
        data.enqueue(new Callback<FeedContent>() {
            @Override
            public void onResponse(Call<FeedContent> call, Response<FeedContent> response) {
                FeedContent body = response.body();
                Log.d(TAG, body.getIntroduce());
            }

            @Override
            public void onFailure(Call<FeedContent> call, Throwable t) {

            }
        });
    }

    private void testPostMappingRetrofit() {
        String[] personalities = {"활발함", "성실함", "정직함"};

        FeedContent feedContent = FeedContent.builder()
                .profile("testProfile")
                .name("ChaeSangYeop")
                .age(25)
                .score(80)
                .personalities(personalities)
                .introduce("this is test introduce message")
                .build();

        Call<FeedContent> postsData = NetRetrofit.getInstance().getRetrofitService().postDatas(feedContent);
        postsData.enqueue(new Callback<FeedContent>() {
            @Override
            public void onResponse(Call<FeedContent> call, Response<FeedContent> response) {
                String[] personalities = response.body().getPersonalities();
                Log.d(TAG, personalities[0]);
                Log.d(TAG, personalities[1]);
                Log.d(TAG, personalities[2]);
                Log.d(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<FeedContent> call, Throwable t) {
                Log.d(TAG, t.getMessage());
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