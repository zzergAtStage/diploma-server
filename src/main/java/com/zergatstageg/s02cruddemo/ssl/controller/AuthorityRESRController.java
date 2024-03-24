package com.zergatstageg.s02cruddemo.ssl.controller;

import com.zergatstageg.s02cruddemo.ssl.domain.AuthenticationResponse;
import com.zergatstageg.s02cruddemo.ssl.domain.Earthquake;
import com.zergatstageg.s02cruddemo.ssl.domain.LoginForm;
import com.zergatstageg.s02cruddemo.ssl.repository.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorityRESRController {
    private final WarehouseService warehouseService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody LoginForm request) {
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .accessToken(request.getUsername()+ "authenticated")
                .build());
    }

    @GetMapping("/earthquakes")
    public ResponseEntity<Earthquake> getEarthquakes(@RequestParam("depth") int depth){
        return new ResponseEntity<Earthquake>(warehouseService.getEarthquakesByDepth(depth), HttpStatus.OK);
    }
}
