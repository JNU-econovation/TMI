package com.example.honeybee.model.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

// JSON, DTO 클래스 선언
@Data
public class LoginData {
    @SerializedName("uid")
    private String uid;

    @SerializedName("password")
    private String password;

    public LoginData(String uid, String password) {
        this.uid = uid;
        this.password = password;
    }
}
