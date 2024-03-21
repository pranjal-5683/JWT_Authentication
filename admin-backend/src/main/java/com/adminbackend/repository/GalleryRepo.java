package com.adminbackend.repository;


import com.adminbackend.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GalleryRepo extends JpaRepository<Gallery,Long> {

}
