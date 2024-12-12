package com.sena.taskmanager.service;

import com.sena.taskmanager.config.JwtService;
import com.sena.taskmanager.controller.auth.AuthenticationResponse;
import com.sena.taskmanager.dto.auth.LoginRequestDto;
import com.sena.taskmanager.dto.auth.RegisterRequestDto;
import com.sena.taskmanager.entity.Role;
import com.sena.taskmanager.entity.User;
import com.sena.taskmanager.repository.RoleRepository;
import com.sena.taskmanager.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequestDto request) {
        List<Role> roles = List.of(roleRepository.getRoleByName("OPERATOR").orElseThrow(() -> new RuntimeException("Role not found")));
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .roles(roles)
                .build();

        userRepository.save(user);
        String accessToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(accessToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        Hibernate.initialize(user.getRoles());
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String accessToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(accessToken)
                    .build();
        }
        throw new BadCredentialsException("Invalid username or password");
    }
}
