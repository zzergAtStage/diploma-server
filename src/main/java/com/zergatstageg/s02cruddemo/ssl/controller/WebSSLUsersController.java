package com.zergatstageg.s02cruddemo.ssl.controller;

import com.zergatstageg.s02cruddemo.ssl.domain.User;
import com.zergatstageg.s02cruddemo.ssl.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class WebSSLUsersController {
    @Autowired
    private final UserService userService;

    private final UserDetailsService userDetailsService;
    public WebSSLUsersController(UserService userService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
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

    @GetMapping("/user-details")
    public String showUserDetails(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        Optional<User> user = userService.findByUserName(username);
        model.addAttribute("user", user);
        if (user.isEmpty()) model.addAttribute("no-user-found", "No user found there...");
        return "user-details";
    }
    @GetMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestParam("username") String username,
                                       @RequestParam("password") String password ){
        return new ResponseEntity<>("let my people go...",HttpStatus.ACCEPTED);
    }
}
