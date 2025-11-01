package com.upc.unistress.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {
    private String nombre;
    private String apellidos;
    private String correo;
    private String contraseña;
    private String telefono;

    // Campos propios del perfil de estudiante
    private String universidad;
    private String carrera;
    private String ciclo;
    private String estadoAcademico;
}

