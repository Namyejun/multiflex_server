package com.example.demo.src.movietime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMovieTimeSeatsRes {
    private int Idx;
    private int movieIdx;
    private int theaterIdx;
    private String showdate;
    private String startTime;
    private String endTime;
    private int count_remain;
}
