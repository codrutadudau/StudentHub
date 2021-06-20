package com.project.StudentHub.controller;

import com.google.common.collect.Lists;
import com.project.StudentHub.dto.QuizDto;
import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Question;
import com.project.StudentHub.dto.QuestionDto;
import com.project.StudentHub.model.Quiz;
import com.project.StudentHub.repository.CourseRepository;
import com.project.StudentHub.repository.QuestionRepository;
import com.project.StudentHub.repository.QuizRepository;
import com.project.StudentHub.repository.UserTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
@Validated
public class QuizController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserTeacherRepository userTeacherRepository;

    @PostMapping("/quizzes")
    public Quiz addQuiz(@Valid @RequestBody QuizDto quiz){
        Quiz q = new Quiz();
        q.setName(quiz.getName());
        q.setPassword(quiz.getPassword());
        q.setQuizIntro(quiz.getQuizIntro());
        q.setTimeOpen(quiz.getTimeOpen());
        q.setTimeClose(quiz.getTimeClose());
        q.setCourse(courseRepository.findCourseById(quiz.getCourse()));
        q.setDuration(quiz.getDuration());

        return quizRepository.save(q);
    }

    @PostMapping("/quizzes/{id}/question")
    public Quiz addQuizQuestion(@PathVariable Integer id, @Valid @RequestBody QuestionDto question) {
        Quiz quiz = quizRepository.findQuizById(id);
        Question q = new Question();
        q.setDefaultGrade(question.getDefaultGrade());
        q.setDescription(question.getDescription());
        q.setHasMultipleAnswers(question.hasMultipleAnswers());
        quiz.add(q);

        return quizRepository.save(quiz);
    }

    @DeleteMapping("/quizzes/{id}/question/{questionId}")
    public void deleteQuizQuestion(@PathVariable Integer id, @PathVariable Integer questionId) {
        Quiz quiz = quizRepository.findQuizById(id);
        Question question = questionRepository.findQuestionById(questionId);
        quiz.remove(question);

        quizRepository.save(quiz);
    }

    @GetMapping("/quizzes")
    public ArrayList<Object> getQuiz(@RequestParam("course") Optional<Integer> courseId, @RequestParam("teacher") Optional<Integer> teacherId){
        if (courseId.isPresent()) {
            return Lists.newArrayList(quizRepository.findByCourseId(courseId.get()));
        }

        if (teacherId.isPresent()) {
            ArrayList<Object> response = new ArrayList<>();
            for (Quiz q : quizRepository.findQuizzesByUserTeacher(userTeacherRepository.findTeacherById(teacherId.get()))) {
                Map<String, Object> json = new HashMap();
                json.put("id", q.getId());
                json.put("name", q.getName());
                json.put("quizIntro", q.getQuizIntro());
                json.put("timeOpen", q.getTimeOpen());
                json.put("timeClose", q.getTimeClose());
                json.put("password", q.getPassword());
                json.put("duration", q.getDuration());
                json.put("courseId", q.getCourse().getId());
                json.put("courseName", q.getCourse().getName());

                response.add(json);
            }

            return response;
        }

        ArrayList<Object> response = new ArrayList<>();
        for (Quiz q : quizRepository.findAll()) {
            Map<String, Object> json = new HashMap();
            json.put("id", q.getId());
            json.put("name", q.getName());
            json.put("quizIntro", q.getQuizIntro());
            json.put("timeOpen", q.getTimeOpen());
            json.put("timeClose", q.getTimeClose());
            json.put("password", q.getPassword());
            json.put("duration", q.getDuration());
            json.put("courseId", q.getCourse().getId());
            json.put("courseName", q.getCourse().getName());

            response.add(json);
        }

        return response;
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
    public ResponseEntity<Object> updateQuiz(@Valid @RequestBody QuizDto quiz, @PathVariable Integer id){
        Optional<Quiz> quizOptional = Optional.ofNullable(quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz with id: " + id + " not found")));
        if(!quizOptional.isPresent())
            return ResponseEntity.notFound().build();

        Quiz q = quizOptional.get();
        q.setName(quiz.getName());
        q.setPassword(quiz.getPassword());
        q.setQuizIntro(quiz.getQuizIntro());
        q.setTimeOpen(quiz.getTimeOpen());
        q.setTimeClose(quiz.getTimeClose());
        q.setDuration(quiz.getDuration());
        q.setCourse(courseRepository.findCourseById(quiz.getCourse()));
        quizRepository.save(q);

        return ResponseEntity.noContent().build();
    }
}
