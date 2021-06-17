package com.project.StudentHub.controller;

import com.project.StudentHub.dto.ClassroomDto;
import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Classroom;
import com.project.StudentHub.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
@Validated
public class ClassroomController {
    @Autowired
    private ClassroomRepository classroomRepository;

    @PostMapping("/classrooms")
    public Classroom addClassroom(@Valid @RequestBody ClassroomDto classroom){
        Classroom classroom1 = new Classroom();
        classroom1.setName(classroom.getName());
        classroom1.setYear(classroom.getYear());

        return classroomRepository.save(classroom1);
    }

    @GetMapping("/classrooms")
    public Iterable<Classroom> getClassroom(){
        return classroomRepository.findAll();
    }

    @GetMapping("/classrooms/{id}")
    public Classroom findClassroomById(@PathVariable Integer id) {
        Optional<Classroom> classroom = Optional.ofNullable(classroomRepository.findClassroomById(id));

        return classroom
                .orElseThrow(() -> new ResourceNotFoundException("Classroom with id: " + id + " not found"));
    }

    @DeleteMapping("/classrooms/{id}")
    public void deleteClassroom(@PathVariable Integer id){
        Optional<Classroom> classroom = Optional.ofNullable(Optional.ofNullable(classroomRepository.findClassroomById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Question with id: " + id + " not found")));

        classroomRepository.delete(classroomRepository.findClassroomById(id));
    }

    @PutMapping("/classrooms/{id}")
    public ResponseEntity<Object> updateClassroom(@PathVariable Integer id, @Valid @RequestBody ClassroomDto classroom){
        Optional<Classroom> classroomOptional = Optional.ofNullable(Optional.ofNullable(classroomRepository.findClassroomById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Question with id: " + id + " not found")));
        if(!classroomOptional.isPresent())
            return ResponseEntity.notFound().build();

        Classroom classroom1 = new Classroom();
        classroom1.setId(id);
        classroom1.setName(classroom.getName());
        classroom1.setYear(classroom.getYear());
        classroomRepository.save(classroom1);

        return ResponseEntity.noContent().build();
    }
}
