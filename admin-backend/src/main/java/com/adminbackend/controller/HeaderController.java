package com.adminbackend.controller;

import com.adminbackend.entity.Header;
import com.adminbackend.service.HeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HeaderController {
    @Autowired
    private HeaderService headerService;

    @PostMapping("/head")
    public ResponseEntity<String> addHeader(@RequestBody Header header)  {
        try {
            String result = headerService.createHeader(header);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to add header: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/gethead")
    public ResponseEntity<List<Header>> getHeads() {
       List<Header> heads =  headerService.getallhead();
        return ResponseEntity.ok(heads);
    }

    @PutMapping("/headerupdate/{id}")
    public ResponseEntity<String> updateHeader(@PathVariable Long id, @RequestBody Header header) {
        try {
            headerService.updateHead(id, header.getTitle(), header.getParagraph());
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}