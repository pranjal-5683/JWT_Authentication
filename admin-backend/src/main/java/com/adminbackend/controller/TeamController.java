package com.adminbackend.controller;


import com.adminbackend.entity.Team;
import com.adminbackend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/uploadTeam")
    public ResponseEntity<String> uploadImageAndSave(@RequestParam("file") MultipartFile file,
                                                     @ModelAttribute Team team) {
        try {
            Team newTeam = Team.builder()
                    .title(team.getTitle())
                    .description(team.getDescription())
                    .build();

            String response = teamService.uploadImageAndSaveTeam(file,newTeam);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image and saving Team");
        }
    }
    @GetMapping("/getTeam")
    public List<Team> fetchTeamTitles() {
        return teamService.getAllTeam();
    }
    @PutMapping("/updateTeam/{id}")
    public ResponseEntity<String> updateTeam(@PathVariable long id,
                                                @RequestParam("image") MultipartFile image,
                                                @RequestParam("title") String title,
                                                @RequestParam("description") String description) {
        try {
            teamService.updateTeam(id, image, title, description);
            return ResponseEntity.ok("Team updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Team");
        }
    }

    @DeleteMapping("/deleteTeam/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long id) {
        try {
            teamService.deleteTeam(id);
            return ResponseEntity.ok("Team deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Team");
        }
    }
}
