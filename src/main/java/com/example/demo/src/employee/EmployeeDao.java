package com.example.demo.src.employee;


import com.example.demo.src.employee.model.*;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*
    private int employeeIdx;
    private String ID;
    private String employeeName;
    private String password;
    private String email;
    private int birth;
    private String address;
    private int status;
    */
    public List<GetEmployeeRes> getEmployees(){
        String getUsersQuery = "select * from Employee";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetEmployeeRes(
                        rs.getInt("employeeIdx"),
                        rs.getString("employeeName"),
                        rs.getString("id"),
                        rs.getString("Email"),
                        rs.getString("password"),
                        rs.getInt("birth"),
                        rs.getString("address"),
                        rs.getInt("status")
                )
                );
    }

    public List<GetEmployeeRes> getEmployeeByEmail(String email){
        String getUsersByEmailQuery = "select * from Employee where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetEmployeeRes(
                        rs.getInt("employeeIdx"),
                        rs.getString("employeeName"),
                        rs.getString("id"),
                        rs.getString("Email"),
                        rs.getString("password"),
                        rs.getInt("birth"),
                        rs.getString("address"),
                        rs.getInt("status")
                        ),
                getUsersByEmailParams);
    }

    // 일하고 있는 직원 목록
    public List<GetEmployeeRes> getEmployeeWorks(){
        String getUsersByEmailQuery = "select * from Employee where status = ?";
        int getUsersByEmailParams = 1;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetEmployeeRes(
                        rs.getInt("employeeIdx"),
                        rs.getString("employeeName"),
                        rs.getString("id"),
                        rs.getString("Email"),
                        rs.getString("password"),
                        rs.getInt("birth"),
                        rs.getString("address"),
                        rs.getInt("status")
                ),
                getUsersByEmailParams);
    }



    public GetEmployeeRes getEmployee(int employeeIdx){
        String getUserQuery = "select * from Employee where employeeIdx = ?";
        int getUserParams = employeeIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetEmployeeRes(
                        rs.getInt("employeeIdx"),
                        rs.getString("employeeName"),
                        rs.getString("id"),
                        rs.getString("Email"),
                        rs.getString("password"),
                        rs.getInt("birth"),
                        rs.getString("address"),
                        rs.getInt("status")
                ),
                getUserParams);
    }

    public int createEmployee(PostEmployeeReq postEmployeeReq){
        String createUserQuery = "insert into Employee (employeeName, id, password, email, birth, address, status) VALUES (?,?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postEmployeeReq.getEmployeeName(), postEmployeeReq.getId(), postEmployeeReq.getPassword(), postEmployeeReq.getEmail(), postEmployeeReq.getBirth(), postEmployeeReq.getAddress(), 0};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from Employee where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    // 직원 출퇴근
    public int modifyWorks(PathWorkReq pathWorkReq){
        String modifyUserNameQuery = "update Employee set status = ? where employeeIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{pathWorkReq.getStatus(), pathWorkReq.getEmployeeIdx()};
        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }


    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};
        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }
    /*
    table ticketing(thickedtIdx, userIdx, movietimeIdx, count, price, status, branchIdx)

    select userInfo.userName, movie.movieName, branch.branchname, count, price, userIdx, movieIdx
    from userInfo, movie, branch, ticketing
    where ticketing.userIdx = userInfo.userName AND ticketing.movieIdx = movie.movieIdx AND ticketing.branchIdx = branch.branchIdx
    */

    public Employee getPwd(PostEmpLoginReq postEmpLoginReq){
        String getPwdQuery = "select * from Employee where id = ?";
        String getPwdParams = postEmpLoginReq.getId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new Employee(
                        rs.getInt("employeeIdx"),
                        rs.getString("employeeName"),
                        rs.getString("id"),
                        rs.getString("Email"),
                        rs.getString("password"),
                        rs.getInt("birth"),
                        rs.getString("address"),
                        rs.getInt("status")
                ),
                getPwdParams
        );
    }
}
