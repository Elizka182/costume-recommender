package com.yelyzaveta.costume_recommender_be.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity that represents the time required to prepare a costume.
 * Used to describe how much effort or time is needed for preparation.
 */
@Data
@Entity
public class TimePrep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
