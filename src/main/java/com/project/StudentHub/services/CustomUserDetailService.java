package com.project.StudentHub.services;

import com.project.StudentHub.exception.EmailExistsException;
import com.project.StudentHub.model.Privilege;
import com.project.StudentHub.model.Role;
import com.project.StudentHub.model.UserDto;
import com.project.StudentHub.repository.RoleRepository;
import com.project.StudentHub.repository.UserRepository;
import com.project.StudentHub.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getAuthorities(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException {

        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException
                    ("There is an account with that email adress: " + accountDto.getEmail());
        }
        User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());

        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }
}
