package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    void addRole(Role role);

    void updateUser(Long id, User user);

    void removeUserById(Long id);

    User getUserById(Long id);

    List<User> getUsersList();

    List<Role> getRolesList();

    User getUserByUsername(String username);

}
