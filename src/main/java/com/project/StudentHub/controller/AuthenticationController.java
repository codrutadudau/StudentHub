package com.project.StudentHub.controller;

import com.project.StudentHub.configuration.SecurityConfiguration;
import com.project.StudentHub.exception.EmailExistsException;
import com.project.StudentHub.model.AuthenticationRequest;
import com.project.StudentHub.model.User;
import com.project.StudentHub.model.UserDto;
import com.project.StudentHub.repository.RoleRepository;
import com.project.StudentHub.repository.UserRepository;
import com.project.StudentHub.services.util.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

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
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        }catch (Exception e){
            throw new Exception("Invalid email or password! Please try again!");
        }
        return tokenProvider.generateToken(authenticationRequest.getEmail());
    }

    @PostMapping("/signup")
    public User registerUser(@RequestBody UserDto accountDto) throws EmailExistsException {
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException
                    ("This email already exists: " + accountDto.getEmail());

        }

        User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setEmail(accountDto.getEmail());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setPhoneNumber(accountDto.getPhoneNumber());

        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/userping", method = RequestMethod.GET)
    public String userPing(){
        return "Any User Can Read This";
    }
}
