package com.j2ee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.j2ee.common.R;
import com.j2ee.entity.User;
import com.j2ee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public R<User> register(User user){
        boolean save = userService.save(user);
        if(save){
            return R.success(user);
        }else{
            return R.error("注册失败");
        }
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(String username, String password, HttpSession session){
        log.info("username:" + username + "  password:" + password);

        // 在数据库中查询该用户名的用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        // 拿到数据库中该用户的信息
        User user = userService.getOne(queryWrapper);
        //将密码和数据库中的进行对比
        if(user.getPassword().equals(password)){
            // 将当前用户信息保存到session中
            session.setAttribute("username",username);
            return R.success(user);
        }else{
            return R.error(("登录失败，密码错误"));
        }
    }
}
