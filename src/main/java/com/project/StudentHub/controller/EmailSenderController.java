package com.project.StudentHub.controller;

import com.project.StudentHub.configuration.email.EmailConfiguration;
import com.project.StudentHub.dto.EmailSenderDto;
import com.project.StudentHub.model.User;
import com.project.StudentHub.repository.UserRepository;
import com.project.StudentHub.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/api")
@Validated
public class EmailSenderController {
    private EmailConfiguration emailConfiguration;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/emails/send")
    public ResponseEntity<Object> sendEmail(@Valid @RequestBody EmailSenderDto emailSenderDto) throws ValidationException {
        emailService.sendEmail(
                userRepository.findByEmail(emailSenderDto.getFrom()),
                userRepository.findByEmail(emailSenderDto.getTo()),
                emailSenderDto.getSubject(),
                emailSenderDto.getBody()
        );

        return ResponseEntity.noContent().build();
    }
}
