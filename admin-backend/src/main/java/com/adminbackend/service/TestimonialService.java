package com.adminbackend.service;


import com.adminbackend.entity.Testimonial;
import com.adminbackend.repository.TestimonialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class TestimonialService {
    @Autowired
    private TestimonialRepo testimonialRepo;

    public String uploadImageAndSaveTestimonial(MultipartFile image, Testimonial testimonial) {
        try {
            // Convert MultipartFile to byte array
            byte[] fileBytes = image.getBytes();

            // Set the byte array to the Feature entity
            testimonial.setImage(fileBytes);

            // Save the Feature entity
            testimonialRepo.save(testimonial);

            return "Image uploaded and Testimonial saved successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading image and saving Testimonial";
        }
    }
    public List<Testimonial> getAllTestimonial() {
        return testimonialRepo.findAll();
    }

    public void updateTestimonial(Long id, MultipartFile image, String title, String description) throws IOException {
         Testimonial testimonial = testimonialRepo.findById(id).orElseThrow(() -> new RuntimeException("Testimonial not found"));


        if (image != null ) {
            if (image != null && !image.isEmpty()) {
                byte[] imageData = image.getBytes();
                testimonial.setImage(imageData);
            }
        }

        testimonial.setTitle(title);
        testimonial.setDescription(description);

        testimonialRepo.save(testimonial);
    }

    public void deleteTestimonial(Long id) {
        testimonialRepo.deleteById(id);
    }
}
