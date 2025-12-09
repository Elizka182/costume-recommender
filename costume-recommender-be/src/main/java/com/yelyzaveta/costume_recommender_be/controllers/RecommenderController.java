package com.yelyzaveta.costume_recommender_be.controllers;


import com.yelyzaveta.costume_recommender_be.model.CostumeSaveParams;

import com.yelyzaveta.costume_recommender_be.model.CostumeSearchParams;
import com.yelyzaveta.costume_recommender_be.model.CostumeSearchResult;
import com.yelyzaveta.costume_recommender_be.model.ImageRequest;
import com.yelyzaveta.costume_recommender_be.serveses.CostumeService;
import com.yelyzaveta.costume_recommender_be.serveses.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(path = "/costume")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") //allow requests from frontend
public class RecommenderController {

    private final CostumeService costumeService;
    private final FileService fileService;

    /**
    * Returns an image file by its filename.
    * Used to display the costume image on the frontend.
    * @param request contains the image filename
    * @return image as Resource or 404 if not found
    */
    @PostMapping("findImage")
    public ResponseEntity<Resource> findImage(@RequestBody ImageRequest request) throws IOException {
        Resource resource = fileService.findImageByFilename(request.getImageurl());
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        String contentType = fileService.getContentType(resource);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    /**
     * Searches for a costume based on user-selected parameters.
     *
     * @param params search filters from the user
     * @return matched costume or 404 if no results found
     */
    @PostMapping("/search")
    public ResponseEntity<CostumeSearchResult> search(@RequestBody CostumeSearchParams params) {
        CostumeSearchResult costumeSearchResult = costumeService.searchCostumeByWeather(params);
        if (costumeSearchResult == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(costumeSearchResult);
    }

    /**
     * Uploads a costume image to the server.
     *
     * @param file image file sent from the frontend
     * @return filename of the uploaded image or error message
     */
    @PostMapping(value = "/uploadImage")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Map<String, String> result = fileService.uploadImage(file);
            if ("error".equals(result.get("status"))) {
                return ResponseEntity.internalServerError().body(result);
            }

            return ResponseEntity.ok(result);
        } catch (IOException e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Upload failed: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Saves a new costume to the database.
     *
     * @param params all costume data from the frontend form
     * @return success or error message
     */
    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> save(@RequestBody CostumeSaveParams params) {
        Map<String, String> result = costumeService.saveFromParams(params);

        if ("error".equals(result.get("status"))) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

}