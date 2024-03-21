package com.zergatstageg.s02cruddemo.ssl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoggingController {

    @GetMapping("/logging")
    public ResponseEntity<String> logging() {
        return new ResponseEntity<>("logging/zerAtStage", HttpStatus.OK);
    }

}
