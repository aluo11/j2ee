package com.j2ee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2ee.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
