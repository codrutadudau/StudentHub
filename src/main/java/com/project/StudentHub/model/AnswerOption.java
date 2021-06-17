package com.project.StudentHub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "answer_option")
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_student_id")
    @JsonIgnore
    private UserStudent userStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    @JsonIgnore
    private Answer answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserStudent getUserStudent() {
        return userStudent;
    }

    public void setUserStudent(UserStudent userStudent) {
        this.userStudent = userStudent;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
