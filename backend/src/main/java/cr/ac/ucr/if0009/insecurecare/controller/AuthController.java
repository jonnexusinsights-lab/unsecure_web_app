package cr.ac.ucr.if0009.insecurecare.controller;

import cr.ac.ucr.if0009.insecurecare.dto.LoginRequest;
import cr.ac.ucr.if0009.insecurecare.dto.LoginResponse;
import cr.ac.ucr.if0009.insecurecare.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.autenticar(request);
        return ResponseEntity.ok(response);
    }
}

