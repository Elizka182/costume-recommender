package com.yelyzaveta.costume_recommender_be.repository;

import com.yelyzaveta.costume_recommender_be.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on {@link Gender} entities.
 */
public interface GenderRepository extends JpaRepository<Gender, Integer> {

    /**
     * Finds a Gender entity by its name.
     *
     * @param name the name of the gender
     * @return the {@link Gender} entity with the specified name, or null if not found
     */
    Gender findByName(String name);
}
