package com.upc.unistress.interfaces;

import com.upc.unistress.dtos.EstadisticaEmocionDTO;
import com.upc.unistress.dtos.EvolucionEmocionDTO;
import com.upc.unistress.dtos.TendenciaEmocionalDTO;
import com.upc.unistress.dtos.EstadisticaDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IEstadisticaEmocionalService {
    List<EstadisticaEmocionDTO> promedioNivelPorEmocion(Long usuarioId);
    List<EvolucionEmocionDTO> evolucionEmociones(Long usuarioId);
    List<EvolucionEmocionDTO> evolucionEmocionesEntreFechas(
            Long usuarioId,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );

    // Tendencias generales en un rango de fechas
    List<TendenciaEmocionalDTO> obtenerTendencias(LocalDateTime inicio, LocalDateTime fin);

    EstadisticaDTO obtenerEstadisticas();
}
