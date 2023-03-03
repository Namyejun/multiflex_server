package com.example.demo.src.movietime;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import com.example.demo.src.movietime.model.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/app")
public class MovieTimeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MovieTimeProvider movieTimeProvider;

    public MovieTimeController(MovieTimeProvider movieTimeProvider){
        this.movieTimeProvider = movieTimeProvider;
    }

    /*
     * 영화상영시간 전체 조회 API
     * [GET]/app/movietimes?showdate=2021-11-30&movieIdx=1&branchIdx=1
     */
    @ResponseBody
    @GetMapping("/movietime-all")
    public BaseResponse<List<GetMovieTimeRes>> getMovieTimeAll(){
        try{
            List<GetMovieTimeRes> getMovieTimeRes = movieTimeProvider.getMovieTimes();
            return new BaseResponse<>(getMovieTimeRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // paging
    @ResponseBody
    @GetMapping("/movietime/paging")
    public BaseResponse<List<GetMovieTimeRes>> getMovieTimePaging(
            @RequestParam(required = true) int size,
            @RequestParam(required = true) int page
            ){
        try{
            List<GetMovieTimeRes> getMovieTimeRes = movieTimeProvider.getMovieTimePaging(size, page);
            return new BaseResponse<>(getMovieTimeRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /*
     * 영화상영시간 조회 API
     * [GET]/app/movietimes?showdate=2021-11-30&movieIdx=1&branchIdx=1
     */
    @ResponseBody
    @GetMapping("/movietimes")
    public BaseResponse<List<GetMovieTimeRes>> getMovieTime(@RequestParam(required = true) String showdate, @RequestParam(required = true) int movieIdx, @RequestParam(required = true) int branchIdx){
        try{
            List<GetMovieTimeRes> getMovieTimeRes = movieTimeProvider.getMovieTimeByParams(showdate, movieIdx, branchIdx);
            return new BaseResponse<>(getMovieTimeRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/mts")
    public BaseResponse<List<GetMovieTimeSeatsRes>> getMovieTimeSeat(@RequestParam(required = true) String showdate, @RequestParam(required = true) int movieIdx, @RequestParam(required = true) int branchIdx){
        try{
            List<GetMovieTimeSeatsRes> getMovieTimeSeatsRes = movieTimeProvider.getMovieTimeSeatsByParams(showdate, movieIdx, branchIdx);
            return new BaseResponse<>(getMovieTimeSeatsRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 전체 좌석 개수 조회 [GET]/app/totalseats?movietimeIdx=?
    @ResponseBody
    @GetMapping("/totalseats")
    public BaseResponse getTotalSeat(@RequestParam(required = false) int movietimeIdx){
        try {
            int count = movieTimeProvider.getTotalSeat(movietimeIdx);
            return new BaseResponse(count);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }

    // 남은 좌석 개수 조회 [GET]/app/remainseats?movietimeIdx=?
    @ResponseBody
    @GetMapping("/remainseats")
    public BaseResponse getRemainSeat(@RequestParam(required = false) int movietimeIdx){
        try {
            int count = movieTimeProvider.getRemainSeat(movietimeIdx);
            return new BaseResponse(count);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }
    }
}
