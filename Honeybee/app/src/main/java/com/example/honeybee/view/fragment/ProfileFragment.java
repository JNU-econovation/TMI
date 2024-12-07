package com.example.honeybee.view.fragment;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.honeybee.R;


public class ProfileFragment extends Fragment {
    private ImageView iv_profile;
    private TextView tv_nickname;
    private TextView tv_mbti;
    private TextView tv_myInfo;
    private TextView tv_logout;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        myInfoClicked();
        logoutClicked();
        return view;
    }

    public void myInfoClicked() {
        tv_myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void logoutClicked() {
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void init(View view) {
        iv_profile = view.findViewById(R.id.iv_profile);
        tv_nickname = view.findViewById(R.id.tv_nickname);
        tv_mbti = view.findViewById(R.id.tv_mbti);
        tv_myInfo = view.findViewById(R.id.tv_myInfo);
        tv_logout = view.findViewById(R.id.tv_logout);
        iv_profile.setClipToOutline(true);

    }
}