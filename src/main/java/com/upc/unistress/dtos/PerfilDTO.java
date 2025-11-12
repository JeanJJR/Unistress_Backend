package com.upc.unistress.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
    private Long id;
    private Long usuarioId;
    private String tipoPerfil; // ESTUDIANTE o PSICOLOGO
    private String universidad;
    private String carrera;
    private String ciclo;
    private String estadoAcademico;
    private String especialidad;
    private String colegiatura;
    private Integer anosExperiencia;
    private String fotoUrl;
    private String descripcion;

}