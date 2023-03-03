package com.example.demo.src.employee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostEmpLoginReq {
    private String id;
    private String password;
}
