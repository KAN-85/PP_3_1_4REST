package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private final UserService userService;


    @Autowired
    public Init(UserService userService) {
        this.userService = userService;

    }

    @PostConstruct
    public void initDb() {
        Role roleAdmin = new Role(1L, "ROLE_ADMIN");
        Role roleUser = new Role(2L, "ROLE_USER");
        Set<Role> adminRolesSet = new HashSet<>();
        Set<Role> userRolesSet = new HashSet<>();

        userService.addRole(roleAdmin);
        userService.addRole(roleUser);

        adminRolesSet.add(roleAdmin);
        adminRolesSet.add(roleUser);
        userRolesSet.add(roleUser);

        User admin = new User("Alexey", "Kuznetsov", "alex228@ya.ru", "admin", "admin", adminRolesSet);
        admin.setId(1L);

        User user = new User("Semen", "Shniperson", "tsahalforever@gmail.com", "user", "user", userRolesSet);
        user.setId(2L);

        userService.saveUser(admin);
        userService.saveUser(user);
    }
}
