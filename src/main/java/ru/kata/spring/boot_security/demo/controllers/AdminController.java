package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getUsersList());
        return "/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/new_user")
    public String newUser(Model model) {
        User user = new User();
        List<Role> roles = userService.getRolesList();
        model.addAttribute("user", user);
        model.addAttribute("rolesList", roles);
        return "admin/new";
    }

    @PostMapping("/save_new_user")
    public String saveUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit_user/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> roles = userService.getRolesList();
        model.addAttribute("user", user);
        model.addAttribute("rolesList", roles);
        return "admin/edit";
    }

    @PostMapping("/update_user")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user.getId(), user);
        return "redirect:/admin";
    }
}
