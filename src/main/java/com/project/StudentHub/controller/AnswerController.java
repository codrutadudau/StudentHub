package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Answer;
import com.project.StudentHub.model.AnswerDto;
import com.project.StudentHub.repository.AnswerRepository;
import com.project.StudentHub.repository.QuestionRepository;
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
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/answers")
    public Answer addAnswer(@Valid @RequestBody AnswerDto answerDto){
        Answer answer = new Answer();
        answer.setDescription(answerDto.getDescription());
        answer.setCorrect(answerDto.isCorrect());
        answer.setQuestion(questionRepository.findQuestionById(answerDto.getQuestion()));

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
        Answer answerDelete = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer with id: " + id + " not found"));
        answerRepository.delete(answerDelete);
    }

    @PutMapping("/answers/{id}")
    public ResponseEntity<Object> updateAnswer(@Valid @RequestBody AnswerDto answerDto, @PathVariable Integer id){
        Optional<Answer> answerOptional = Optional.ofNullable(answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer with id: " + id + " not found")));
        if(!answerOptional.isPresent())
            return ResponseEntity.notFound().build();

        Answer answer = answerRepository.findAnswerById(id);
        answer.setCorrect(answerDto.isCorrect());
        answer.setDescription(answerDto.getDescription());
        answerRepository.save(answer);

        return ResponseEntity.noContent().build();
    }
}
