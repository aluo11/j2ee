package com.j2ee.entity;

import lombok.Data;

@Data
public class Ticket {
    //旅客身份证id
    private String passengerId;

    //航班id
    private String flightId;
}
