package com.example.demo.src.seat;

import com.example.demo.src.seat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SeatDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){this.jdbcTemplate = new JdbcTemplate(dataSource);}

    public List<GetSeatRes> getSeatsByMovieTime(int movietimeIdx){
        String getSeatsByMovieTimeQuery = "select * from seatinfo where movietimeIdx = ?";
        int getSeatsByMovieTimeParams = movietimeIdx;
        return this.jdbcTemplate.query(getSeatsByMovieTimeQuery,
                (rs, rowNum) -> new GetSeatRes(
                        rs.getInt("movietimeIdx"),
                        rs.getInt("seatIdx"),
                        rs.getInt("userIdx")),
                getSeatsByMovieTimeParams);
    }

    public int modifySeatstatus(PatchSeatReq patchSeatReq){
        String modifySeatStatusQuery = "update seatinfo set userIdx = ? where movietimeIdx = ? and seatIdx = ?";
        Object[] modifySeatStatusParams = new Object[]{patchSeatReq.getUserIdx(), patchSeatReq.getMovietimeIdx(), patchSeatReq.getSeatIdx()};

        return this.jdbcTemplate.update(modifySeatStatusQuery, modifySeatStatusParams);
    }
}
