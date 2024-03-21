package com.adminbackend.repository;


import com.adminbackend.entity.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialRepo extends JpaRepository<Testimonial,Long> {
}
