package com.example.honeybee.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.honeybee.R;
import com.example.honeybee.contract.FeedContract;
import com.example.honeybee.presenter.FeedContentPresenterImpl;
import com.example.honeybee.view.activity.DetailFeedContentActivity;

public class FeedContentFragment extends Fragment implements FeedContract.View {
    private ImageView iv_profile;
    private DetailFeedContentActivity detailFeedContentActivity;
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
        View view = inflater.inflate(R.layout.fragment_feed_content, container, false);

        lookInfoDetail(view);
        return view;

    }

    @Override
    public void addFragment(Fragment fragment) {

    }

    @Override
    public void moveActivity(Activity activity) {
        Intent intent = new Intent(getContext(), activity.getClass());
        startActivity(intent);
    }

    public void lookInfoDetail(View view ) {
        iv_profile = view.findViewById(R.id.iv_profile);

        FeedContract.Presenter presenter = new FeedContentPresenterImpl(this);

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                detailFeedContentActivity = new DetailFeedContentActivity();
                presenter.setActivity(detailFeedContentActivity);
            }
        });
    }
}