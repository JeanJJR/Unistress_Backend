package com.upc.unistress.dtos;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BancoPreguntaDTO {
    private Long id;
    private String enunciado;
    private Long psicologoId;
}
