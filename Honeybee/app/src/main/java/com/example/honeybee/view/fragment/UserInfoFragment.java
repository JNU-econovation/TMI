package com.example.honeybee.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.honeybee.R;

public class UserInfoFragment extends Fragment {

    public static UserInfoFragment newInstance(){
        return new UserInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        Button button = view.findViewById(R.id.nextBtn);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                UserMoreFragment userMoreFragment = new UserMoreFragment();
                transaction.replace(R.id.fragment_layout, userMoreFragment);
                transaction.commit();
            }
        });
        return view;
    }

}