package com.yelyzaveta.costume_recommender_be.repository;

import com.yelyzaveta.costume_recommender_be.entity.ScareLevel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on {@link ScareLevel} entities.
 */
public interface ScareLevelRepository extends JpaRepository<ScareLevel, Integer> {

    /**
     * Finds a ScareLevel entity by its name.
     *
     * @param name the name of the scare level
     * @return the {@link ScareLevel} entity with the specified name, or null if not found
     */
    ScareLevel findByName(String name);
}
