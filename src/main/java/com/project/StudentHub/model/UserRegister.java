//package com.project.StudentHub.model;
//
//public class UserRegister {
//    public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException {
//
//        if (emailExist(accountDto.getEmail())) {
//            throw new EmailExistsException
//                    ("There is an account with that email adress: " + accountDto.getEmail());
//        }
//        User user = new User();
//
//        user.setFirstName(accountDto.getFirstName());
//        user.setLastName(accountDto.getLastName());
//        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
//        user.setEmail(accountDto.getEmail());
//
//        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
//        return repository.save(user);
//    }
//}
