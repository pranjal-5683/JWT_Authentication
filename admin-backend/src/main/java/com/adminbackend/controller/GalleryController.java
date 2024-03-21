package com.adminbackend.controller;


import com.adminbackend.entity.Gallery;
import com.adminbackend.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GalleryController {
    @Autowired
    private GalleryService galleryService;

    @PostMapping("/uploadGallery")
    public ResponseEntity<String> uploadImageAndSave(@RequestParam("file") MultipartFile file,
                                                            @ModelAttribute Gallery gallery) {
        try {
            Gallery newGallery =Gallery.builder()
                    .title(gallery.getTitle())
                    .build();

            String response = galleryService.uploadImageAndSaveGallery(file,newGallery);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image and saving feature");
        }
    }
    @GetMapping("/getGallery")
    public List<Gallery> fetchGallery() {
        return galleryService.getAllGallery();
    }
    @PutMapping("/updategallery/{id}")
    public ResponseEntity<String> updateFeature(@PathVariable long id,
                                                @RequestParam("image") MultipartFile image,
                                                @RequestParam("title") String title
                                               ) {
        try {
           galleryService.updateGallery(id, image, title);
            return ResponseEntity.ok("Gallery updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Gallery");
        }
    }

    @DeleteMapping("/deleteGallery/{id}")
    public ResponseEntity<String> deleteFeature(@PathVariable Long id) {
        try {
            galleryService.deleteGallery(id);
            return ResponseEntity.ok("Gallery deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Gallery");
        }
    }


}
