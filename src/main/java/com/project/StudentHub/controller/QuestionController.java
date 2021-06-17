package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Answer;
import com.project.StudentHub.model.Question;
import com.project.StudentHub.dto.QuestionAnswersDto;
import com.project.StudentHub.model.Quiz;
import com.project.StudentHub.repository.AnswerRepository;
import com.project.StudentHub.repository.QuestionRepository;
import com.project.StudentHub.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
    public Question findQuestionById(@PathVariable Integer id) {
        Optional<Question> question = Optional.ofNullable(questionRepository.findQuestionById(id));
        return question
                .orElseThrow(() -> new ResourceNotFoundException("Question with id: " + id + " not found"));
    }

    @GetMapping("/questions/answers/{id}")
    public Collection<Answer> findAnswers(@PathVariable Integer id){
        Question question = questionRepository.findQuestionById(id);
        return question.getAnswers();
    }

    @GetMapping("/questions/quiz/{id}")
    public ArrayList<Object> getQuestionsByQuiz(@PathVariable Integer id) {
        ArrayList<Object> response = new ArrayList<>();
        Quiz quiz = quizRepository.findQuizById(id);
        Collection<Question> questions = quiz.getQuestions();

        for (Question q : questions) {
            HashMap<Object, Object> questionAnswers = new HashMap<Object, Object>();
            Collection<Answer> answers = q.getAnswers();
            questionAnswers.put("question", q);
            questionAnswers.put("answers", answers);
            response.add(questionAnswers);
        }

        return response;
    }

    @PostMapping("/questions/answers/{id}")
    public Question addQuestionAnswers(@PathVariable Integer id, @Valid @RequestBody QuestionAnswersDto questionAnswers) {
        Question question = questionRepository.findQuestionById(id);

        Object answers = questionAnswers.getAnswers();
        Object correctAnswers = questionAnswers.getCorrectAnswers();

        Map<String, String> answersArray = (Map<String, String>) answers;
        Map<String, String> correctAnswersArray = (Map<String, String>) correctAnswers;
        for (String answerKey : answersArray.keySet()) {
            if (!answersArray.get(answerKey).equals("")) {
                Answer a = new Answer();
                a.setQuestion(question);
                a.setDescription(answersArray.get(answerKey));
                a.setCorrect(false);
                for (String correctAnswerKey : correctAnswersArray.keySet()) {
                    if (answerKey.equals(correctAnswersArray.get(correctAnswerKey))) {
                        a.setCorrect(true);
                    }
                }

                answerRepository.save(a);
            }
        }

        return questionRepository.save(question);
    }

    @GetMapping("/questions/{id}/answers")
    public Collection<Answer> getAnswersByQuestionId(@PathVariable Integer id) {
        Question question = questionRepository.findQuestionById(id);

        return question.getAnswers();
    }

    @DeleteMapping("/questions/{id}")
    public void deleteQuestion(@PathVariable Integer id){
        Question questionDelete = questionRepository.findById(id)
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
