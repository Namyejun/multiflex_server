package com.example.demo.src.seat;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.seat.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SeatDao seatDao;
    private final SeatProvider seatProvider;

    @Autowired
    public SeatService(SeatDao seatDao, SeatProvider seatProvider){
        this.seatDao = seatDao;
        this.seatProvider = seatProvider;
    }

    public void modifySeatStatus(PatchSeatReq patchSeatReq) throws BaseException{
        try{
            int result = seatDao.modifySeatstatus(patchSeatReq);
            if(result == 0) {
                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_USERNAME);
            }
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
