package com.example.demo.src.fcm;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestDTO {
    private String TargetToken;
    private String Title;
    private String Body;
}