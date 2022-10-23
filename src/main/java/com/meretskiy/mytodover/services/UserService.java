package com.meretskiy.mytodover.services;

import com.meretskiy.mytodover.dto.UserDto;
import com.meretskiy.mytodover.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findByUsername(String username);

    User createNewUser(UserDto userDto);
}
