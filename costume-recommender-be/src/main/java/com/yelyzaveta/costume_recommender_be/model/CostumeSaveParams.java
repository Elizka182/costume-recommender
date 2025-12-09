package com.yelyzaveta.costume_recommender_be.model;

import lombok.Data;

/**
 * DTO class representing the full set of parameters needed to save a costume.
 * Extends {@link CostumeParams} with additional fields for saving to the database.
 */
@Data
public class CostumeSaveParams extends CostumeParams{
    private String imageurl; //response from the "upload" request

    private String name;
    private String description;
    private String age;
    private int minTemperature;
    private int maxTemperature;
}
