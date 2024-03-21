package com.adminbackend.controller;


import com.adminbackend.entity.Testimonial;
import com.adminbackend.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestimonialController {
    @Autowired
    private TestimonialService testimonialService;

    @PostMapping("/uploadTestimonial")
    public ResponseEntity<String> uploadImageAndSave(@RequestParam("file") MultipartFile file,
                                                     @ModelAttribute Testimonial testimonial) {
        try {
            Testimonial newTestimonial = Testimonial.builder()
                    .title(testimonial.getTitle())
                    .description(testimonial.getDescription())
                    .build();

            String response = testimonialService.uploadImageAndSaveTestimonial(file, newTestimonial);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image and saving Testimoanial");
        }
    }

    @GetMapping("/getTestimonial")
    public List<Testimonial> fetchTestimonialTitles() {
        return testimonialService.getAllTestimonial();
    }

    @PutMapping("/updateTestimonial/{id}")
    public ResponseEntity<String> updateTestimonial(@PathVariable long id,
                                             @RequestParam("image") MultipartFile image,
                                             @RequestParam("title") String title,
                                             @RequestParam("description") String description) {
        try {
            testimonialService.updateTestimonial(id, image, title, description);
            return ResponseEntity.ok("Testimonial updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Testimonial");
        }
    }

    @DeleteMapping("/deleteTestimonial/{id}")
    public ResponseEntity<String> deleteTestimonial(@PathVariable Long id) {
        try {
            testimonialService.deleteTestimonial(id);
            return ResponseEntity.ok("Testimonial deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Testimonial");
        }
    }
}
