package com.zergatstage.diploma_server.ssl.controller;

import com.zergatstage.diploma_server.ssl.domain.AuthenticationResponse;
import com.zergatstage.diploma_server.ssl.domain.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorityRESRController {
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody LoginForm request) {
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .accessToken(request.getUsername()+ "authenticated")
                .build());
    }
}
