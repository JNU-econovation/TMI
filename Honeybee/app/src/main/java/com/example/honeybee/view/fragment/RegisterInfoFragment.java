package com.example.honeybee.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.honeybee.R;
import com.example.honeybee.view.activity.RegisterActivity;


public class RegisterInfoFragment extends Fragment {

    public static Fragment newInstance(){
        return new RegisterInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_info, container, false);
        Button button = view.findViewById(R.id.nextBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                GeneralInfoFragment generalInfoFragment = new GeneralInfoFragment();
                transaction.replace(R.id.fragment_layout, generalInfoFragment);
                transaction.commit();
            }
        });
        return view;
    }
}