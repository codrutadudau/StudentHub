package com.project.StudentHub.controller;

import com.project.StudentHub.exception.ResourceNotFoundException;
import com.project.StudentHub.model.Role;
import com.project.StudentHub.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/roles")
    public Role addRole (@Valid @RequestBody Role role){
        return roleRepository.save(role);
    }

    @GetMapping("/roles")
    public Iterable<Role> roles(){
        return roleRepository.findAll();
    }

    @GetMapping("/roles/{id}")
    public Role findRoleById(@PathVariable Integer id) {
        Optional<Role> role = Optional.ofNullable(roleRepository.findRoleById(id));
        return role
                .orElseThrow(() -> new ResourceNotFoundException("Role with id: " + id + " not found"));
    }

    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable Integer id){
        Role roleDelete = Optional.ofNullable(roleRepository.findRoleById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Role with id: " + id + " not found"));
        roleRepository.delete(roleDelete);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<Object> updateRole(@Valid @RequestBody Role role, @PathVariable Integer id){
        Optional<Role> roleOptional = Optional.ofNullable(Optional.ofNullable(roleRepository.findRoleById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Role with id: " + id + " not found")));
        if(!roleOptional.isPresent())
            return ResponseEntity.notFound().build();
        role.setId(id);
        roleRepository.save(role);

        return ResponseEntity.noContent().build();
    }
}
