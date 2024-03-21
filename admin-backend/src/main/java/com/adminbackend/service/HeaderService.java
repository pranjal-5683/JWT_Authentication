package com.adminbackend.service;

import com.adminbackend.entity.Header;
import com.adminbackend.repository.HeaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeaderService {

    @Autowired
    private HeaderRepo headerRepo;

    public String createHeader(Header header){
        try {
            Header head = Header.builder()
                    .title(header.getTitle())
                    .paragraph(header.getParagraph())
                    .build();
            headerRepo.save(head);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "added successfully";
    }

    public List<Header> getallhead()
    {
        return this.headerRepo.findAll();
    }


        public void updateHead(Long id, String title, String paragraph) {
            Header head = headerRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Head not found with id: " + id));
            head.setTitle(title);
            head.setParagraph(paragraph);
           headerRepo.save(head);
        }
    }


