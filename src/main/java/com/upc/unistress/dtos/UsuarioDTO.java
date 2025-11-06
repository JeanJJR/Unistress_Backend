package com.upc.unistress.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contrasena;
    private String telefono;
    private RolDTO rol;
    private PerfilDTO perfil;
}
