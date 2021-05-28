package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Quiz;
import com.project.StudentHub.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;

    @PostMapping("/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz){
        return quizRepository.save(quiz);
    }

    @GetMapping("/quizzes")
    public Iterable<Quiz> getQuiz(){
        return quizRepository.findAll();
    }

    @GetMapping("/quizzes/{id}")
    public Quiz findQuizById(@PathVariable Integer id) {
        Optional<Quiz> quiz = Optional.ofNullable(quizRepository.findQuizById(id));
        return quiz
                .orElseThrow(() -> new ResourceNotFoundException("Quiz with id: " + id + " not found"));
    }
    @DeleteMapping("/quizzes/{id}")
    public void deleteQuiz(@PathVariable Integer id){
        Quiz quizDelete= quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz with id: " + id + " not found"));
        quizRepository.delete(quizDelete);
    }

    @PutMapping("/quizzes/{id}")
    public ResponseEntity<Object> updateQuiz(@Valid @RequestBody Quiz quiz, @PathVariable Integer id){
        Optional<Quiz> quizOptional = Optional.ofNullable(quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz with id: " + id + " not found")));
        if(!quizOptional.isPresent())
            return ResponseEntity.notFound().build();
        quiz.setId(id);
        quizRepository.save(quiz);
        return ResponseEntity.noContent().build();
    }
}
