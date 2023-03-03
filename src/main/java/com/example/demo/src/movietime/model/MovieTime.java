package com.example.demo.src.movietime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@AllArgsConstructor
public class MovieTime {
    private int Idx;
    private int movieIdx;
    private int theaterIdx;
    private String showdate;
    private String startTime;
    private String endTime;
}
