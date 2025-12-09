package com.yelyzaveta.costume_recommender_be.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * DTO representing the response from a geocoding service.
 * Contains a list of geocoding results with latitude, longitude, and country code.
 */
@Data
public class GeocodeResponse {
    private List<GeocodeResult> results;

    @Data
    public static class GeocodeResult {
        private Double latitude;
        private Double longitude;

        @JsonProperty("country_code")
        private String countryCode;
    }
}
