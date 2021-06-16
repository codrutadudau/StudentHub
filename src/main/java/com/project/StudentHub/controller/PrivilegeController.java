package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Privilege;
import com.project.StudentHub.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class PrivilegeController {
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @PostMapping("/privileges")
    public Privilege addPrivilege(@Valid @RequestBody Privilege privilege){
        return privilegeRepository.save(privilege);
    }

    @GetMapping("/privileges")
    public Iterable<Privilege> privileges(){
        return privilegeRepository.findAll();
    }

    @GetMapping("/privileges/{id}")
    public Privilege findPrivilegeById(@PathVariable Integer id) {
        Optional<Privilege> privilege = Optional.ofNullable(privilegeRepository.findPrivilegeById(id));
        return privilege
                .orElseThrow(() -> new ResourceNotFoundException("Privilege with id: " + id + " not found"));
    }

    @DeleteMapping("/privileges/{id}")
    public void deleteQuestion(@PathVariable Integer id){
        Privilege privilege = Optional.ofNullable(privilegeRepository.findPrivilegeById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Privilege with id: " + id + " not found"));
        privilegeRepository.delete(privilege);
    }

    @PutMapping("/privileges/{id}")
    public ResponseEntity<Object> updatePrivilege(@Valid @RequestBody Privilege privilege, @PathVariable Integer id){
        Optional<Privilege> privilegeOptional = Optional.ofNullable(Optional.ofNullable(privilegeRepository.findPrivilegeById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Privilege with id: " + id + " not found")));
        if(!privilegeOptional.isPresent())
            return ResponseEntity.notFound().build();
        privilege.setId(id);
        privilegeRepository.save(privilege);
        return ResponseEntity.noContent().build();
    }
}
