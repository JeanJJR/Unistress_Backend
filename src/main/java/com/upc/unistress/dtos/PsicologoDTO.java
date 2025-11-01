package com.upc.unistress.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PsicologoDTO {
    // Datos de Usuario
    private String nombre;
    private String apellidos;
    private String correo;
    private String contraseña;
    private String telefono;

    // Datos de Perfil
    private String especialidad;
    private String colegiatura;
    private Integer añosExperiencia;

}