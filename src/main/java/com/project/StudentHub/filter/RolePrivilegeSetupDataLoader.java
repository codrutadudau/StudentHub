package com.project.StudentHub.filter;

import com.project.StudentHub.model.Privilege;
import com.project.StudentHub.model.Role;
import com.project.StudentHub.model.User;
import com.project.StudentHub.repository.PrivilegeRepository;
import com.project.StudentHub.repository.RoleRepository;
import com.project.StudentHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class RolePrivilegeSetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(alreadySetup){
            return;
        }

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        if (null == userRepository.findByEmail("admin@admin.com")) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin");
            user.setPassword(passwordEncoder.encode("admin1234"));
            user.setEmail("admin@admin.com");
            user.setPhoneNumber("0712345678");
            user.setRole(adminRole);
            user.setEnabled(true);
            userRepository.save(user);

            alreadySetup = true;
        }
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name){
        Privilege role_privilege = privilegeRepository.findByName(name);
        if(role_privilege == null){
            role_privilege = new Privilege(name);
            privilegeRepository.save(role_privilege);
        }
        return role_privilege;
    }

    @Transactional
    public Role createRoleIfNotFound(String name, Collection<Privilege> privileges){
        Role user_role = roleRepository.findByName(name);
        if(user_role == null){
            user_role = new Role(name);
            user_role.setPrivileges(privileges);
            roleRepository.save(user_role);
        }
        return user_role;
    }
}
