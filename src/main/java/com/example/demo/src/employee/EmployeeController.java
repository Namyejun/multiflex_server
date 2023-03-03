package com.example.demo.src.employee;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.employee.model.*;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/employees")
public class EmployeeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final EmployeeProvider employeeProvider;
    @Autowired
    private final EmployeeService employeeService;
    @Autowired
    private final JwtService jwtService;

    public EmployeeController(EmployeeProvider employeeProvider, EmployeeService employeeService, JwtService jwtService){
        this.employeeProvider = employeeProvider;
        this.employeeService = employeeService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 조회 API
     * [GET] /employees
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetEmployeeRes>> getEmployees(@RequestParam(required = false) String Email) {
        try{
            if(Email == null){
                List<GetEmployeeRes> getEmployeeRes = employeeProvider.getEmployees();
                return new BaseResponse<>(getEmployeeRes);
            }
            // Get Users
            List<GetEmployeeRes> getEmployeeRes = employeeProvider.getEmployeeByEmail(Email);
            return new BaseResponse<>(getEmployeeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 1명 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{employeeIdx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetEmployeeRes> getEmployee(@PathVariable("employeeIdx") int employeeIdx) {
        // Get Users
        try{
            GetEmployeeRes getEmployeeRes = employeeProvider.getEmployee(employeeIdx);
            return new BaseResponse<>(getEmployeeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    // 일하고 있는 직원
    @ResponseBody
    @GetMapping("/working") // (GET) 127.0.0.1:9000/app/employees/working
    public BaseResponse<List<GetEmployeeRes>> getWorkingEmployees() {
        try{
            List<GetEmployeeRes> getEmployeeRes = employeeProvider.getWorkingEmployees();
            return new BaseResponse<>(getEmployeeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostEmployeeRes> createEmployee(@RequestBody PostEmployeeReq postEmployeeReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postEmployeeReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postEmployeeReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            PostEmployeeRes postEmployeeRes = employeeService.createEmployee(postEmployeeReq);
            return new BaseResponse<>(postEmployeeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostEmpLoginRes> logIn(@RequestBody PostEmpLoginReq postEmpLoginReq){
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostEmpLoginRes postEmpLoginRes = employeeProvider.logIn(postEmpLoginReq);
            return new BaseResponse<>(postEmpLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{employeeIdx}")
    public BaseResponse<String> modifyUserName(@PathVariable("employeeIdx") int employeeIdx, @RequestBody Employee employee){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(employeeIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchUserReq patchUserReq = new PatchUserReq(employeeIdx,employee.getEmployeeName());
            employeeService.modifyUserName(patchUserReq);

            String result = "";
        return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @PatchMapping("/works")
    public BaseResponse<String> modifyWorks(@RequestBody PathWorkReq pathWorkReq){
        try {
            employeeService.modifyWork(pathWorkReq);
            String result = "";
            if(pathWorkReq.getStatus() == 1){result = pathWorkReq.getEmployeeIdx()+" 님이 "+"출근";}
            else{result = pathWorkReq.getEmployeeIdx()+" 님이 "+"퇴근";}
            return new BaseResponse<>(result);
//            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
