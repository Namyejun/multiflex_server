package com.example.demo.src.branch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetBranchRes {
    private int Idx;
    private String branchName;
    private String branchAddress;
    private int branchTelecom;
    private int locationIdx;
    private String locationName;
}
