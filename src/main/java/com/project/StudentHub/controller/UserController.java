package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.repository.UserRepository;
import com.project.StudentHub.model.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/users")
    public Iterable<User> getUser(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable Integer id) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserById(id));
        return user
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        User userDelete= userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
        userRepository.delete(userDelete);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User user, @PathVariable Integer id){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found")));
        if(!userOptional.isPresent())
            return ResponseEntity.notFound().build();
        user.setId(id);
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/me")
    public User getLoggedUser(@RequestHeader HttpHeaders headers){
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION).substring(7);
        String[] tokenChunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(tokenChunks[1]));

        JSONObject jsonPayload = new JSONObject(payload);

        return userRepository.findByEmail(jsonPayload.getString("sub"));
    }
}
