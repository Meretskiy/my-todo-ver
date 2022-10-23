package com.meretskiy.mytodover.services;

import com.meretskiy.mytodover.dto.JwtResponse;

public interface AuthService {

    JwtResponse createAuthToken(String userName, String password);
}
