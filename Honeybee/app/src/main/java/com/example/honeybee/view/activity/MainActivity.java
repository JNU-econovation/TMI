package com.example.honeybee.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        testRetrofit();
    }

    private void testRetrofit() {

//        ArrayList<String> lists = new ArrayList<>();
//        lists.add("활발함");
//        lists.add("소심함");
//        lists.add("과묵함");

//        FeedContent feedContent = FeedContent.builder()
//                .profile("profile")
//                .name("name")
//                .age(20)
//                .score(75)
//                .personalities(lists)
//                .introduce("introduce")
//                .build();

//        Gson gson = new Gson();
//        String objJson = gson.toJson(feedContent);


        Call<FeedContent> getData = NetRetrofit.getInstance().getRetrofitService().getDatas("id");
        getData.enqueue(new Callback<FeedContent>() {
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