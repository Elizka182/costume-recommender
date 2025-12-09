package com.yelyzaveta.costume_recommender_be.repository;

import com.yelyzaveta.costume_recommender_be.entity.TimePrep;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on {@link TimePrep} entities.
 */
public interface TimePrepRepository extends JpaRepository<TimePrep, Integer> {

    /**
     * Finds a TimePrep entity by its name.
     *
     * @param name the name of the time preparation level
     * @return the {@link TimePrep} entity with the specified name, or null if not found
     */
    TimePrep findByName(String name);
}
