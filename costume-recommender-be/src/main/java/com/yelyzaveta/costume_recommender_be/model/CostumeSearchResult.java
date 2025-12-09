package com.yelyzaveta.costume_recommender_be.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO representing a single costume search result.
 * Contains the main details needed to display a costume.
 */
@Data
@AllArgsConstructor
public class CostumeSearchResult  {
    private String name;
    private String description;
    private String imageurl;

}