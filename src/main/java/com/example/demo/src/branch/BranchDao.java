package com.example.demo.src.branch;

import com.example.demo.src.branch.model.*;
import com.example.demo.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BranchDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }

    // transactional 지점 조회
    public List<GetBranchRes> searchBranches(String search){
        String getSearchParams = search;
        if(getSearchParams == ""){
            System.out.println("search!!");
            String getBranchesQuery = "select branch.Idx, branchName, branchAddress, branchTelecom, branch.locationIdx, locationName\n" +
                    "from branch inner join location\n" +
                    "on branch.locationIdx = location.Idx";
            return this.jdbcTemplate.query(getBranchesQuery,
                    (rs, rowNum) -> new GetBranchRes(
                            rs.getInt("Idx"),
                            rs.getString("branchName"),
                            rs.getString("branchAddress"),
                            rs.getInt("branchTelecom"),
                            rs.getInt("locationIdx"),
                            rs.getString("locationName")
                    )
            );
        }
        else{
            String getSearchBranchesQuery = "select branch.Idx, branchName, branchAddress, branchTelecom, branch.locationIdx, locationName\n" +
                    "from branch inner join location\n" +
                    "on branch.locationIdx = location.Idx\n" +
                    "where branch.branchAddress like CONCAT('%',?,'%')";
            return this.jdbcTemplate.query(getSearchBranchesQuery,
                    (rs, rowNum) -> new GetBranchRes(
                            rs.getInt("Idx"),
                            rs.getString("branchName"),
                            rs.getString("branchAddress"),
                            rs.getInt("branchTelecom"),
                            rs.getInt("locationIdx"),
                            rs.getString("locationName")),
                    getSearchParams);
        }
    }

    public int createSearch(PostSearchReq postSearchReq){
        String searchQuery = "INSERT INTO search(searchName)\n" +
                "VALUES(?);";
        Object[] searchParams = new Object[]{postSearchReq.getSearchName()};
        this.jdbcTemplate.update(searchQuery, searchParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    // search 내용 response
    public List<GetSearchRes> getSearch(){
        String getBranchesQuery = "select * from search";
        return this.jdbcTemplate.query(getBranchesQuery,
                (rs, rowNum) -> new GetSearchRes(
                        rs.getInt("Idx"),
                        rs.getString("searchName")
                )
        );
    }

    public List<GetBranchRes> getBranches(){
        String getBranchesQuery =   "select branch.Idx, branchName, branchAddress, branchTelecom, branch.locationIdx, locationName\n" +
                                    "from branch inner join location\n" +
                                    "on branch.locationIdx = location.Idx";
        return this.jdbcTemplate.query(getBranchesQuery,
                (rs, rowNum) -> new GetBranchRes(
                        rs.getInt("Idx"),
                        rs.getString("branchName"),
                        rs.getString("branchAddress"),
                        rs.getInt("branchTelecom"),
                        rs.getInt("locationIdx"),
                        rs.getString("locationName")
                )
        );
    }

    public List<GetLocationRes> getLocations(){
        String getLocationsQuery = "select Idx, locationName from location";
        return this.jdbcTemplate.query(getLocationsQuery,
                (rs, rowNum) -> new GetLocationRes(
                        rs.getInt("Idx"),
                        rs.getString("locationName")
                )
        );
    }

    public List<GetBranchRes> getBranchesByLocation(int locationIdx){
        String getBranchesByLocationQuery = "select branch.Idx, branchName, branchAddress, branchTelecom, branch.locationIdx, locationName\n" +
                                            "from branch inner join location\n" +
                                            "on branch.locationIdx = location.Idx\n" +
                                            "where location.Idx = ?";
        int getBranchesByLocationParams = locationIdx;
        return this.jdbcTemplate.query(getBranchesByLocationQuery,
                (rs, rowNum) -> new GetBranchRes(
                        rs.getInt("Idx"),
                        rs.getString("branchName"),
                        rs.getString("branchAddress"),
                        rs.getInt("branchTelecom"),
                        rs.getInt("locationIdx"),
                        rs.getString("locationName")),
                getBranchesByLocationParams);
    }
}
