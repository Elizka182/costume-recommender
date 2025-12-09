package com.yelyzaveta.costume_recommender_be.repository;

import com.yelyzaveta.costume_recommender_be.entity.Age;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Age} entity.
 * Provides methods for CRUD operations and custom queries by age range or age name.
*/
 public interface AgeRepository extends JpaRepository<Age, Integer> {

    /**
     * Finds an Age entity where the given age falls within the yearsFrom and yearsTo range.
     *
     * @param yearsFrom the minimum age to compare (inclusive)
     * @param yearsTo the maximum age to compare (inclusive)
     * @return the matching Age entity
     */
    Age findByYearsFromLessThanEqualAndYearsToGreaterThanEqual(int yearsFrom, int yearsTo);

    /**
     * Finds an Age entity by its name.
    *
    * @param name the name of the age group
    * @return the matching Age entity
    */
    Age findByName(String name);
}
