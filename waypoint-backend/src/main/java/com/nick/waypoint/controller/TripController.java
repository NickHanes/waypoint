package com.nick.waypoint.controller;

import com.nick.waypoint.model.TripRequest;
import com.nick.waypoint.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/trip")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/tripRequest")
    public String trip(@RequestBody TripRequest tripRequest) {

        return tripService.planTrip(tripRequest);
    }
}
