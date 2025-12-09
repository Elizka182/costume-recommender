package com.yelyzaveta.costume_recommender_be.serveses;

import com.yelyzaveta.costume_recommender_be.entity.Costume;
import com.yelyzaveta.costume_recommender_be.repository.CostumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for handling image file operations including upload, retrieval,
 * and cleanup of unused images for costumes.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final ResourceLoader resourceLoader;
    private final CostumeRepository costumeRepository;
    private static final String IMAGE_DIRECTORY = "static/images/";

    /**
     * Finds an image resource by its filename.
     *
     * @param filename the name of the image file
     * @return a {@link Resource} representing the image, or null if not found
     */
    public Resource findImageByFilename(String filename) {
        Resource resource = resourceLoader.getResource("classpath:" + IMAGE_DIRECTORY + filename);

        if (!resource.exists()) {
            return null;
        }
        return resource;
    }

    /**
     * Determines the content type of the given resource.
     *
     * @param resource the image resource
     * @return the content type as a string (default "image/jpeg" if unknown)
     * @throws IOException if an I/O error occurs
     */
    public String getContentType(Resource resource) throws IOException {
        String contentType = Files.probeContentType(Paths.get(resource.getFilename()));
        return contentType != null ? contentType : "image/jpeg";
    }

    /**
     * Uploads a multipart image file to the server and generates a unique filename.
     *
     * @param file the uploaded {@link MultipartFile}
     * @return a map containing upload status, filename, image URL, physical path, and file size
     * @throws IOException if an I/O error occurs while saving the file
     */
    public Map<String, String> uploadImage(MultipartFile file) throws IOException {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Please upload an image file");
            return response;
        }

        Path uploadPath = Paths.get(resourceLoader.getResource("classpath:" + IMAGE_DIRECTORY).getURI());

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String uuidFilename = UUID.randomUUID() + extension;
        Path filePath = uploadPath.resolve(uuidFilename);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        response.put("status", "success");
        response.put("filename", uuidFilename);
        response.put("imageUrl", uuidFilename);
        response.put("physicalPath", filePath.toString());
        response.put("size", String.valueOf(file.getSize()));

        return response;
    }

    /**
     * Scheduled cleanup method that removes unused image files older than 15 minutes.
     * Runs daily at 01:00 AM based on the cron expression.
     */
    @Scheduled(cron = "0 0 01 * * *")
    public void cleanUp() {
        Instant now = Instant.now().minus(15, ChronoUnit.MINUTES);
        Set<String> imageUrls = costumeRepository.findAll().stream()
                .map(Costume::getImageurl)
                .collect(Collectors.toSet());
        Resource resource = resourceLoader.getResource("classpath:" + IMAGE_DIRECTORY);

        try (Stream<Path> images = Files.list(resource.getFile().toPath())) {
            images.filter(path -> !imageUrls.contains(path.getFileName().toString()))
                    .forEach(path -> deleteFile(path, now));
        } catch (IOException e) {
            log.warn("Could not clean up temporary files", e);
        }
    }

    private static void deleteFile(Path path, Instant now) {
        try {
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            if (attr.creationTime().toInstant().isBefore(now)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            log.warn("Failed to delete file {}", path, e);
        }
    }
}
