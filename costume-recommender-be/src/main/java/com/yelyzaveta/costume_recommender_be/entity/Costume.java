package com.yelyzaveta.costume_recommender_be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity that represents a Halloween costume with all its attributes.
 * Includes properties like name, description, image, temperature range,
 * makeup, mask, and relations to other entities (e.g., age, budget, themes, colors).
 */
@Data
@Entity
public class Costume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String imageurl;
    private int minTemperature;
    private int maxTemperature;
    private String makeUp;
    private String mask;

    @ManyToOne
    @JoinColumn(name = "scareLevel_id", referencedColumnName = "id")
    private ScareLevel scareLevel;

    @ManyToOne
    @JoinColumn(name = "age_id", referencedColumnName = "id")
    private Age age;

    @ManyToOne
    @JoinColumn(name = "budget_id", referencedColumnName = "id")
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private GroupType groupType;

    @ManyToOne
    @JoinColumn(name = "timePrep_id", referencedColumnName = "id")
    private TimePrep timePrep;

    @ManyToMany
    @JoinTable(
            name = "costume_theme",
            joinColumns = @JoinColumn(name = "costume_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private Set<Theme> themes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "costume_color",
            joinColumns = @JoinColumn(name = "costume_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private Set<Color> colors = new HashSet<>();
}
