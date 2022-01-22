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
import android.widget.Toast;

import com.example.honeybee.R;
import com.example.honeybee.view.activity.AuthenticationActivity;
import com.example.honeybee.view.activity.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthenticationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthenticationFragment extends Fragment {

    private String authKey;

    public static Fragment newInstance() {
        return new AuthenticationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authentication, container, false);
        Button button = view.findViewById(R.id.authBtn);
        EditText authNumber = view.findViewById(R.id.authNumber);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                authKey = getArguments().getString("authKey");

                if (authNumber.getText().toString().equals(authKey)){
                    Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                    System.out.println("인증되었습니다.");
                }
                else {
                    System.out.println("인증에 실패했습니다.");
                }
            }
        });
        return view;
    }



}