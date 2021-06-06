package com.project.StudentHub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "varchar(255) default null")
    @NotEmpty
    private String description;

    @Column(columnDefinition = "integer default 0")
    private int defaultGrade;

    @Column(columnDefinition = "boolean default false")
    private boolean hasMultipleAnswers;

    @ManyToMany(mappedBy = "questions")
    @JsonIgnore
    private Collection<Quiz> quizzes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "question")
    @JsonIgnore
    private Collection<Answer> answers;

    public Collection<Quiz> getQuizzes() {
        return quizzes;
    }

    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    public void setQuizzes(Collection<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public Question(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDefaultGrade() {
        return defaultGrade;
    }

    public void setDefaultGrade(int defaultGrade) {
        this.defaultGrade = defaultGrade;
    }

    public boolean isHasMultipleAnswers() {
        return hasMultipleAnswers;
    }

    public void setHasMultipleAnswers(boolean hasMultipleAnswers) {
        this.hasMultipleAnswers = hasMultipleAnswers;
    }
}
