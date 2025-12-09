package com.yelyzaveta.costume_recommender_be.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO for receiving weather data from an external API.
 * Contains the current weather information.
 */
@Data
public class WeatherResponse {
    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;

    /**
    * Inner class representing current weather information.
    */
    @Data
    public static class CurrentWeather {
        private Double temperature;
    }
}
