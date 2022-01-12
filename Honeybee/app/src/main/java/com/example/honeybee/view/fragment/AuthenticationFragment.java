package com.example.honeybee.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.honeybee.R;
import com.example.honeybee.view.activity.AuthenticationActivity;

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


                System.out.println("auth에서 입력한 값"+authNumber.getText().toString());
                System.out.println("생성된 인증 번호"+authKey);
                if (authNumber.getText().toString().equals(authKey)){
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