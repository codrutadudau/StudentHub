package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.*;
import com.project.StudentHub.repository.AnswerRepository;
import com.project.StudentHub.repository.AnswerOptionRepository;
import com.project.StudentHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class AnswerOptionController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @PostMapping("/answer_options")
    public AnswerOption addAnswerOption(@Valid @RequestBody AnswerOptionDto answerOption){
        AnswerOption answerOption1 = new AnswerOption();
        answerOption1.setUser(userRepository.findUserById(answerOption.getUser()));
        answerOption1.setAnswer(answerRepository.findAnswerById(answerOption.getAnswer()));

        return answerOptionRepository.save(answerOption1);
    }

    @GetMapping("/answer_options")
    public Iterable<AnswerOption> getUsersOptions(){ return answerOptionRepository.findAll();}

    @DeleteMapping("/answer_options/{id}")
    public void deleteUserOptions(@PathVariable Integer id){
        AnswerOption answerOption = Optional.ofNullable(answerOptionRepository.findUserOptionById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Answer Option with id: " + id + " not found"));
        answerOptionRepository.delete(answerOption);
    }
}
