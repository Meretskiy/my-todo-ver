package com.meretskiy.mytodover.dto;

import com.meretskiy.mytodover.model.Note;
import com.meretskiy.mytodover.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String username;
    private String password;
    private String email;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }
}
