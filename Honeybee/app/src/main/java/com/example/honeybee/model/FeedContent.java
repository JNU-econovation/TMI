package com.example.honeybee.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedContent {

    @SerializedName("profile")
    private String profile;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private int age;

    @SerializedName("score")
    private int score;

    @SerializedName("personalities")
    private String[] personalities;

    @SerializedName("introduce")
    private String introduce;

    @SerializedName("userId")
    private String[] userId;

    @Builder
    public FeedContent(String profile, String name, int age, int score, String[] personalities, String introduce, String[] userId) {
        this.profile = profile;
        this.name = name;
        this.age = age;
        this.score = score;
        this.personalities = personalities;
        this.introduce = introduce;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FeedContent{" +
                "profile='" + profile + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", personalities=" + Arrays.toString(personalities) +
                ", introduce='" + introduce + '\'' +
                ", userId=" + Arrays.toString(userId) +
                '}';
    }
}
