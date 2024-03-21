package com.adminbackend.controller;


import com.adminbackend.entity.Feature;
import com.adminbackend.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImageAndSave(@RequestParam("file") MultipartFile file,
                                                            @ModelAttribute Feature feature) {
        try {
            Feature newFeature = Feature.builder()
                    .title(feature.getTitle())
                    .description(feature.getDescription())
                    .build();

            String response = featureService.uploadImageAndSaveFeature(file,newFeature);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image and saving feature");
        }
    }
    @GetMapping("/getFeature")
    public List<Feature> fetchFeatureTitles() {
        return featureService.getAllFeatures();
    }
     @PutMapping("/updatefeature/{id}")
    public ResponseEntity<String> updateFeature(@PathVariable long id,
                                                @RequestParam("image") MultipartFile image,
                                                @RequestParam("title") String title,
                                                @RequestParam("description") String description) {
        try {
            featureService.updateFeature(id, image, title, description);
            return ResponseEntity.ok("Feature updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating feature");
        }
    }

    @DeleteMapping("/deletefeature/{id}")
    public ResponseEntity<String> deleteFeature(@PathVariable Long id) {
        try {
            featureService.deleteFeature(id);
            return ResponseEntity.ok("Feature deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting feature");
        }
    }


}