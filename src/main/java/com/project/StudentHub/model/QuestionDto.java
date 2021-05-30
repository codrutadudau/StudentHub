package com.project.StudentHub.model;

public class QuestionDto {
    private String description;

    private int defaultGrade;

    private boolean hasMultipleAnswers;

    public QuestionDto(){};

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

    public boolean hasMultipleAnswers() {
        return hasMultipleAnswers;
    }

    public void setHasMultipleAnswers(boolean hasMultipleAnswers) {
        this.hasMultipleAnswers = hasMultipleAnswers;
    }
}
