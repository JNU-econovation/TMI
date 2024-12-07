package com.example.honeybee.model;

import lombok.Data;

@Data
public class AiResponseData {
    private String result;

    public AiResponseData(String result) {
        this.result = result;
    }
}

