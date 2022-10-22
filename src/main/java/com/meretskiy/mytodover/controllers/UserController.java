package com.meretskiy.mytodover.controllers;

import com.meretskiy.mytodover.dto.JwtRequest;
import com.meretskiy.mytodover.dto.JwtResponse;
import com.meretskiy.mytodover.dto.UserDto;
import com.meretskiy.mytodover.exceptions_handling.TodoverError;
import com.meretskiy.mytodover.services.AuthService;
import com.meretskiy.mytodover.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuthService authService;

    @PostMapping("/authentication")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            JwtResponse jwtResponse = authService.createAuthToken(authRequest.getUsername(), authRequest.getPassword());
            return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new TodoverError(HttpStatus.UNAUTHORIZED.value(),
                    "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody UserDto userDto) {
        try {
            userService.createNewUser(userDto);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new TodoverError(HttpStatus.BAD_REQUEST.value(),
                    "Incorrect username or email"), HttpStatus.BAD_REQUEST);
        }
    }
}
