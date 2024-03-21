package com.adminbackend.service;


import com.adminbackend.entity.Gallery;
import com.adminbackend.repository.GalleryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class GalleryService {
    @Autowired
    private GalleryRepo galleryRepo;

    public String uploadImageAndSaveGallery(MultipartFile image, Gallery gallery) {
        try {
            // Convert MultipartFile to byte array
            byte[] fileBytes = image.getBytes();

            // Set the byte array to the Feature entity
            gallery.setImage(fileBytes);

            // Save the Feature entity
            galleryRepo.save(gallery);

            return "Image uploaded and Gallery saved successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading image and saving Gallery";
        }
    }
    public List<Gallery> getAllGallery() {
        return galleryRepo.findAll();
    }

    public void updateGallery(Long id, MultipartFile image, String title) throws IOException {
        Gallery gallery = galleryRepo.findById(id).orElseThrow(() -> new RuntimeException("Gallery not found"));


        if (image != null ) {
            if (image != null && !image.isEmpty()) {
                byte[] imageData = image.getBytes();
                gallery.setImage(imageData);
            }
        }

        gallery.setTitle(title);


        galleryRepo.save(gallery);
    }

    public void deleteGallery(Long id) {
        galleryRepo.deleteById(id);
    }
}

