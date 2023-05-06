package com.j2ee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class User {
    //用户姓名
    private String username;

    //用户密码
    private String password;

    //旅客身份证id
    private String passengerId;
}
