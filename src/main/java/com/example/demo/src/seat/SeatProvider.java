package com.example.demo.src.seat;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.seat.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatProvider {
    private final SeatDao seatDao;

    public SeatProvider(SeatDao seatDao){
        this.seatDao = seatDao;
    }

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<GetSeatRes> getSeatsByMovieTime(int movietimeIdx) throws BaseException{
        try{
            List<GetSeatRes> getSeatRes = seatDao.getSeatsByMovieTime(movietimeIdx);
            return getSeatRes;
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
