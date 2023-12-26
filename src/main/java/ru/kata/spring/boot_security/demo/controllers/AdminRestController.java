package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exceptions.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getUsersList(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchUserException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        try {
            userService.updateUser(id, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchUserException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/roles")
    ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(userService.getRolesList(), HttpStatus.OK);
    }
}
