package com.example.honeybee.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.honeybee.R;

public class FeedContentFragment extends Fragment {
    public FeedContentFragment() {

    }

    public static FeedContentFragment newInstance() {
        FeedContentFragment fragment = new FeedContentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_content, container, false);
    }
}