package com.example.demo.src.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// Service Create, Update, Delete 의 로직 처리
@Service
public class MovieService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MovieDao movieDao;
    private final MovieProvider movieProvider;


    @Autowired
    public MovieService(MovieDao movieDao, MovieProvider movieProvider) {
        this.movieDao = movieDao;
        this.movieProvider = movieProvider;
    }
}
