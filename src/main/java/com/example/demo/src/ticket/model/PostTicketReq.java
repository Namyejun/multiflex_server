package com.example.demo.src.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostTicketReq {
    private int userIdx;
    private int movietimeIdx;
    private int count;
    private int price;
    private int status;
}
