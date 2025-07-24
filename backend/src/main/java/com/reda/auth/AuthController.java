package com.reda.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION , response.token())
                .body(response);
    }
}
