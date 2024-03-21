package com.zergatstageg.s02cruddemo.ssl;

import com.zergatstageg.s02cruddemo.domain.LoginForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthorityTestController {
    @GetMapping("/welcome")
    public String welcome() {
        return "ssl/welcome";
    }
    @PostMapping("/authenticate")
    public ResponseEntity<String> checkAuth(){
        return new ResponseEntity<>("passed", HttpStatus.OK);
    }

}