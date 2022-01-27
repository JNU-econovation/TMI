package com.example.honeybee.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.honeybee.R;
import com.example.honeybee.view.fragment.ChatFragment;
import com.example.honeybee.view.fragment.FeedFragment;
import com.example.honeybee.view.fragment.ProfileFragment;
import com.example.honeybee.view.fragment.WishFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity.class";
    private BottomNavigationView bottomNavigationView;

    private Fragment feedFragment, wishFragment, chatFragment, profileFragment;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedFragment = FeedFragment.newInstance();
        activateBottomNavigationView();
    }

    private void activateBottomNavigationView() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, feedFragment)
                .commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_home:
                        Log.d(TAG, "tab_home clicked");

                        if (feedFragment == null) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.fragment_container, feedFragment)
                                    .commit();
                        }
                        if (feedFragment != null)
                            getSupportFragmentManager().beginTransaction().show(feedFragment).commit();
                        if (wishFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(wishFragment).commit();
                        if (chatFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(chatFragment).commit();
                        if (profileFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(profileFragment).commit();

                        return true;
                    case R.id.tab_wish:

                        if (wishFragment == null) {
                            wishFragment = WishFragment.newInstance();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.fragment_container, wishFragment)
                                    .commit();
                        }
                        if (feedFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(feedFragment).commit();
                        if (wishFragment != null) {
                            getSupportFragmentManager().beginTransaction().show(wishFragment).commit();
                            Log.d(TAG, "wishFragment show");
                        }
                        if (chatFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(chatFragment).commit();
                        if (profileFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(profileFragment).commit();

                        return true;

                    case R.id.tab_chat:

                        if (chatFragment == null) {
                            chatFragment = ChatFragment.newInstance();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.fragment_container, chatFragment)
                                    .commit();
                        }
                        if (feedFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(feedFragment).commit();
                        if (wishFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(wishFragment).commit();
                        if (chatFragment != null)
                            getSupportFragmentManager().beginTransaction().show(chatFragment).commit();
                        if (profileFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(profileFragment).commit();

                        return true;

                    case R.id.tab_profile:

                        if (profileFragment == null) {
                            profileFragment = ProfileFragment.newInstance();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.fragment_container, profileFragment)
                                    .commit();
                        }
                        if (feedFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(feedFragment).commit();
                        if (wishFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(wishFragment).commit();
                        if (chatFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(chatFragment).commit();
                        if (profileFragment != null)
                            getSupportFragmentManager().beginTransaction().show(profileFragment).commit();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
}