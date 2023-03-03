package com.example.demo.src.ticket;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.ticket.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TicketDao ticketDao;

    @Autowired
    public TicketService(TicketDao ticketDao){
        this.ticketDao = ticketDao;
    }

    public PostTicketRes postTicket(PostTicketReq postTicketReq) throws BaseException{
        try{
            int ticketIdx = ticketDao.postTicket(postTicketReq);
            return new PostTicketRes(ticketIdx);
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
