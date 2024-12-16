package com.sena.taskmanager.controller.auth;

import com.sena.taskmanager.dto.auth.LoginRequestDto;
import com.sena.taskmanager.dto.auth.RegisterRequestDto;
import com.sena.taskmanager.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register/{userRole}")
    @PreAuthorize("hasPermission('role', #userRole)")
    @Tag(name = "Auth", description = "Registrar un usuario")
    public ResponseEntity<AuthenticationResponse> register(@PathVariable String userRole, @RequestBody RegisterRequestDto request) {
        return new ResponseEntity<>(authenticationService.register(userRole, request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Tag(name = "Auth", description = "Iniciar sesión")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequestDto request) {
        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
    }
}
