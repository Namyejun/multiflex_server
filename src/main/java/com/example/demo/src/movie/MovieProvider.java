package com.example.demo.src.movie;


import com.example.demo.config.BaseException;
import com.example.demo.src.movie.model.GetMovieRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class MovieProvider {

    private final MovieDao movieDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MovieProvider(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public List<GetMovieRes> getMovies() throws BaseException{
        try{
            List<GetMovieRes> getMovieRes = movieDao.getMovieResList();
            return getMovieRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // Customer 조회 Idx로
    public GetMovieRes getMovie(int Idx) throws BaseException {
        try {
            GetMovieRes getMovieRes = movieDao.getMovie(Idx);
            return getMovieRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
