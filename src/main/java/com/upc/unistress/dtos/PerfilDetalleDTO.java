package com.upc.unistress.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerfilCompletoDTO {
    private Long idUsuario;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contrasena;  // NO SE DEVUELVE
    private String telefono;
    private String rol;
    private PerfilDTO perfil;   // academico o profesional
    private SuscripcionDTO suscripcion;    // PREMIUM, FREE, o null si es psiclogo
}

