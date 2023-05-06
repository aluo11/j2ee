package com.j2ee.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Flight {
    //航班id
    private String id;

    //航空公司
    private String company;

    //始发地
    private String provenance;

    //目的地
    private String destination;

    //座位总数
    private Integer total;

    //座位剩余量
    private Integer rest;

    //登机时间
    private String boardingTime;

    //到达时间
    private String arriveTime;

    //机票金额
    private Integer money;
}
