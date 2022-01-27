package com.example.honeybee.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.honeybee.R;

public class UserMoreFragment extends Fragment {

    public static UserMoreFragment newInstance() {
        return new UserMoreFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_more, container, false);
        Button button = view.findViewById(R.id.nextBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UserMoreFragment.class", "UserMoreFragment.class caleed");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ImageFragment imageFragment = new ImageFragment();
                transaction.replace(R.id.fragment_layout, imageFragment);
                transaction.commit();
            }
        });

        return view;
    }
}