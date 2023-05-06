package com.j2ee;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class testStringToLocalDateTime {
    public static void main(String[] args) {
        // String 转换为 LocalDateTime
        String dateStr = "2021-09-03 21:36:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedDate = LocalDateTime.parse(dateStr, formatter);
        System.out.println(parsedDate);

    }
}
