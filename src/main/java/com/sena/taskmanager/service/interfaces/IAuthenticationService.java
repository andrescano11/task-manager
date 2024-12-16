package com.sena.taskmanager.service.interfaces;

import com.sena.taskmanager.controller.auth.AuthenticationResponse;
import com.sena.taskmanager.dto.auth.LoginRequestDto;
import com.sena.taskmanager.dto.auth.RegisterRequestDto;

public interface IAuthenticationService {
    AuthenticationResponse register(String userRole, RegisterRequestDto request);
    AuthenticationResponse login(LoginRequestDto request);
}
