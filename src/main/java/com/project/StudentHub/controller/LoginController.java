package com.project.StudentHub.controller;

import com.project.StudentHub.model.AuthenticationRequest;
import com.project.StudentHub.services.util.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        }catch (Exception e){
            throw new Exception("Invalid email or password! Please try again!");
        }
        return tokenProvider.generateToken(authenticationRequest.getEmail());
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/userping", method = RequestMethod.GET)
    public String userPing(){
        return "Any User Can Read This";
    }
}
