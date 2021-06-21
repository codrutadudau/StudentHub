package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.*;
import com.project.StudentHub.dto.QuizInstanceDto;
import com.project.StudentHub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@Validated
public class QuizInstanceController {

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

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
        quizInstance1.setUserStudent(userStudentRepository.findUserStudentById(quizInstance.getUserStudent()));
        quizInstance1.setQuiz(quizRepository.findQuizById(quizInstance.getQuiz()));
        quizInstance1.setGrade(quizInstance.getGrade());
        quizInstance1.setAssignedBy(userTeacherRepository.findTeacherById(quizInstance.getAssignedBy()));
        quizInstance1.setStartedAt(quizInstance.getStartedAt());
        quizInstance1.setAssignedAt(LocalDateTime.now());
        quizInstance1.setFinishedAt(quizInstance.getFinishedAt());

        return quizInstanceRepository.save(quizInstance1);
    }

    @GetMapping("/quiz_instances")
    public List<QuizInstance> getQuizInstances(@RequestParam("user") Optional<Integer> userId){
        if (userId.isPresent()) {
            return quizInstanceRepository.findQuizInstancesByUserId(userId.get());
        }

        return quizInstanceRepository.findAll();
    }

    @GetMapping("/quiz_instances/{id}/answers")
    public ArrayList<Object> getQuizInstanceAnswers(@PathVariable Integer id){
        Optional<QuizInstance> quizInstanceOptional = Optional.ofNullable(quizInstanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz instance with id: " + id + " not found")));

        ArrayList<Object> response = new ArrayList<>();

        QuizInstance quizInstance = quizInstanceOptional.get();
        UserStudent userStudent = quizInstance.getUserStudent();
        Quiz quiz = quizInstance.getQuiz();
        Collection<Question> questions = quiz.getQuestions();
        for (Question q : questions) {
            HashMap<Object, Object> questionAnswers = new HashMap<Object, Object>();
            Collection<Answer> answers = q.getAnswers();
            ArrayList<Object> userOptionAnswers = new ArrayList<>();
            for (Answer a : answers) {
                HashMap<String, String> enrichedAnswer = new HashMap<String, String>();
                enrichedAnswer.put("id", String.valueOf(a.getId()));
                enrichedAnswer.put("correct", a.isCorrect() ? "true" : "false");
                enrichedAnswer.put("description", a.getDescription());
                enrichedAnswer.put("question_id", String.valueOf(a.getQuestion().getId()));
                boolean hasAnswerOption = answerOptionRepository.hasAnswerOptionForUserStudentIdAndAnswerId(userStudent.getId(), a.getId()) > 0;
                enrichedAnswer.put("checkedByUser", hasAnswerOption ? "true" : "false");
                userOptionAnswers.add(enrichedAnswer);
            }

            questionAnswers.put("question", q);
            questionAnswers.put("answers", userOptionAnswers);
            response.add(questionAnswers);
        }

        return response;
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

    @PutMapping("/quiz_instances/{id}")
    public ResponseEntity<Object> updateQuizInstance(@Valid @RequestBody QuizInstanceDto quizInstance, @PathVariable Integer id){
        Optional<QuizInstance> quizInstanceOptional = Optional.ofNullable(quizInstanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz instance with id: " + id + " not found")));
        if(!quizInstanceOptional.isPresent())
            return ResponseEntity.notFound().build();

        QuizInstance qi = quizInstanceOptional.get();
        if (qi.getFinishedAt() == null) {
            qi.setFinishedAt(LocalDateTime.now());
            qi.setGrade(quizInstance.getGrade());
        }

        quizInstanceRepository.save(qi);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/quiz_instances/{id}/start")
    public ResponseEntity<Object> startQuiz(@PathVariable Integer id){
        Optional<QuizInstance> quizInstanceOptional = Optional.ofNullable(quizInstanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz instance with id: " + id + " not found")));
        if(!quizInstanceOptional.isPresent())
            return ResponseEntity.notFound().build();

        QuizInstance qi = quizInstanceOptional.get();
        if (qi.getStartedAt() == null) {
            qi.setStartedAt(LocalDateTime.now());
        }

        quizInstanceRepository.save(qi);

        return ResponseEntity.noContent().build();
    }
}
