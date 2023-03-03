package com.example.demo.src.movietime;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.movietime.model.*;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class MovieTimeProvider {

    private final MovieTimeDao movieTimeDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Autowired
    public MovieTimeProvider(MovieTimeDao movieTimeDao){
        this.movieTimeDao = movieTimeDao;
    }

    @Autowired


    public List<GetMovieTimeRes> getMovieTimes() throws BaseException{
        try{
            List<GetMovieTimeRes> getMovieTimeRes = movieTimeDao.getMovieTimes();
            return getMovieTimeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // paging
    public List<GetMovieTimeRes> getMovieTimePaging(int size, int page) throws BaseException {
        try{
            List<GetMovieTimeRes> getMovieTimeRes = movieTimeDao.getMovieTimePaging(size, page);
            return getMovieTimeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



    public List<GetMovieTimeRes> getMovieTimeByParams(String showdate, int movieIdx, int branchIdx) throws BaseException {
        try{
            List<GetMovieTimeRes> getMovieTimeRes = movieTimeDao.getMovieTimesByParams(showdate, movieIdx, branchIdx);
            return getMovieTimeRes;
        }
        catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<GetMovieTimeSeatsRes> getMovieTimeSeatsByParams(String showdate, int movieIdx, int branchIdx) throws BaseException{
        try{
            List<GetMovieTimeSeatsRes> getMovieTimeSeatsRes = movieTimeDao.getMovieTimeSeatsByParams(showdate, movieIdx, branchIdx);
            return getMovieTimeSeatsRes;
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int getTotalSeat(int movietimeIdx) throws BaseException{
        try{
            return movieTimeDao.getTotalSeat(movietimeIdx);
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int getRemainSeat(int movietimeIdx) throws BaseException{
        try{
            return movieTimeDao.getRemainSeat(movietimeIdx);
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
