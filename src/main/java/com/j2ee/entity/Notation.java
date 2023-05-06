package com.j2ee.entity;

public class Notation {
    // 登机时间
    private String boardingTime;

    // 旅客身份证id
    private String passengerId;

    // 机票金额
    private Integer money;

    // 订单编号
    private String orders_id;

    public Notation() {
    }

    public String getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(String boardingTime) {
        this.boardingTime = boardingTime;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }
}
