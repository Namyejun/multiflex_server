package com.example.demo.src.ticket;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.ticket.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
public class TicketContoller {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final TicketProvider ticketProvider;
    @Autowired
    private final TicketService ticketService;

    public TicketContoller(TicketService ticketService, TicketProvider ticketProvider){
        this.ticketService = ticketService;
        this.ticketProvider = ticketProvider;
    }

    /*
    * [POST]/app/tickets/post
    * {
    *   "userIdx":?,
    *   "movietimeIdx":?,
    *   "count":?,
    *   "price":?
    *   "status":1 // 0 = 예매 취소, 1 = 예매
    * }
    */
    @PostMapping("/tickets/post")
    public BaseResponse<PostTicketRes> postTicket(@RequestBody PostTicketReq postTicketReq){
        try{
            PostTicketRes postTicketRes = ticketService.postTicket(postTicketReq);
            return new BaseResponse<>(postTicketRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /*
    * [GET]/app/tickets?userIdx=?
    */
    @ResponseBody
    @GetMapping("/tickets")
    public BaseResponse<List<GetTicketRes>> getTicketByUser(@RequestParam(required = true) Integer userIdx){
        try{
            List<GetTicketRes> getTicketRes = ticketProvider.getTicketByUser(userIdx);
            return new BaseResponse<>(getTicketRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
