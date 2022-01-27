package com.example.honeybee.model.dto;

import android.graphics.Bitmap;
import android.graphics.Point;

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
    private Integer score;
    private String department;
    private Integer location;
    private String mbti;
    private ArrayList<String> personality;
    private String introduce;
    private String smoke;
    private String drink;
    private Integer height;

    @Builder
    public FeedContentDto(ArrayList<String> user_image, String nickname, Integer age,Integer score, String department, Integer location, String mbti, ArrayList<String> personality, String introduce, String smoke, String drink, Integer height) {
        this.user_image = user_image;
        this.nickname = nickname;
        this.age = age;
        this.score = score;
        this.department = department;
        this.location = location;
        this.mbti = mbti;
        this.personality = personality;
        this.introduce = introduce;
        this.smoke = smoke;
        this.drink = drink;
        this.height = height;
    }
}
