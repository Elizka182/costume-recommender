package com.yelyzaveta.costume_recommender_be.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity that represents a group type for a costume.
 * Examples: solo, couple, group, family-friendly.
 */
@Data
@Entity
public class GroupType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
