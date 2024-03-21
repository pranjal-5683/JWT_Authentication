package com.adminbackend.service;


import com.adminbackend.entity.Services;
import com.adminbackend.repository.ServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class Services_Service {

    @Autowired
    private ServiceRepo serviceRepo;

    public String uploadImageAndSaveService(MultipartFile image, Services services) {
        try {
            // Convert MultipartFile to byte array
            byte[] fileBytes = image.getBytes();

            // Set the byte array to the Feature entity
            services.setImage(fileBytes);

            // Save the Feature entity
            serviceRepo.save(services);

            return "Image uploaded and Service saved successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading image and saving Service";
        }
    }
    public List<Services> getAllService() {
        return serviceRepo.findAll();
    }

    public void updateService(Long id, MultipartFile image, String title, String description) throws IOException {
        Services services = serviceRepo.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));


        if (image != null ) {
            if (image != null && !image.isEmpty()) {
                byte[] imageData = image.getBytes();
                services.setImage(imageData);
            }
        }

        services.setTitle(title);
        services.setDescription(description);

        serviceRepo.save(services);
    }

    public void deleteService(Long id)
    {
        serviceRepo.deleteById(id);
    }
}


