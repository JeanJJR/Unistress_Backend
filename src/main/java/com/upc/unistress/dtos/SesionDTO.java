package com.upc.unistress.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SesionDTO {
    private Long id;
    private Long psicologoId;
    private Long estudianteId;
    private LocalDate fecha;
    private LocalTime hora;
    private String mensaje;
    private String estado;
}