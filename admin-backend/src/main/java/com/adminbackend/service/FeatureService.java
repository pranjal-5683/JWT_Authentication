package com.adminbackend.service;


import com.adminbackend.entity.Feature;
import com.adminbackend.repository.FeatureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FeatureService {

    @Autowired
    private FeatureRepo featureRepo;


    public String uploadImageAndSaveFeature(MultipartFile image, Feature feature) {
        try {
            // Convert MultipartFile to byte array
            byte[] fileBytes = image.getBytes();

            // Set the byte array to the Feature entity
            feature.setImage(fileBytes);

            // Save the Feature entity
            featureRepo.save(feature);

            return "Image uploaded and Feature saved successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading image and saving feature";
        }
    }
    public List<Feature> getAllFeatures() {
        return featureRepo.findAll();
    }

    public void updateFeature(Long id, MultipartFile image, String title, String description) throws IOException {
        Feature feature = featureRepo.findById(id).orElseThrow(() -> new RuntimeException("Feature not found"));


        if (image != null ) {
            if (image != null && !image.isEmpty()) {
                byte[] imageData = image.getBytes();
                feature.setImage(imageData);
            }
        }

        feature.setTitle(title);
        feature.setDescription(description);

        featureRepo.save(feature);
    }

    public void deleteFeature(Long id) {
        featureRepo.deleteById(id);
    }
}


