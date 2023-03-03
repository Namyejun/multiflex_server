package com.example.demo.src.employee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostEmployeeRes {
    private String jwt;
    private int employeeIdx;
}
