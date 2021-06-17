package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.QuizInstance;
import com.project.StudentHub.model.QuizInstanceDto;
import com.project.StudentHub.model.UserStudent;
import com.project.StudentHub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
public class QuizInstanceController {

    @Autowired
    private QuizInstanceRepository quizInstanceRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserStudentRepository userStudentRepository;

    @Autowired
    private UserTeacherRepository userTeacherRepository;

    @PostMapping("/quiz_instances")
    public QuizInstance addQuizInstance(@Valid @RequestBody QuizInstanceDto quizInstance){
        QuizInstance quizInstance1 = new QuizInstance();
        quizInstance1.setUserStudent(userStudentRepository.findStudentById(quizInstance.getUser()));
        quizInstance1.setQuiz(quizRepository.findQuizById(quizInstance.getQuiz()));
        quizInstance1.setGrade(quizInstance.getGrade());
        quizInstance1.setAssignedBy(userTeacherRepository.findTeacherById(quizInstance.getAssignedBy()));
        quizInstance1.setStartedAt(quizInstance.getStartedAt());
        quizInstance1.setFinishedAt(quizInstance.getFinishedAt());

        return quizInstanceRepository.save(quizInstance1);
    }

    @GetMapping("/quiz_instances")
    public Iterable<QuizInstance> getQuizInstances(){
        return quizInstanceRepository.findAll();
    }

    @GetMapping("/quiz_instances/{id}")
    public QuizInstance findQuizInstanceById(@PathVariable Integer id) {
        return quizInstanceRepository.findQuizInstanceById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz instance with id: " + id + " not found"));
    }

    @DeleteMapping("/quiz_instances/{id}")
    public void deleteQuizInstance(@PathVariable Integer id){
        QuizInstance quizInstance = quizInstanceRepository.findQuizInstanceById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz instance with id: " + id + " not found"));

        quizInstanceRepository.delete(quizInstance);
    }
}
