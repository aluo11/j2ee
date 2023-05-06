package com.j2ee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2ee.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
