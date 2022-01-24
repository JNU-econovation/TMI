package com.example.honeybee.model.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LikeUserDto {
    @SerializedName("profile")
    private String profile;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private int age;

    @Builder
    public LikeUserDto(String profile, String name, int age) {
        this.profile = profile;
        this.name = name;
        this.age = age;
    }



}
