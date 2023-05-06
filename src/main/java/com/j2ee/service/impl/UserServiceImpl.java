package com.j2ee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2ee.entity.User;
import com.j2ee.mapper.UserMapper;
import com.j2ee.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
