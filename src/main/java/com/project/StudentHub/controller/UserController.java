package com.project.StudentHub.controller;

import com.project.StudentHub.dto.getStudentProperties;
import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.repository.AnswerRepository;
import com.project.StudentHub.repository.UserRepository;
import com.project.StudentHub.model.User;
import com.project.StudentHub.services.EmailService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class UserController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

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

    @PutMapping("/users/{id}/enable")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id) throws ValidationException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found")));
        if(userOptional.isEmpty())
            return ResponseEntity.notFound().build();

        if (!userOptional.get().isEnabled()) {
            userOptional.get().setEnabled(true);

            User userFrom = userRepository.findByEmail("admin@info.uaic.ro");
            String subject = "Account validated";
            String content = "Your account on StudentHub has been validated. You can now log into your account.";
            emailService.sendEmail(userFrom, userOptional.get(), subject, content);
        }

        userRepository.save(userOptional.get());

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
