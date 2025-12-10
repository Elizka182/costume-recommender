package com.yelyzaveta.costume_recommender_be;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yelyzaveta.costume_recommender_be.entity.*;
import com.yelyzaveta.costume_recommender_be.model.ImageRequest;
import com.yelyzaveta.costume_recommender_be.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecommenderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AgeRepository ageRepository;
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private GroupTypeRepository groupTypeRepository;
    @Autowired
    private ScareLevelRepository scareLevelRepository;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private TimePrepRepository timePrepRepository;

    @Test
    void fullFlow_uploadImages_createCostumes_and_search() throws Exception {
        init();

        // --- Загрузка первой картинки ---
        MockMultipartFile file1 = new MockMultipartFile(
                "file",
                "image1.jpg",
                "image/jpeg",
                "fake-image-1".getBytes()
        );

        mockMvc.perform(multipart("/costume/uploadImage")
                        .file(file1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.filename").exists());

        String image1Filename = mockMvc.perform(multipart("/costume/uploadImage")
                        .file(file1))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replaceAll(".*\"filename\":\"([^\"]+)\".*", "$1");

        // --- Находим первую картинку по url ---
        mockMvc.perform(post("/costume/findImage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"imageurl\":\"" + image1Filename + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/jpeg"));

        // --- Создаем первый костюм ---
        String costume1Json = """
                {
                  "name": "Costume 1",
                  "description": "First costume",
                    "age":"child",
                        "minTemperature": -10,
                        "maxTemperature": 22,
                        "budget": "medium",
                        "colors": ["black"],
                        "gender": "feminine",
                        "groupType": "solo",
                        "scaryLevel": "sexy",
                        "themes": ["movie, cartoon, game characters"],
                        "timeToPrep": "quick",
                        "makeUp": "light",
                        "mask": "no",
                  "imageurl": "%s"
                }
                """.formatted(image1Filename);

        mockMvc.perform(post("/costume/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(costume1Json))
                .andExpect(status().isOk());

        // --- Загрузка второй картинки ---
        MockMultipartFile file2 = new MockMultipartFile(
                "file",
                "image2.jpg",
                "image/jpeg",
                "fake-image-2".getBytes()
        );

        String image2Filename = mockMvc.perform(multipart("/costume/uploadImage")
                        .file(file2))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replaceAll(".*\"filename\":\"([^\"]+)\".*", "$1");

        // --- Создаем второй костюм ---
        String costume2Json = """
                {
                  "name": "Costume 2",
                  "description": "Second costume",
                  "age":"teen",
                        "minTemperature": 5,
                        "maxTemperature": 30,
                        "budget": "low",
                        "colors": ["red"],
                        "gender": "masculine",
                        "groupType": "couple",
                        "scaryLevel": "spooky",
                        "themes": ["movie, cartoon, game characters"],
                        "timeToPrep": "quick",
                        "makeUp": "no",
                        "mask": "yes",
                  "imageurl": "%s"
                }
                """.formatted(image2Filename);

        mockMvc.perform(post("/costume/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(costume2Json))
                .andExpect(status().isOk());

        // --- Первый поиск (должен найти первый костюм) ---
        String search1Json = """
                {
                  "scaryLevel": "sexy",
                          "budget": "medium",
                          "themes": [
                              "movie, cartoon, game characters"
                          ],
                          "gender": "feminine",
                          "timeToPrep": "quick",
                          "makeUp": "light",
                          "mask": "no",
                          "groupType": "solo",
                          "colors": [
                              "black"
                          ],
                          "city": "Yakutsk",
                          "country": "Russia"
                }
                """;

        mockMvc.perform(post("/costume/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(search1Json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Costume 1"));

        // --- Второй поиск (должен найти второй костюм) ---
        String search2Json = """
                {
                 "scaryLevel": "spooky",
                          "budget": "low",
                          "themes": [
                              "movie, cartoon, game characters"
                          ],
                          "gender": "masculine",
                          "timeToPrep": "quick",
                          "makeUp": "no",
                          "mask": "yes",
                          "groupType": "couple",
                          "colors": [
                              "red"
                          ],
                          "city": "Phalaborwa",
                          "country": "South Africa"
                }
                """;

        mockMvc.perform(post("/costume/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(search2Json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Costume 2"));
    }

    @Test
    void findImage_fileDoesNotExist_returns404() throws Exception {
        ImageRequest request = new ImageRequest("this-file-does-not-exist.jpg");

        mockMvc.perform(post("/costume/findImage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void search_onlyMandatoryParams_returns404() throws Exception {
        String searchJson = """
            {
                "scaryLevel": "spooky",
                "budget": "medium",
                "timeToPrep": "quick",
                "makeUp": "light",
                "mask": "no",
                "city": "Yakutsk",
                "country": "Russia",
                "themes": [],
                "colors":[]
            }
            """;

        mockMvc.perform(post("/costume/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(searchJson))
                .andExpect(status().isNotFound());
    }

    private void init() {
        Age age = new Age();
        age.setName("child");
        age.setYearsFrom(0);
        age.setYearsTo(12);
        ageRepository.save(age);
        Age age2 = new Age();
        age2.setName("teen");
        age2.setYearsFrom(13);
        age2.setYearsTo(17);
        ageRepository.save(age2);


        Budget budget = new Budget();
        budget.setName("medium");
        budgetRepository.save(budget);
        Budget budget2 = new Budget();
        budget2.setName("low");
        budgetRepository.save(budget2);

        GroupType groupType = new GroupType();
        groupType.setName("couple");
        groupTypeRepository.save(groupType);
        GroupType groupType2 = new GroupType();
        groupType2.setName("solo");
        groupTypeRepository.save(groupType2);

        ScareLevel scareLevel = new ScareLevel();
        scareLevel.setName("sexy");
        scareLevelRepository.save(scareLevel);
        ScareLevel scareLevel2 = new ScareLevel();
        scareLevel2.setName("spooky");
        scareLevelRepository.save(scareLevel2);

        TimePrep timePrep = new TimePrep();
        timePrep.setName("quick");
        timePrepRepository.save(timePrep);

        Gender gender = new Gender();
        gender.setName("masculine");
        genderRepository.save(gender);
        Gender gender2 = new Gender();
        gender2.setName("feminine");
        genderRepository.save(gender2);

        Color color = new Color();
        color.setName("red");
        colorRepository.save(color);
        Color color2 = new Color();
        color2.setName("black");
        colorRepository.save(color2);

        Theme theme = new Theme();
        theme.setName("movie, cartoon, game characters");
        themeRepository.save(theme);
    }

}
