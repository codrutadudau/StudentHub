package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Answer;
import com.project.StudentHub.model.User;
import com.project.StudentHub.model.UserOption;
import com.project.StudentHub.repository.AnswerRepository;
import com.project.StudentHub.repository.UserOptionRepository;
import com.project.StudentHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class UserOptionController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserOptionRepository userOptionRepository;

    @PostMapping("/userOptions")
    public UserOption addUserOption(@RequestParam Integer answerId, @RequestParam Integer userId, @Valid @RequestBody UserOption userOption){
        Answer answer = answerRepository.findAnswerById(answerId);
        User user = userRepository.findUserById(userId);
        userOption.setUser(user);
        userOption.setAnswer(answer);

        return userOptionRepository.save(userOption);
    }

    @GetMapping("/userOptions")
    public Iterable<UserOption> getUsersOptions(){return userOptionRepository.findAll();}

    @DeleteMapping("/userOptions/{id}")
    public void deleteUserOptions(@PathVariable Integer id){
        UserOption userOption = Optional.ofNullable(userOptionRepository.findUserOptionById(id))
                .orElseThrow(() -> new ResourceNotFoundException("User Option with id: " + id + " not found"));
        userOptionRepository.delete(userOption);
    }
}
