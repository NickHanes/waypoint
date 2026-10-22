package com.nick.waypoint.service;

import com.nick.waypoint.model.GroqResponse;
import com.nick.waypoint.model.TripRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;


@Service
public class TripService {

    @Value("${groq.api.key}")
    private String groqApiKey;


    private final WebClient webClient;

    public String planTrip(TripRequest tripRequest) {

        String requestBody = "The user is planning a trip from " + tripRequest.getOrigin() + " to " + tripRequest.getFinalDestination()
                + " for " + tripRequest.getTripLength() + " days."
                + "\n" + "They will be going with " + tripRequest.getGroupSize() + " people."
                + "\n" + "They want the trip to be at the pace of " + tripRequest.getPace()
                + "\n" + "They want the trip to cost no more than " + tripRequest.getBudget() + " dollars for N people, N being the number of people in the group, not a total number for the whole group."
                + "\n" + "Their interests are: " + tripRequest.getInterests()
                + "\n" + "They want to stay in " + tripRequest.getLodging()
                + "\n" + "They want to rent a car: " + tripRequest.isRentalCar()
                + "\n" + "If yes, please include the cost of the car rental."
                + "\n" + "They want to travel by " + tripRequest.getModeOfTransport()
                + "\n" + "If plane, please include the cost of the plane tickets."
                + "\n" + "If car, please generate stops and distances between stops. Plus prices for gas, tolls, and parking. Optimize the route to be the most cost effective."
                + "\n" + "Please make an itinerary for the user that includes the pricing for each day of the trip."
                + "\n" + "Please include the total cost of the trip."
                + "\n" + "Make sure the trip has all sites that are must-sees and hidden gems based off other reviews and surrounding places."
                + "\n" + "Format the itinerary to include morning, afternoon and evening sections."
                + "\n" + "Please make sure that each days driving is realistic and does not backtrack, convert to miles and average time to get to each stop."
                + "\n" + "Use real, existing hotel names and keep generic if needed.";


        Map<String, Object> body = Map.of(
                "model","openai/gpt-oss-120b",
                "messages", List.of(
                        Map.of("role", "user", "content", requestBody)
                ),
                "max_completion_tokens", 8000
        );

        return callGroq(body).getChoices().get(0).getMessage().getContent();

    }

    public TripService(WebClient webClient) {
        this.webClient = webClient;
    }

    public GroqResponse callGroq(Map<String, Object> body) {
        String url = "https://api.groq.com/openai/v1/chat/completions";

        return

                this.webClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + groqApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(GroqResponse.class)
                .block();
    }
}