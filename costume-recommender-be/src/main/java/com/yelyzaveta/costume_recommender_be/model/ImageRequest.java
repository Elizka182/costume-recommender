package com.yelyzaveta.costume_recommender_be.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for sending an image request.
 * Contains the URL of the image to be processed or retrieved.
 */
@Data
@AllArgsConstructor
public class ImageRequest {
    private String imageurl;
}
