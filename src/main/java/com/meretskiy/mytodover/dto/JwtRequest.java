package com.meretskiy.mytodover.dto;

import lombok.Data;

@Data
public class JwtRequest {

    private String username;
    private String password;
}
