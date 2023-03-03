package com.example.demo.src.ticket;

import com.example.demo.src.ticket.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

import javax.sql.DataSource;

@Repository
public class TicketDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){this.jdbcTemplate = new JdbcTemplate(dataSource);}

    public int postTicket(PostTicketReq postTicketReq){
        String postTicketQuery = "insert into ticketing(userIdx, movietimeIdx, count, price, status) values(?,?,?,?,?)";
        Object[] postTicketParams = new Object[]{postTicketReq.getUserIdx(), postTicketReq.getMovietimeIdx(), postTicketReq.getCount(), postTicketReq.getPrice(), postTicketReq.getStatus()};
        this.jdbcTemplate.update(postTicketQuery, postTicketParams);

        String lastInserIdxQuery = "select last_Insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdxQuery, int.class);
    }

    public List<GetTicketRes> getTicketByUser(int userIdx){
        String getTicketByUserQuery = "select ticketing.Idx, ticketing.userIdx, final_table.movieName, final_table.theaterName, final_table.startTime, ticketing.count, ticketing.price\n" +
                "from ticketing inner join (\n" +
                "select t1.Idx as Idx, t1.startTime, t1.movieName, t2.theaterName  \n" +
                "from(\n" +
                "select movietime.Idx as Idx, startTime, movieName\n" +
                "from movietime inner join movie\n" +
                "on movietime.movieIdx = movie.movieIdx\n" +
                ") as t1\n" +
                "inner join\n" +
                "(\n" +
                "select movietime.Idx as Idx, theaterName\n" +
                "from movietime inner join theater\n" +
                "on movietime.theaterIdx = theater.Idx\n" +
                ") as t2\n" +
                "on t1.Idx = t2.Idx\n" +
                ") as final_table\n" +
                "on ticketing.movietimeIdx = final_table.Idx\n" +
                "where ticketing.userIdx = ?";
        int getTicketByUserParams = userIdx;

        return this.jdbcTemplate.query(getTicketByUserQuery,
                (rs, rowNum) -> new GetTicketRes(
                        rs.getInt("Idx"),
                        rs.getInt("userIdx"),
                        rs.getString("movieName"),
                        rs.getString("theaterName"),
                        rs.getString("startTime"),
                        rs.getInt("count"),
                        rs.getInt("price")),
                getTicketByUserParams);
    }
}
