package com.upc.unistress.security.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    private String username; // correo
    private String password;
}
