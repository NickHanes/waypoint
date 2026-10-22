package com.nick.waypoint.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.*;

@Data
public class TripRequest {
    private int tripLength;
    private float budget;
    private String pace;
    private int groupSize;
    private List<String> interests;
    private String origin;
    private String finalDestination;
    private String lodging;
    private boolean rentalCar;
    private String modeOfTransport;
}
