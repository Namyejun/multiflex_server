package com.example.demo.src.employee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Employee {
    private int employeeIdx;
    private String ID;
    private String employeeName;
    private String password;
    private String email;
    private int birth;
    private String address;
    private int status;     // 0 : 퇴근 , 1 : 출근
}
