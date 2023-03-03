package com.example.demo.src.movie;

import com.example.demo.src.movie.model.GetMovieRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MovieDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 영화 리스트 조회
    public List<GetMovieRes> getMovieResList(){
        String getMovieQuery = "select * from movie";
        return this.jdbcTemplate.query(getMovieQuery,
                (rs,rowNum) -> new GetMovieRes(
                        rs.getInt("movieIdx"),
                        rs.getString("movieName"),
                        rs.getString("movieImage"),
                        rs.getString("genre"),
                        rs.getString("birth"),
                        rs.getString("summary"),
                        rs.getString("producers"),
                        rs.getString("actors"),
                        rs.getString("description"),
                        rs.getInt("time"),
                        rs.getFloat("star")
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
    public GetMovieRes getMovie(int Idx){
        String getMovieQuery = "select * from movie where movieIdx = ?";
        int getMovieParams = Idx;
        return this.jdbcTemplate.queryForObject(getMovieQuery,
                (rs, rowNum) -> new GetMovieRes(
                        rs.getInt("movieIdx"),
                        rs.getString("movieName"),
                        rs.getString("movieImage"),
                        rs.getString("genre"),
                        rs.getString("birth"),
                        rs.getString("summary"),
                        rs.getString("producers"),
                        rs.getString("actors"),
                        rs.getString("description"),
                        rs.getInt("time"),
                        rs.getFloat("star")),
                        getMovieParams);
    }
}
