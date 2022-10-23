package com.meretskiy.mytodover.services.impl;

import com.meretskiy.mytodover.dto.UserDto;
import com.meretskiy.mytodover.model.Role;
import com.meretskiy.mytodover.model.User;
import com.meretskiy.mytodover.repositories.UserRepository;
import com.meretskiy.mytodover.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DefaultUserServiceTest {

    public static final String USER_NAME = "userName";
    public static final String USER_PASS = "userPass";
    public static final String USER_EMAIL = "userEmail";

    @InjectMocks
    private DefaultUserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private RoleService roleService;

    @Test
    public void findByUserNameTest() {
        final User mockUser = new User();
        mockUser.setUsername(USER_NAME);
        Mockito.when(userRepository.findByUsername(USER_NAME)).thenReturn(Optional.of(mockUser));

        User actualUser = userService.findByUsername(USER_NAME).get();

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(mockUser, actualUser);
        Mockito.verify(userRepository).findByUsername(USER_NAME);
    }

    @Test
    public void LoadUserByUsernameLest() {
        final User mockUser = new User();
        mockUser.setUsername(USER_NAME);
        mockUser.setPassword(USER_PASS);
        mockUser.setRoles(new ArrayList<>());
        Mockito.when(userRepository.findByUsername(USER_NAME)).thenReturn(Optional.of(mockUser));

        UserDetails userDetails = userService.loadUserByUsername(USER_NAME);

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(mockUser.getPassword(), userDetails.getPassword());
    }

    @Test
    public void createNewUserTest() {
        final User mockUser = new User();
        mockUser.setUsername(USER_NAME);
        mockUser.setPassword(USER_PASS);
        mockUser.setEmail(USER_EMAIL);
        mockUser.setRoles(new ArrayList<>());
        Role mockRole = Mockito.mock(Role.class);
        mockUser.getRoles().add(mockRole);
        Mockito.when(encoder.encode(USER_PASS)).thenReturn(USER_PASS);
        Mockito.when(roleService.getRoleForNewUser()).thenReturn(mockRole);

        User actualUser = userService.createNewUser(new UserDto(mockUser));

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(mockUser, actualUser);
        Mockito.verify(userRepository).save(actualUser);
    }
}