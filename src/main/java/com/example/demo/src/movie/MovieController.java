package com.example.demo.src.movie;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.event.EventProvider;
import com.example.demo.src.event.EventService;
import com.example.demo.src.event.model.GetEventRes;
import com.example.demo.src.movie.model.GetMovieRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/app/movie")
public class MovieController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MovieProvider movieProvider;
    @Autowired
    private final MovieService movieService;
    @Autowired
    private final EventProvider eventProvider;
    @Autowired
    private final EventService eventService;


    public MovieController(MovieProvider movieProvider, MovieService movieService, EventProvider eventProvider, EventService eventService){
        this.movieProvider = movieProvider;
        this.movieService = movieService;
        this.eventProvider = eventProvider;
        this.eventService = eventService;
    }

    //Query String
    // 여러객체 보내주기
    @ResponseBody
    @GetMapping("/main") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<Map> getMovies() {
        try{
            // Get Users
            List<GetMovieRes> getMovieRes = movieProvider.getMovies();
            List<GetEventRes> getEventRes = eventProvider.getEvents();
//            ArrayList<Object> response = new ArrayList<>();
//            response.add(getMovieRes);
//            response.add(getEventRes);

            Map<String, Object> result = new LinkedMultiValueMap();
            result.put("movie", getMovieRes);
            result.put("events", getEventRes);

            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // movie 객체
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetMovieRes>> getEvents() {
        try{
            // Get Users
            List<GetMovieRes> getMovieRes = movieProvider.getMovies();
            return new BaseResponse<>(getMovieRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 1명 조회 API
     * [GET] /customer/:Idx
     * @return BaseResponse<GetCustomerRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{Idx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetMovieRes> getMovie(@PathVariable("Idx") int Idx) {
        // Get Users
        try{
            GetMovieRes getMovieRes = movieProvider.getMovie(Idx);
            return new BaseResponse<>(getMovieRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
