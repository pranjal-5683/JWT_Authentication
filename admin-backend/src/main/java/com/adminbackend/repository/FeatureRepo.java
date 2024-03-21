package com.adminbackend.repository;



import com.adminbackend.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepo extends JpaRepository<Feature,Long> {
}
