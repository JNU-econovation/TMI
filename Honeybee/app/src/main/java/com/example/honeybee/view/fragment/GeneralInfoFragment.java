package com.example.honeybee.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.honeybee.R;

public class GeneralInfoFragment extends Fragment {
    private final int GET_GALLERY_IMAGE = 200;

    private ImageView imageView;
    private String imagePath;

    public static GeneralInfoFragment newInstance(){
        return new GeneralInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_general_info, container, false);

        imageView = (ImageView) view.findViewById(R.id.addImage);
    return view;
    }

}