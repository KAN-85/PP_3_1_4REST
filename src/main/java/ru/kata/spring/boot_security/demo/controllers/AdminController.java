package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;


@Controller
public class AdminController {
    private final UserService userService;


    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.listUser());
        return "admin/users";
    }

    @GetMapping("admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/new_user")
    public String newUser(Model model) {
        User user = new User();
        List<Role> roles = userService.listRole();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roles);
        return "admin/new";
    }

    @PostMapping("admin/save_new_user")
    public String saveNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = userService.listRole();
            model.addAttribute("allRoles", roles);
            return "admin/new";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/edit_user/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> roles = userService.listRole();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roles);
        return "admin/edit";
    }

    @PostMapping("admin/update_user")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = userService.listRole();
            model.addAttribute("allRoles", roles);
            return "admin/edit";
        }
        userService.updateUser(user.getId(), user);
        return "redirect:/admin";
    }
}
