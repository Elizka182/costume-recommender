package com.yelyzaveta.costume_recommender_be.model;

import lombok.Data;

import java.util.List;

/**
 * DTO class representing parameters used to describe a costume.
 * Typically used for filtering or creating costumes in the application.
 */
@Data
public class CostumeParams {

    private String scaryLevel;
    private String budget;
    private List<String> themes;
    private String gender;
    private String timeToPrep;
    private String makeUp;
    private String mask;
    private String groupType;
    private List<String> colors;
}
