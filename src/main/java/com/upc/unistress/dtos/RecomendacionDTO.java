package com.upc.unistress.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecomendacionDTO {
    private Long id;
    private String mensaje;
    private String tipo;
    private Long registroEmocionalId; // Para asociar si aplica
    private Long usuarioId;
}
