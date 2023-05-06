package com.j2ee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.j2ee.common.R;
import com.j2ee.entity.Flight;
import com.j2ee.dto.FlightDto;
import com.j2ee.entity.Notation;
import com.j2ee.entity.Orders;
import com.j2ee.entity.User;
import com.j2ee.service.FlightService;
import com.j2ee.service.OrdersService;
import com.j2ee.service.UserService;
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

    @Autowired
    private UserService userService;

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

        // 将旅客身份证id添加到用户表中
        LambdaQueryWrapper<User> queryWrapperUser = new LambdaQueryWrapper<>();
        String username = (String) session.getAttribute("username");
        queryWrapperUser.eq(User::getUsername,username);
        User user = userService.getOne(queryWrapperUser);
        user.setPassengerId(passengerId);
        userService.updateById(user);

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

        // 随机生成订单编号
        String id = RandomId.getRanmdomId();
        // 将订单编号存在seesion中，方便后续显示取票通知账单
        session.setAttribute("orderId",id);
        Orders order = new Orders(id, username, passengerId, flightId);
        log.info("订单信息:" + order);

        ordersService.save(order);
        return R.success(order);
    }

    /**
     * 用户选择航次后显示取票通知和账单,"请于登机前一天凭取票通知和账单交款取票"
     * @param flightId
     * @return
     */
    @PostMapping("/notation")
    public R<Notation> notation(String flightId){

        // 通过航次编号拿到订单编号，作为打印机票时核对的信息
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getFlightId,flightId);
        Orders orders = ordersService.getOne(queryWrapper);
        // 订单编号
        String ordersId = orders.getId();
        // 旅客身份证id
        String passengerId = orders.getPassengerId();


        // 拿到航班信息
        Flight flight = flightService.getById(flightId);

        Notation notation = new Notation();
        // 订单编号
        notation.setOrders_id(ordersId);
        // 旅客身份证id
        notation.setPassengerId(passengerId);
        // 登机时间
        notation.setBoardingTime(flight.getBoardingTime());
        // 金额
        notation.setMoney(flight.getMoney());

        return R.success(notation);
    }

    /**
     * 核对旅客的取票通知(订单号) + 身份证号
     * @param ordersId
     * @return
     */
    @PostMapping("/check")
    public R<FlightDto> check(String ordersId, String passengerId){
        // 从数据库查询有无该订单id
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getId,ordersId);
        Orders orders = ordersService.getOne(queryWrapper);

        // 当核对取票通知的订单号和旅客身份证id一致时，调用打印机票方法
        if(orders != null && orders.getPassengerId().equals(passengerId)){
            return print(ordersId);
        }else {
            return R.error("信息有误，核对失败");
        }
    }

    /**
     * 核对旅客的取票通知(订单号) + 身份证号，打印机票
     * @param ordersId
     * @return
     */
    @PostMapping("/print")
    public R<FlightDto> print(String ordersId){
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
