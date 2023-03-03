package com.example.demo.src.employee;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.employee.model.Employee;
import com.example.demo.src.employee.model.GetEmployeeRes;
import com.example.demo.src.employee.model.PostEmpLoginReq;
import com.example.demo.src.employee.model.PostEmpLoginRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PostLoginReq;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.src.user.model.User;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class EmployeeProvider {
    private final EmployeeDao employeeDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EmployeeProvider(EmployeeDao employeeDao, JwtService jwtService) {
        this.employeeDao = employeeDao;
        this.jwtService = jwtService;
    }

    // 일하고 있는 직원출력
    public List<GetEmployeeRes> getWorkingEmployees() throws BaseException{
        try{
            List<GetEmployeeRes> getEmployeeRes = employeeDao.getEmployeeWorks();
            return getEmployeeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetEmployeeRes> getEmployees() throws BaseException{
        try{
            List<GetEmployeeRes> getEmployeeRes = employeeDao.getEmployees();
            return getEmployeeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetEmployeeRes> getEmployeeByEmail(String email) throws BaseException{
        try{
            List<GetEmployeeRes> getEmployeeRes = employeeDao.getEmployeeByEmail(email);
            return getEmployeeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetEmployeeRes getEmployee(int employeeIdx) throws BaseException {
        try {
            GetEmployeeRes getEmployeeRes = employeeDao.getEmployee(employeeIdx);
            return getEmployeeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEmail(String email) throws BaseException{
        try{
            return employeeDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostEmpLoginRes logIn(PostEmpLoginReq postEmpLoginReq) throws BaseException{
        Employee employee = employeeDao.getPwd(postEmpLoginReq);
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(employee.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(postEmpLoginReq.getPassword().equals(password)){
            int userIdx = employeeDao.getPwd(postEmpLoginReq).getEmployeeIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostEmpLoginRes(userIdx,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }
}
