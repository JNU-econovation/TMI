package com.example.honeybee.model;
import lombok.Builder;
import lombok.Data;

@Data
public class IdealTypeData {
    Integer attractiveness;
    Integer sincerity;
    Integer intelligence;
    Integer fun;
    Integer ambition;
    Integer shared_interest;

    @Builder
    public IdealTypeData(Integer attractiveness, Integer sincerity,
                         Integer intelligence, Integer fun,
                         Integer ambition, Integer shared_interest) {
        this.attractiveness = attractiveness;
        this.sincerity = sincerity;
        this.intelligence = intelligence;
        this.fun = fun;
        this.ambition = ambition;
        this.shared_interest = shared_interest;
    }
}