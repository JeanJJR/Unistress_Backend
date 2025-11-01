package com.upc.unistress.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticaDTO {
    private Long totalUsuarios;
    private Long totalPsicologos;
    private Long totalEstudiantes;
    private Long totalSesiones;
}
