package com.zergatstage.diploma_server.ssl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorityController {
    @GetMapping("/welcome")
    public String welcome() {
        return "ssl/welcome";
    }
    @PostMapping("/authenticate")
    public ResponseEntity<String> checkAuth(){
        return new ResponseEntity<>("passed", HttpStatus.OK);
    }

}