package com.example.demo.src.ticket;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.ticket.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketProvider {
    private final TicketDao ticketDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TicketProvider(TicketDao ticketDao){
        this.ticketDao = ticketDao;
    }

    public List<GetTicketRes> getTicketByUser(int userIdx) throws BaseException{
        try{
            List<GetTicketRes> getTicketRes = ticketDao.getTicketByUser(userIdx);
            return getTicketRes;
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
