package com.example.demo.src.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Ticket {
    private int Idx;
    private int userIdx;
    private int movieTimeIdx;
    private int count;
    private int price;
    private char status;
}

/*
좌석 정보 보기 : 상영시간테이블에서 가져오기 - 좌석정보테이블 필요
    상영시간 인덱스 받아서 좌석 정보(상영시간 인덱스, 좌석 인덱스, 상태)
    그러면 이제 좌석 선택
    상영시간 인덱스는 이미 선택돼있고 좌석인덱스 같이 and로 묶어서 상태변경
    여기서 count값 같이 받아주고 price도 뭐...


 */
