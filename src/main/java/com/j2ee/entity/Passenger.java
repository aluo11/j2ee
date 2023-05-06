package com.j2ee.entity;

import lombok.Data;

@Data
public class Passenger {
    //旅客身份证id
    private String id;

    //旅客姓名
    private String name;

    //性别
    private String sex;

    //工作单位
    private String workplace;
}
