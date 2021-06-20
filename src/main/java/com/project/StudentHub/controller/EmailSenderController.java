package com.project.StudentHub.controller;

import com.project.StudentHub.configuration.email.EmailConfiguration;
import com.project.StudentHub.dto.EmailSenderDto;
import com.project.StudentHub.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
public class EmailSenderController {
    private EmailConfiguration emailConfiguration;

    @PostMapping("/emails/send")
    public ResponseEntity<Object> sendEmail(@Valid @RequestBody EmailSenderDto emailSenderDto){
        User user = new User();
        emailSenderDto.setFrom(user.getEmail());
        emailSenderDto.setTo(user.getEmail());
        emailSenderDto.setSubject(user.getFirstName()+" " + user.getLastName() + " has registered");
        emailSenderDto.setBody("Email text to be replaced");

        return ResponseEntity.noContent().build();
    }

}
