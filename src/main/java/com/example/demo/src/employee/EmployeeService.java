package com.example.demo.src.employee;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.employee.model.PathWorkReq;
import com.example.demo.src.employee.model.PostEmployeeReq;
import com.example.demo.src.employee.model.PostEmployeeRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class EmployeeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EmployeeDao employeeDao;
    private final EmployeeProvider employeeProvider;
    private final JwtService jwtService;


    @Autowired
    public EmployeeService(EmployeeDao employeeDao, EmployeeProvider employeeProvider, JwtService jwtService) {
        this.employeeDao = employeeDao;
        this.employeeProvider = employeeProvider;
        this.jwtService = jwtService;
    }

    //POST
    public PostEmployeeRes createEmployee(PostEmployeeReq postEmployeeReq) throws BaseException {
        //중복
        if(employeeProvider.checkEmail(postEmployeeReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String pwd;
        try{
            //암호화
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postEmployeeReq.getPassword());
            postEmployeeReq.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userIdx = employeeDao.createEmployee(postEmployeeReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostEmployeeRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyWork(PathWorkReq pathWorkReq) throws BaseException {
        try{
            int result = employeeDao.modifyWorks(pathWorkReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = employeeDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
