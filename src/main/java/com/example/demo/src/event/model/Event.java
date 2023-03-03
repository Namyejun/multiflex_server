package com.example.demo.src.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Event {
    private int eventIdx;
    private String eventImg;
    private String eventName;
}
