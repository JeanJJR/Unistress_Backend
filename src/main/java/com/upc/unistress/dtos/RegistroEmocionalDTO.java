package com.upc.unistress.dtos;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroEmocionalDTO {
    private Long id;
    private Long usuarioId;
    private String emocion;
    private Integer nivel;
    private String descripcion;
}
