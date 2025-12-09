package com.yelyzaveta.costume_recommender_be.serveses;

import com.yelyzaveta.costume_recommender_be.entity.*;
import com.yelyzaveta.costume_recommender_be.model.CostumeSaveParams;
import com.yelyzaveta.costume_recommender_be.model.CostumeSearchParams;
import com.yelyzaveta.costume_recommender_be.model.CostumeSearchResult;
import com.yelyzaveta.costume_recommender_be.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for managing costume operations, including
 * searching costumes by weather and filters, creating new costumes,
 * and calculating match score
 */
@Service
@RequiredArgsConstructor
public class CostumeService {
    private final CostumeSearchRepository searchRepository;
    private final CostumeRepository costumeRepository;
    private final WeatherService weatherService;
    private final GenderRepository genderRepository;
    private final BudgetRepository budgetRepository;
    private final ScareLevelRepository scareLevelRepository;
    private final TimePrepRepository timePrepRepository;
    private final GroupTypeRepository groupTypeRepository;
    private final ThemeRepository themeRepository;
    private final ColorRepository colorRepository;
    private final AgeRepository ageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Searches for a costume based on weather and additional filters
     *
     * @param params the search parameters including age, city, country, and costume filters
     * @return a {@link CostumeSearchResult} representing the best matched costume, or null if not found
     */
    public CostumeSearchResult searchCostumeByWeather(CostumeSearchParams params) {
        int temperature = 0;
        if (params.getCity() != null && params.getCountry() != null) {
            temperature = weatherService.getCurrentTemperature(params.getCity(), params.getCountry());
        }
        return findBestMatchByScore(params, temperature);
    }


    /**
     * Finds the best matching costume from the search results by calculating a score.
     *
     * @param params the search parameters including filters
     * @param temperature the current temperature to consider
     * @return a {@link CostumeSearchResult} of the best match costume
     */
    public CostumeSearchResult findBestMatchByScore(CostumeSearchParams params, int temperature) {
        String ageCategoryName;
        if (params.getAge() != null) {
            Age ageCategory = getAgeByValue(params.getAge());
            ageCategoryName = ageCategory != null ? ageCategory.getName() : null;
        } else {
            ageCategoryName = null;
        }

        boolean tooCold = false;
        boolean tooHot = false;

        if (temperature < -10) {
            tooCold = true;
            temperature = -10;
        } else if (temperature > 30) {
            tooHot = true;
            temperature = 30;
        }

        List<Costume> filteredCostumes = searchRepository.searchCostumes(temperature, ageCategoryName, params);

        List<CostumeScore> scoredCostumes = filteredCostumes.stream()
                .map(costume -> new CostumeScore(costume, calculateMatchScore(costume, params, ageCategoryName)))
                .filter(cs -> cs.score > 0)
                .sorted(Comparator.comparingInt(a -> a.score))
                .toList();

        if (scoredCostumes.isEmpty()) {
            return null;
        }

        Costume bestMatch = scoredCostumes.get(0).costume;

        String descriptionPrefix = "";
        if (tooCold) {
            descriptionPrefix = "❄️ Unfortunately, we don't have a costume perfectly suited for this very cold weather. Layer this outfit with a warm jacket and tights. Your health comes first! ✨\n\n";
        } else if (tooHot) {
            descriptionPrefix = "☀️ It's quite hot! Choose lighter options and don't forget to stay hydrated. ✨\n\n";
        }

        return new CostumeSearchResult(
                bestMatch.getName(),
                descriptionPrefix + bestMatch.getDescription(),
                bestMatch.getImageurl()
        );
    }

    /**
     * Creates a new {@link Costume} entity from the provided save parameters.
     *
     * @param params the costume save parameters including name, description, age, themes, and colors
     * @return a new {@link Costume} entity
     */
    public Costume createCostumeFromRequest(CostumeSaveParams params) {
        Costume costume = new Costume();

        // Set basic fields
        costume.setName(params.getName());
        costume.setDescription(params.getDescription());
        costume.setImageurl(params.getImageurl());
        costume.setMakeUp(params.getMakeUp());
        costume.setMask(params.getMask());
        costume.setMinTemperature(params.getMinTemperature());
        costume.setMaxTemperature(params.getMaxTemperature());

        // Set relationships
        costume.setScareLevel(getScaryLevel(params.getScaryLevel()));
        costume.setBudget(getBudget(params.getBudget()));
        costume.setGender(getGender(params.getGender()));
        costume.setAge(getAgeByName(params.getAge()));
        costume.setTimePrep(getPrepTime(params.getTimeToPrep()));
        costume.setGroupType(getGroupType(params.getGroupType()));

        // Assign themes and colors
        assignThemes(costume, params.getThemes());
        assignColors(costume, params.getColors());

        return costume;
    }

    public ScareLevel getScaryLevel(String name) {
        return scareLevelRepository.findByName(name);
    }

    public Budget getBudget(String name) {
        return budgetRepository.findByName(name);
    }

    public Gender getGender(String name) {
        return genderRepository.findByName(name);
    }

    public TimePrep getPrepTime(String name) {
        return timePrepRepository.findByName(name);
    }

    public GroupType getGroupType(String name) {
        return groupTypeRepository.findByName(name);
    }

    public void assignThemes(Costume costume, List<String> themeNames) {
        for (String name : themeNames) {
            Theme theme = themeRepository.findByName(name);
            costume.getThemes().add(theme);
        }
    }

    public void assignColors(Costume costume, List<String> colorNames) {
        for (String name : colorNames) {
            Color color = colorRepository.findByName(name);
            costume.getColors().add(color);
        }
    }

    /**
     * Finds an {@link Age} entity by its numeric value.
     *
     * @param value the age in years
     * @return the matching {@link Age} entity, or null if not found
     */
    public Age getAgeByValue(int value) {
        return ageRepository.findByYearsFromLessThanEqualAndYearsToGreaterThanEqual(value, value);
    }

    /**
     * Finds an {@link Age} entity by its name.
     *
     * @param name the age category name
     * @return the matching {@link Age} entity, or null if not found
     */
    public Age getAgeByName(String name) {
        return ageRepository.findByName(name);
    }

    /**
     * Saves a costume to the database using the provided parameters.
     *
     * @param params the costume save parameters
     * @return a map with save status information
     */
    public Map<String, String> saveFromParams(CostumeSaveParams params) {
        Map<String, String> response = new HashMap<>();

        String imageurl = params.getImageurl();
        if (imageurl != null && !imageurl.isEmpty()) {
            String[] parts = imageurl.split("/");
            params.setImageurl(parts[parts.length - 1]);
        }

        Costume costume = createCostumeFromRequest(params);
        costumeRepository.save(costume);

        return response;
    }

    private int calculateMatchScore(Costume costume, CostumeSearchParams params, String ageCategory) {
        int score = 0;

        if (params.getScaryLevel() != null &&
                costume.getScareLevel().getName().equalsIgnoreCase(params.getScaryLevel())) {
            score += 10;
        }

        if (params.getBudget() != null &&
                costume.getBudget().getName().equalsIgnoreCase(params.getBudget())) {
            score += 10;
        }

        if (params.getGender() != null &&
                !params.getGender().equalsIgnoreCase("does not matter") &&
                costume.getGender().getName().equalsIgnoreCase(params.getGender())) {
            score += 8;
        }

        if (costume.getAge().getName().equalsIgnoreCase(ageCategory)) {
            score += 8;
        }

        if (params.getTimeToPrep() != null &&
                costume.getTimePrep().getName().equalsIgnoreCase(params.getTimeToPrep())) {
            score += 7;
        }

        if (params.getMakeUp() != null &&
                costume.getMakeUp().equalsIgnoreCase(params.getMakeUp())) {
            score += 5;
        }

        if (params.getMask() != null &&
                !params.getMask().equalsIgnoreCase("does not matter") &&
                costume.getMask().equalsIgnoreCase(params.getMask())) {
            score += 5;
        }

        if (params.getGroupType() != null &&
                costume.getGroupType().getName().equalsIgnoreCase(params.getGroupType())) {
            score += 7;
        }

        if (params.getThemes() != null && !params.getThemes().isEmpty()) {
            long matchingThemes = costume.getThemes().stream()
                    .filter(theme -> params.getThemes().stream()
                            .anyMatch(requestTheme -> requestTheme.equalsIgnoreCase(theme.getName())))
                    .count();
            score += (int) (matchingThemes * 5);
        }

        if (params.getColors() != null && !params.getColors().isEmpty()) {
            long matchingColors = costume.getColors().stream()
                    .filter(color -> params.getColors().stream()
                            .anyMatch(requestColor -> requestColor.equalsIgnoreCase(color.getName())))
                    .count();
            score += (int) (matchingColors * 3);
        }

        return score;
    }

    private static class CostumeScore {
        Costume costume;
        int score;

        CostumeScore(Costume costume, int score) {
            this.costume = costume;
            this.score = score;
        }
    }

}
