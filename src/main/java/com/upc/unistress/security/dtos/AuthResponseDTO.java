package com.upc.unistress.security.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AuthResponseDTO {
    private String jwt;
    private Set<String> roles;
    private Long id;
}
