package com.example.demo.src.event;


import com.example.demo.src.event.model.GetEventRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EventDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 영화 리스트 조회
    public List<GetEventRes> getEventResList(){
        String getEventQuery = "select * from event";
        return this.jdbcTemplate.query(getEventQuery,
                (rs,rowNum) -> new GetEventRes(
                        rs.getInt("eventIdx"),
                        rs.getString("eventImg"),
                        rs.getString("eventName")
                )
                );
    }

    /*
    private int Idx;
    private String movieName;
    private String movieImage;
    private String genre;
    private String birth;
    private String summary;
    private String producers;
    private String actors;
    private String description;
    private int time;
    private float star;
    */

    // 고객 Idx 로 조회
    public GetEventRes getEvent(int eventIdx){
        String getMovieQuery = "select * from event where eventIdx = ?";
        int getEventParams = eventIdx;
        return this.jdbcTemplate.queryForObject(getMovieQuery,
                (rs, rowNum) -> new GetEventRes(
                        rs.getInt("eventIdx"),
                        rs.getString("eventImg"),
                        rs.getString("eventName")
                ),
                getEventParams);
    }
}
