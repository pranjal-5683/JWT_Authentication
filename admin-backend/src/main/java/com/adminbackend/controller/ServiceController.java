package com.adminbackend.controller;


import com.adminbackend.entity.Services;
import com.adminbackend.service.Services_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ServiceController {

    @Autowired
    private Services_Service services_service;


    @PostMapping("/uploadService")
    public ResponseEntity<String> uploadImageAndSave(@RequestParam("file") MultipartFile file,
                                                            @ModelAttribute Services services) {
        try {
            Services newServices = Services.builder()
                    .title(services.getTitle())
                    .description(services.getDescription())
                    .build();

            String response = services_service.uploadImageAndSaveService(file,newServices);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image and saving feature");
        }
    }
    @GetMapping("/getService")
    public List<Services> fetchServiceTitles() {
        return services_service.getAllService();
    }
    @PutMapping("/updateService/{id}")
    public ResponseEntity<String> updateFeature(@PathVariable long id,
                                                @RequestParam("image") MultipartFile image,
                                                @RequestParam("title") String title,
                                                @RequestParam("description") String description) {
        try {
            services_service.updateService(id, image, title, description);
            return ResponseEntity.ok("Service updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Service");
        }
    }

    @DeleteMapping("/deleteService/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        try {
            services_service.deleteService(id);
            return ResponseEntity.ok("Service deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Service");
        }
    }

}
