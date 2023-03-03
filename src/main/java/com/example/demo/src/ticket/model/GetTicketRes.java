package com.example.demo.src.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetTicketRes {
    private int Idx;
    private int userIdx;
    private String movieName;
    private String theaterName;
    private String startTime;
    private int count;
    private int price;
}