package com.upc.unistress.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TendenciaEmocionalDTO {
    private String emocion;
    private Double promedioNivel;
    private Long totalRegistros;
}
