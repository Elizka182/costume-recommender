package com.yelyzaveta.costume_recommender_be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Entity representing an age group for costumes.
 * Defines the range of ages for which a costume is suitable.
 */
@Data
@Entity
public class Age {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private int yearsFrom;
    private int yearsTo;
}
