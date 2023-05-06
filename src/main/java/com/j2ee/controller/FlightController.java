package com.j2ee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.j2ee.common.R;
import com.j2ee.entity.Flight;
import com.j2ee.dto.FlightDto;
import com.j2ee.entity.Orders;
import com.j2ee.service.FlightService;
import com.j2ee.service.OrdersService;
import com.j2ee.utils.RandomId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/flight")
@Slf4j
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private OrdersService ordersService;

    /**
     * 查询航空公司各个航班飞机的承载情况
     * @param name
     * @return
     */
    @PostMapping("/list")
    public R<List<Flight>> check(String name){
        // 查询该航空公司的航班
        LambdaQueryWrapper<Flight> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Flight::getCompany,name);

        List<Flight> list = flightService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 根据旅行时间和地点为乘客安排合适的航班
     * @param travelTime
     * @param provenance
     * @param destination
     * @return
     */
    @PostMapping("/arrange")
    public R<List<Flight>>  arrange(String travelTime, String provenance, String destination, String passengerId, HttpSession session){
        // 保存旅客身份证id到session中
        session.setAttribute("passengerId",passengerId);

        LambdaQueryWrapper<Flight> queryWrapper = new LambdaQueryWrapper<>();
        // 筛选出行时间在登机和下机之间的航班
        queryWrapper.lt(Flight::getBoardingTime,travelTime);
        queryWrapper.gt(Flight::getArriveTime,travelTime);
        // 筛选始发地和目的地
        queryWrapper.eq(Flight::getProvenance,provenance);
        queryWrapper.eq(Flight::getDestination,destination);

        // 将符合条件的航班封装到map中响应数据
        List<Flight> list = flightService.list(queryWrapper);

        return R.success(list);
    }

    /**
     * 为用户选择的航班创建订单
     * @param flightId
     * @param session
     * @return
     */
    @PostMapping("/choose")
    public R<Orders> choose(String flightId, HttpSession session){
        // 从session中得到用户名和旅客身份证id
        String username = (String) session.getAttribute("username");
        String passengerId = (String) session.getAttribute("passengerId");
        String id = RandomId.getRanmdomId();
        Orders order = new Orders(id, username, passengerId, flightId);
        log.info("订单信息:" + order);

        ordersService.save(order);
        return R.success(order);
    }

    /**
     * 核对旅客的取票通知打印机票
     * @param ordersId
     * @return
     */
    @PostMapping("/print")
    public R<FlightDto> print(String ordersId, HttpSession session){
        // 从订单中拿到航次id
        Orders orders = ordersService.getById(ordersId);
        String flightId = orders.getFlightId();

        // 根据航次id拿到航次信息
        Flight flight = flightService.getById(flightId);
        FlightDto flightDto = new FlightDto();


        //String passengerId = (String) session.getAttribute("passengerId");
        String  passengerId = "1";


        // 设置旅客身份证id
        flightDto.setPassengerId(passengerId);
        // 航班id
        flightDto.setId(flight.getId());
        // 航空公司
        flightDto.setCompany(flight.getCompany());
        // 登机时间
        flightDto.setBoardingTime(flight.getBoardingTime());
        // 到达时间
        flightDto.setArriveTime(flight.getArriveTime());
        // 始发地
        flightDto.setProvenance(flight.getProvenance());
        // 目的地
        flightDto.setDestination(flight.getDestination());
        // 座位号
        flightDto.setSiteNum(RandomId.getRandomSiteNum());
        // 机票金额
        flightDto.setMoney(flight.getMoney());

        return R.success(flightDto);
    }
}
