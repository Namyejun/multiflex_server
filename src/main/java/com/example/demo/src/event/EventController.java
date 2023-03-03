package com.example.demo.src.event;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.event.model.GetEventRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/events")
public class EventController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final EventProvider eventProvider;
    @Autowired
    private final EventService eventService;


    public EventController(EventProvider eventProvider, EventService eventService){
        this.eventProvider = eventProvider;
        this.eventService = eventService;
    }

    /**
     * 회원 조회 API
     * [GET] /customers
     * 회원 번호 및 이메일 검색 조회 API
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetEventRes>> getEvents() {
        try{
            // Get Users
            List<GetEventRes> getEventRes = eventProvider.getEvents();
            return new BaseResponse<>(getEventRes);
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
    public BaseResponse<GetEventRes> getEvent(@PathVariable("eventIdx") int Idx) {
        // Get Users
        try{
            GetEventRes getEventRes = eventProvider.getEvent(Idx);
            return new BaseResponse<>(getEventRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
