package com.yelyzaveta.costume_recommender_be.serveses;

import com.yelyzaveta.costume_recommender_be.model.GeocodeResponse;
import com.yelyzaveta.costume_recommender_be.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

/**
 * Service for retrieving current weather information for a given city and country.
 * Uses geocoding to get latitude/longitude and then queries the Open-Meteo API for temperature.
 */
@Service
@RequiredArgsConstructor
public class WeatherService {

        private final RestTemplate restTemplate;

        /**
        * Gets the current temperature (°C) for the specified city and country.
        *
        * @param city the name of the city
        * @param country the name of the country
        * @return the current temperature in Celsius
        * @throws RuntimeException if the country code, city, or weather data cannot be found
        */
        public int getCurrentTemperature(String city, String country) {
            String countryCode = getCountryCode(country);
            if (countryCode == null) {
                throw new RuntimeException("Country code not found for: " + country);
            }

            String geocodeUrl = String.format(
                    "https://geocoding-api.open-meteo.com/v1/search?name=%s&countryCode=%s",
                    city, countryCode
            );

            GeocodeResponse geoResponse = restTemplate.getForObject(geocodeUrl, GeocodeResponse.class);

            String weatherUrl = getWeatherURL(city, geoResponse);

            WeatherResponse weatherResponse = restTemplate.getForObject(weatherUrl, WeatherResponse.class);

            if (weatherResponse == null || weatherResponse.getCurrentWeather() == null) {
                throw new RuntimeException("Weather data not available");
            }

            double temperature = weatherResponse.getCurrentWeather().getTemperature();
            return (int) Math.round(temperature);
        }

    private static String getWeatherURL(String city, GeocodeResponse geoResponse) {
        if (geoResponse == null || geoResponse.getResults() == null || geoResponse.getResults().isEmpty()) {
            throw new RuntimeException("City not found: " + city);
        }

        GeocodeResponse.GeocodeResult firstResult = geoResponse.getResults().get(0);
        double latitude = firstResult.getLatitude();
        double longitude = firstResult.getLongitude();

        String weatherUrl = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true",
                latitude, longitude
        );
        return weatherUrl;
    }

    private String getCountryCode(String countryName) {
            for (String iso : Locale.getISOCountries()) {
                Locale locale = new Locale("", iso);
                if (locale.getDisplayCountry(Locale.ENGLISH).equalsIgnoreCase(countryName)) {
                    return iso;
                }
            }
            return null;
        }
}
