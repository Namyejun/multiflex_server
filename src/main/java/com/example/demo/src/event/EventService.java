package com.example.demo.src.event;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Service Create, Update, Delete 의 로직 처리
@Service
public class EventService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EventDao eventDao;
    private final EventProvider eventProvider;


    @Autowired
    public EventService(EventDao eventDao, EventProvider eventProvider) {
        this.eventDao = eventDao;
        this.eventProvider = eventProvider;
    }
}
