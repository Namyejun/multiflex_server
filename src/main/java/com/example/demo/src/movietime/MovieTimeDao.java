package com.example.demo.src.movietime;

import com.example.demo.src.movietime.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MovieTimeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){this.jdbcTemplate = new JdbcTemplate(dataSource);}

    public List<GetMovieTimeRes> getMovieTimes(){
        String getMovieTimeQuery = "select * from movietime\n";
        return this.jdbcTemplate.query(getMovieTimeQuery,
                (rs, rowNum) -> new GetMovieTimeRes(
                        rs.getInt("Idx"),
                        rs.getInt("movieIdx"),
                        rs.getInt("theaterIdx"),
                        rs.getString("showdate"),
                        rs.getString("startTime"),
                        rs.getString("endTime")
                ));
    }

    public List<GetMovieTimeRes> getMovieTimePaging(int size, int pages){
        String getMovieTimeQuery = "select * from movietime limit ?, ?\n";
        int getSize = size;
        int getPage;
        if(pages == 1){
            getPage = 0;
        }
        else{
            getPage = (pages-1)*size;
        }

        return this.jdbcTemplate.query(getMovieTimeQuery,
                (rs, rowNum) -> new GetMovieTimeRes(
                        rs.getInt("Idx"),
                        rs.getInt("movieIdx"),
                        rs.getInt("theaterIdx"),
                        rs.getString("showdate"),
                        rs.getString("startTime"),
                        rs.getString("endTime")),
                getPage, getSize);
    }

    public List<GetMovieTimeRes> getMovieTimesByParams(String showdate,int movieIdx, int branchIdx){
        String getMovieTimeQuery = "select movietime.Idx, movieIdx, theaterIdx, showdate, startTime, endTime\n" +
                                    "from movietime inner join theater\n" +
                                    "on movietime.theaterIdx = theater.Idx\n" +
                                    "where movietime.showdate = ? and movietime.movieIdx = ? and theater.branchIdx = ?";
        String getMovieTimeParam0 = showdate;
        int getMovieTimeParam1 = movieIdx;
        int getMovieTimeParam2 = branchIdx;
        return this.jdbcTemplate.query(getMovieTimeQuery,
                (rs, rowNum) -> new GetMovieTimeRes(
                        rs.getInt("Idx"),
                        rs.getInt("movieIdx"),
                        rs.getInt("theaterIdx"),
                        rs.getString("showdate"),
                        rs.getString("startTime"),
                        rs.getString("endTime")),
                getMovieTimeParam0, getMovieTimeParam1,getMovieTimeParam2);
    }

    public List<GetMovieTimeSeatsRes> getMovieTimeSeatsByParams(String showdate, int movieIdx, int branchIdx){
        String getMTSQuery = "select t1.Idx, t1.movieIdx, t1.theaterIdx, t1.showdate, t1.startTime, t1.endTime,  t2.count_remain\n" +
                            "from\n" +
                            "(select movietime.Idx, movieIdx, theaterIdx, showdate, startTime, endTime\n" +
                            "from movietime inner join theater\n" +
                            "on movietime.theaterIdx = theater.Idx\n" +
                            "where showdate = ? and movieIdx = ? and branchIdx = ?)\n" +
                            "as t1\n" +
                            "inner join\n" +
                            "(select movietimeIdx, count(seatIdx) as count_remain\n" +
                            "from seatinfo\n" +
                            "where userIdx = 0\n" +
                            "group by movietimeIdx) as t2\n" +
                            "on t1.Idx = t2.movietimeIdx;";
        String getMTSParam1 = showdate;
        int getMTSParam2 = movieIdx;
        int getMTSParam3 = branchIdx;

        return this.jdbcTemplate.query(getMTSQuery,
                (rs, rowNum) -> new GetMovieTimeSeatsRes(
                        rs.getInt("Idx"),
                        rs.getInt("movieIdx"),
                        rs.getInt("theaterIdx"),
                        rs.getString("showdate"),
                        rs.getString("startTime"),
                        rs.getString("endTime"),
                        rs.getInt("count_remain")),
                getMTSParam1, getMTSParam2, getMTSParam3);
    }

    public int getTotalSeat(int movietimeIdx){
        String getTotalCountQuery = "select count(*) from seatinfo where movietimeIdx = ?";
        int getTotalCountParams = movietimeIdx;
        return this.jdbcTemplate.queryForObject(getTotalCountQuery,
                int.class,
                getTotalCountParams);
    }

    public int getRemainSeat(int movietimeIdx){
        String getTotalCountQuery = "select count(*) from seatinfo where movietimeIdx = ? and userIdx = 0";
        int getTotalCountParams = movietimeIdx;
        return this.jdbcTemplate.queryForObject(getTotalCountQuery,
                int.class,
                getTotalCountParams);
    }
}
