package com.project.StudentHub.model;

public class QuestionAnswersDto {

    private Object answers;
    private Object correctAnswers;
    private Integer nrAnswers;

    public QuestionAnswersDto(){};

    public Object getAnswers() {
        return answers;
    }

    public void setAnswers(Object answers) {
        this.answers = answers;
    }

    public Object getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Object correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Integer getNrAnswers() {
        return nrAnswers;
    }

    public void setNrAnswers(Integer nrAnswers) {
        this.nrAnswers = nrAnswers;
    }
}
