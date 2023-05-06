package com.j2ee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2ee.entity.Flight;
import com.j2ee.mapper.FlightMapper;
import com.j2ee.service.FlightService;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl extends ServiceImpl<FlightMapper, Flight> implements FlightService {
}
