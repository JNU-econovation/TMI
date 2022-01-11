package com.example.honeybee.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.honeybee.R;
import com.example.honeybee.contract.FeedContract;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity.class";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateBottomNavigationView();
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