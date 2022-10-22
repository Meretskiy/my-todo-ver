package com.meretskiy.mytodover.services;

import com.meretskiy.mytodover.beans.JwtTokenUtil;
import com.meretskiy.mytodover.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    public JwtResponse createAuthToken(String userName, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        UserDetails userDetails = userService.loadUserByUsername(userName);
        return new JwtResponse(jwtTokenUtil.generateToken(userDetails));
    }
}
