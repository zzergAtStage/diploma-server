package com.zergatstageg.s02cruddemo.controller;

import com.zergatstageg.s02cruddemo.domain.User;
import com.zergatstageg.s02cruddemo.services.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.stereotype.Controller
public class Controller {
    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;

    }
    @GetMapping("/users")
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user){
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(User user){
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable int id, Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @GetMapping("/user-delete/{id}")
    public String deleteUserById(@PathVariable int id, Model model){
        userService.deleteUserById(id);
        return "redirect:/users";
    }


    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute User user){
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/authenticate")
    public String authenticate(@RequestParam("username") String username,
                               @RequestParam("password") String password ){
        return "move along!: " + username + ":" + password;
    }
}
