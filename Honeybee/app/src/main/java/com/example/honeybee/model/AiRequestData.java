package com.example.honeybee.model;

import lombok.Data;

@Data
public class AiRequestData {
    private String content;

    public AiRequestData(String content) {
        this.content = content;
    }
}
