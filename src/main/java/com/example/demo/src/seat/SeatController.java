package com.example.demo.src.seat;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.seat.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
public class SeatController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SeatProvider seatProvider;
    @Autowired
    private final SeatService seatService;


    public SeatController(SeatProvider seatProvider, SeatService seatService){
        this.seatProvider = seatProvider;
        this.seatService = seatService;
    }

    /*
    * 좌석 조회 API
    * [GET]/app/seats?movietimeIdx=?
    * @return BaseResponse<List<GetSeatRes>>
    */
    @GetMapping("/seats")
    public BaseResponse<List<GetSeatRes>> getSeats(@RequestParam(required = false) int movietimeIdx){
        try{
            List<GetSeatRes> getSeatRes = seatProvider.getSeatsByMovieTime(movietimeIdx);
            return new BaseResponse<>(getSeatRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
    *
    * 좌석 현황 변경
    * [Patch]/app/seats/modify
    * {
    *   "movietimeIdx":1,
    *   "seatIdx":2,
    *   "userIdx":1
    * }
    *
    */
    @PatchMapping("/seats/modify")
    public BaseResponse<String> modifySeatStatus(@RequestBody Seat seat){
        try{
            PatchSeatReq patchSeatReq = new PatchSeatReq(seat.getMovietimeIdx(),seat.getSeatIdx(),seat.getUserIdx());
            seatService.modifySeatStatus(patchSeatReq);

            String result = "성공";
            return new BaseResponse<>(result);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
