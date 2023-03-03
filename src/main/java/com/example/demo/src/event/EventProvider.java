package com.example.demo.src.event;


import com.example.demo.config.BaseException;
import com.example.demo.src.event.model.GetEventRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class EventProvider {

    private final EventDao eventDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EventProvider(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public List<GetEventRes> getEvents() throws BaseException{
        try{
            List<GetEventRes> getEventRes = eventDao.getEventResList();
            return getEventRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // Customer 조회 Idx로
    public GetEventRes getEvent(int eventIdx) throws BaseException {
        try {
            GetEventRes getEventRes = eventDao.getEvent(eventIdx);
            return getEventRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
