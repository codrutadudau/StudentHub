package com.project.StudentHub.services;

import com.project.StudentHub.configuration.email.EmailConfiguration;
import com.project.StudentHub.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;

@Service
@Transactional
public class EmailService {
    private EmailConfiguration emailConfiguration;

    public EmailService(EmailConfiguration emailConfiguration){
        this.emailConfiguration = emailConfiguration;
    }

    public void sendEmail(User from, User to, String subject, String content) throws ValidationException {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(this.emailConfiguration.getHost());
        javaMailSender.setPort(this.emailConfiguration.getPort());
        javaMailSender.setUsername(this.emailConfiguration.getUsername());
        javaMailSender.setPassword(this.emailConfiguration.getPassword());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from.getEmail());
        simpleMailMessage.setTo(to.getEmail());
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);

        javaMailSender.send(simpleMailMessage);
    }
}
