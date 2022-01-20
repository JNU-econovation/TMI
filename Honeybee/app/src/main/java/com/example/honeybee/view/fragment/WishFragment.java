package com.example.honeybee.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.honeybee.R;


public class WishFragment extends Fragment {
    private static final String TAG = "WishFragment.class";

    public WishFragment() {
    }

    public static WishFragment newInstance() {
        Log.d(TAG, "newInstance() 호출");
        Bundle bundle = new Bundle();
        WishFragment fragment = new WishFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "WishFragment onCreateView() called");
        return inflater.inflate(R.layout.fragment_wish, container, false);
    }
}