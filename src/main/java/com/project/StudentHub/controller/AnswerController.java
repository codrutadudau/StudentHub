package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Answer;
import com.project.StudentHub.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/answers")
    public Answer addAnswer(@Valid @RequestBody Answer answer){
        return answerRepository.save(answer);
    }

    @GetMapping("/answers")
    public Iterable<Answer> getAnswer(){
        return answerRepository.findAll();
    }

    @GetMapping("/answers/{id}")
    public Answer findAnswerById(@PathVariable Integer id) {
        Optional<Answer> answer = Optional.ofNullable(answerRepository.findAnswerById(id));
        return answer
                .orElseThrow(() -> new ResourceNotFoundException("Answer with id: " + id + " not found"));
    }
    @DeleteMapping("/answers/{id}")
    public void deleteAnswer(@PathVariable Integer id){
        Answer answerDelete= answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer with id: " + id + " not found"));
        answerRepository.delete(answerDelete);
    }

    @PutMapping("/answers/{id}")
    public ResponseEntity<Object> updateAnswer(@Valid @RequestBody Answer answer, @PathVariable Integer id){
        Optional<Answer> answerOptional = Optional.ofNullable(answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer with id: " + id + " not found")));
        if(!answerOptional.isPresent())
            return ResponseEntity.notFound().build();
        answer.setId(id);
        answerRepository.save(answer);
        return ResponseEntity.noContent().build();
    }
}
