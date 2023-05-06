package com.j2ee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Orders {
    // 订单编号
    private String id;

    //用户姓名
    private String username;

    //旅客身份证id
    private String passengerId;

    //航班id
    private String flightId;

}
