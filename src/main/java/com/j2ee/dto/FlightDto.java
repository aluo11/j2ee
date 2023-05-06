package com.j2ee.dto;

import com.j2ee.entity.Flight;
import lombok.Data;

@Data
public class FlightDto extends Flight {
    private String passengerId;
    private String siteNum;
}
