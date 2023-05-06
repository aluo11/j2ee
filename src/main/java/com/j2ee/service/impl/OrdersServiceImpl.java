package com.j2ee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2ee.entity.Orders;
import com.j2ee.mapper.OrdersMapper;
import com.j2ee.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
