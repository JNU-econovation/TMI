package com.example.honeybee.model;

import lombok.Builder;
import lombok.Data;

@Data
public class InterestData {
    Integer exercise;
    Integer hiking;
    Integer sports;
    Integer art;
    Integer museums;
    Integer reading;
    Integer shopping;
    Integer dining;
    Integer theater;
    Integer concerts;
    Integer movies;
    Integer tv;
    Integer music;

    @Builder
    public InterestData(Integer exercise, Integer hiking, Integer sports,
                        Integer art, Integer museums, Integer reading,
                        Integer shopping, Integer dining, Integer theater,
                        Integer concerts, Integer movies, Integer tv, Integer music) {
        this.exercise = exercise;
        this.hiking = hiking;
        this.sports = sports;
        this.art = art;
        this.museums = museums;
        this.reading = reading;
        this.shopping = shopping;
        this.dining = dining;
        this.theater = theater;
        this.concerts = concerts;
        this.movies = movies;
        this.tv = tv;
        this.music = music;
    }
}