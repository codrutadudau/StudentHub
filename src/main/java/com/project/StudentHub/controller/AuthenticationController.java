package com.project.StudentHub.controller;

import com.project.StudentHub.exception.EmailExistsException;
import com.project.StudentHub.model.AuthenticationRequest;
import com.project.StudentHub.model.User;
import com.project.StudentHub.dto.UserDto;
import com.project.StudentHub.repository.RoleRepository;
import com.project.StudentHub.repository.UserRepository;
import com.project.StudentHub.services.EmailService;
import com.project.StudentHub.services.util.TokenProvider;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.bind.ValidationException;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        User user = userRepository.findByEmail(authenticationRequest.getEmail());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user does not exist");
        }

        if (!user.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email not validated");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        }

        return tokenProvider.generateToken(authenticationRequest.getEmail(), user.getRole().getName());
    }

    @PostMapping("/signup")
    public User registerUser(@RequestBody UserDto accountDto) throws EmailExistsException, ValidationException {
        if (emailExist(accountDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This email already exists");
        }

        User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setEmail(accountDto.getEmail());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setPhoneNumber(accountDto.getPhoneNumber());

        if (user.getFirstName().length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The first name should have at least 3 characters");
        }

        if (user.getLastName().length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The last name should have at least 3 characters");
        }

        user.setRole(roleRepository.findByName("ROLE_USER"));

        User userTo = userRepository.findByEmail("admin@info.uaic.ro");
        String subject = "New registration: " + user.getFirstName() +" " + user.getLastName();
        String content = "The user " + user.getFirstName() +" " + user.getLastName() + " with the email address " +
                user.getEmail() + " has registered. Please validate the account from the admin panel or delete the account.";

        emailService.sendEmail(user, userTo, subject, content);
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/userping", method = RequestMethod.GET)
    public String userPing(){
        return "Any User Can Read This";
    }
}
