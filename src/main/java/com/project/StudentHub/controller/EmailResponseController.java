package com.project.StudentHub.controller;

import com.project.StudentHub.configuration.email.EmailConfiguration;
import com.project.StudentHub.configuration.email.EmailResponse;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/response")
@Validated
public class EmailResponseController {
    private EmailConfiguration emailConfiguration;

    public EmailResponseController(EmailConfiguration emailConfiguration){
        this.emailConfiguration = emailConfiguration;
    }

    @PostMapping
    public void sendResponse(@RequestBody EmailResponse emailResponse, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException("Response is not valid");
        }

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(this.emailConfiguration.getHost());
        javaMailSender.setPort(this.emailConfiguration.getPort());
        javaMailSender.setUsername(this.emailConfiguration.getUsername());
        javaMailSender.setPassword(this.emailConfiguration.getPassword());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailResponse.getEmail());
        simpleMailMessage.setTo("admin@admin.com");
        simpleMailMessage.setSubject("New response from: " + emailResponse.getName());
        simpleMailMessage.setText(emailResponse.getResponse());

        javaMailSender.send(simpleMailMessage);
    }
}
