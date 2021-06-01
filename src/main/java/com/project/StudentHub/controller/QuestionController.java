package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Answer;
import com.project.StudentHub.model.Question;
import com.project.StudentHub.model.Quiz;
import com.project.StudentHub.repository.AnswerRepository;
import com.project.StudentHub.repository.QuestionRepository;
import com.project.StudentHub.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizRepository quizRepository;

    @PostMapping("/questions")
    public Question addQuestion(@Valid @RequestBody Question question){
        return questionRepository.save(question);
    }

    @GetMapping("/questions")
    public Iterable<Question> getQuestion(){
        return questionRepository.findAll();
    }

    @GetMapping("/questions/{id}")
    public Question findQuestionById(@RequestParam Integer id) {
        Optional<Question> question = Optional.ofNullable(questionRepository.findQuestionById(id));
        return question
                .orElseThrow(() -> new ResourceNotFoundException("Question with id: " + id + " not found"));
    }

    @GetMapping("/questions/{id}/answers")
    public Collection<Answer> findAnswers(@PathVariable Integer id){
        Question question = questionRepository.findQuestionById(id);
        return question.getAnswers();
    }

    @GetMapping("/questions/quiz/{id}")
    public Collection<Question> getQuestionsByQuiz(@PathVariable Integer id){
        Quiz quiz = quizRepository.findQuizById(id);
        return quiz.getQuestions();
    }

    @DeleteMapping("/questions/{id}")
    public void deleteQuestion(@PathVariable Integer id){
        Question questionDelete= questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question with id: " + id + " not found"));
        questionRepository.delete(questionDelete);
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Object> updateQuestion(@Valid @RequestBody Question question, @PathVariable Integer id){
        Optional<Question> questionOptional = Optional.ofNullable(questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question with id: " + id + " not found")));
        if(!questionOptional.isPresent())
            return ResponseEntity.notFound().build();
        question.setId(id);
        questionRepository.save(question);
        return ResponseEntity.noContent().build();
    }
}
