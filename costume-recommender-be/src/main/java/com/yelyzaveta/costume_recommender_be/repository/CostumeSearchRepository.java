package com.yelyzaveta.costume_recommender_be.repository;

import com.yelyzaveta.costume_recommender_be.entity.Color;
import com.yelyzaveta.costume_recommender_be.entity.Costume;
import com.yelyzaveta.costume_recommender_be.entity.Theme;
import com.yelyzaveta.costume_recommender_be.model.CostumeSearchParams;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom repository for performing complex costume searches
 * based on multiple criteria like temperature, age, themes, colors, and other attributes.
 */
@Component
public class CostumeSearchRepository {
    private static final String FILTER_ALL = "all";
    @PersistenceContext
    private EntityManager entityManager;

    /**
    * Searches for costumes that match the given criteria.
    *
    * @param temperature the current temperature to filter costumes suitable for it
    * @param ageName the age group name to filter costumes for a specific age
    * @param params additional search parameters like budget, themes, colors, gender, etc.
    * @return a list of {@link Costume} entities matching the search criteria
    */
    public List<Costume> searchCostumes(int temperature, String ageName, CostumeSearchParams params) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Costume> query = cb.createQuery(Costume.class);
        Root<Costume> costume = query.from(Costume.class);

        Join<Costume, Theme> themeJoin = costume.join("themes");
        Join<Costume, Color> colorJoin = costume.join("colors");

        Predicate minTempPredicate = cb.lessThanOrEqualTo(costume.get("minTemperature"), temperature);
        Predicate maxTempPredicate = cb.greaterThanOrEqualTo(costume.get("maxTemperature"), temperature);

        List<Predicate> orPredicates = new ArrayList<>();

        if (ageName != null) {
            orPredicates.add(cb.equal(costume.get("age").get("name"), ageName));
        }
        orPredicates.add(cb.equal(costume.get("budget").get("name"), params.getBudget()));
        orPredicates.add(cb.equal(costume.get("scareLevel").get("name"), params.getScaryLevel()));
        orPredicates.add(cb.equal(costume.get("timePrep").get("name"), params.getTimeToPrep()));
        orPredicates.add(cb.equal(costume.get("groupType").get("name"), params.getGroupType()));
        if (FILTER_ALL.equals(params.getGender())) {
            orPredicates.add(cb.equal(costume.get("gender").get("name"), params.getGender()));
        }

        orPredicates.add(themeJoin.get("name").in(params.getThemes()));
        orPredicates.add(colorJoin.get("name").in(params.getColors()));

        Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[0]));

        Predicate finalPredicate = cb.and(minTempPredicate, maxTempPredicate, orPredicate);

        query.select(costume)
                .distinct(true)
                .where(finalPredicate);

        return entityManager.createQuery(query).getResultList();
    }
}
