package com.example.honeybee.model.dto;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FeedContentDto implements Serializable {

    private ArrayList<String> user_image;
    private String nickname;
    private Integer age;
    private ArrayList<String> personality;
    private String introduce;


    @Builder
    public FeedContentDto(ArrayList<String> user_image, String nickname, Integer age, ArrayList<String> personality, String introduce) {
        this.user_image = user_image;
        this.nickname = nickname;
        this.age = age;
        this.personality = personality;
        this.introduce = introduce;
    }
}
