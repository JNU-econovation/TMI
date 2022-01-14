package com.example.honeybee.view.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainPageAdapter extends FragmentStateAdapter {

    private static final String TAG = "MainPageAdapter.class";
    private final List<Fragment> list = new ArrayList<>();


    public MainPageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public MainPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public MainPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d(TAG, "position : " + position);
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Fragment fragment) {
        Log.d(TAG, "addItem() 호출" + fragment.toString());
        list.add(fragment);
    }

}
