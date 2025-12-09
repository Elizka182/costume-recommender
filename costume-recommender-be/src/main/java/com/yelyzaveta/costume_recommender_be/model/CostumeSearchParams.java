package com.yelyzaveta.costume_recommender_be.model;

import lombok.Data;

/**
 * DTO class representing the parameters used to search for costumes.
 * Extends {@link CostumeParams} with additional fields for filtering based on age, location, and temperature.
 */
@Data
public class CostumeSearchParams extends CostumeParams {

    private Integer age;
    private String city;
    private String country;
    private int minTemperature;
    private int maxTemperature;
}
