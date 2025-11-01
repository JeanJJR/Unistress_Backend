package com.upc.unistress.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvolucionEmocionDTO {
    private LocalDate fecha;
    private String emocion;
    private Integer nivel;
}
