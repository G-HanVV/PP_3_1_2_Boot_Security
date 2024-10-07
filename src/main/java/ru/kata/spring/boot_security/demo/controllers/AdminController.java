package ru.kata.spring.boot_security.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public String showAdmin(ModelMap model, Principal principal){
        if (principal == null) return "redirect:/login";
        logger.info("showAdmin{}", principal.getName());
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "/admin";
    }

    @GetMapping (value = "/form")
    public String newUserForm(ModelMap model){
        logger.info("newUserForm");
        model.addAttribute("user", new User());
        return "/form";
    }

    @PostMapping("/user")
    public String createUser(@ModelAttribute("user") User user){
        logger.info("createUser{}", user.getName());
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(ModelMap model, @PathVariable("id") int id){
        logger.info("editUser{}", id);
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PutMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id){
        logger.info("updateUser{}", user.getName());
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id){
        logger.info("deleteUser{}", id);
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
