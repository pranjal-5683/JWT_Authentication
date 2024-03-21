package com.adminbackend.service;

import com.adminbackend.entity.Team;
import com.adminbackend.repository.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepo teamRepo;

    public String uploadImageAndSaveTeam(MultipartFile image, Team team) {
        try {
            // Convert MultipartFile to byte array
            byte[] fileBytes = image.getBytes();

            // Set the byte array to the Feature entity
            team.setImage(fileBytes);

            // Save the Feature entity
            teamRepo.save(team);

            return "Image uploaded and team saved successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading image and saving team";
        }
    }
    public List<Team> getAllTeam() {
        return teamRepo.findAll();
    }

    public void updateTeam(Long id, MultipartFile image, String title, String description) throws IOException {
        Team team = teamRepo.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));


        if (image != null ) {
            if (image != null && !image.isEmpty()) {
                byte[] imageData = image.getBytes();
                team.setImage(imageData);
            }
        }

        team.setTitle(title);
        team.setDescription(description);

        teamRepo.save(team);
    }

    public void deleteTeam(Long id) {
        teamRepo.deleteById(id);
    }
}

