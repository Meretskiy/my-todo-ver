package com.meretskiy.mytodover.services.impl;

import com.meretskiy.mytodover.beans.JwtTokenUtil;
import com.meretskiy.mytodover.dto.JwtResponse;
import com.meretskiy.mytodover.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class DefaultAuthServiceTest {

    public static final String USER_NAME = "userName";
    public static final String USER_PASS = "userPass";
    public static final String USER_TOKEN = "token";

    @InjectMocks
    private DefaultAuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;


    @Test
    void createAuthToken() {
        UserDetails mockUserDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userService.loadUserByUsername(USER_NAME)).thenReturn(mockUserDetails);
        Mockito.when(jwtTokenUtil.generateToken(mockUserDetails)).thenReturn(USER_TOKEN);

        JwtResponse actualToken = authService.createAuthToken(USER_NAME, USER_PASS);

        Assertions.assertNotNull(actualToken);
        Assertions.assertEquals(new JwtResponse(USER_TOKEN), actualToken);
    }
}