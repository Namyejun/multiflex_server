package com.example.demo.src.employee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostEmployeeReq {
    private String EmployeeName;
    private String id;
    private String email;
    private String password;
    private int birth;
    private String address;
}

//    private String ID;
//    private String employeeName;
//    private String password;
//    private String email;
//    private int birth;
//    private String address;
//    private int status;
