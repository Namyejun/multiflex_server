package com.example.demo.src.movie.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMovieRes {
    private int movieIdx;
    private String movieName;
    private String movieImage;
    private String genre;
    private String birth;
    private String summary;
    private String producers;
    private String actors;
    private String description;
    private int time;
    private float star;
}
