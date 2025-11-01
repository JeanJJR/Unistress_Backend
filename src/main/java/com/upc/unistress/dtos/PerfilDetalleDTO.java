package com.upc.unistress.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDetalleDTO {
    private Long usuarioId;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contraseña;
    private String telefono;

    private String tipoPerfil; // ESTUDIANTE o PSICOLOGO
    private String universidad;
    private String carrera;
    private String ciclo;
    private String estadoAcademico;
    private String especialidad;
    private String colegiatura;
    private Integer añosExperiencia;
    private String telefonoConsulta;
    private String fotoUrl;
    private String descripcion;

}
