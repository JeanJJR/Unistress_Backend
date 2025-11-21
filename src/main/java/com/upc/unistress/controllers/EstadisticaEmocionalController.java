package com.upc.unistress.controllers;

import com.upc.unistress.dtos.EstadisticaDTO;
import com.upc.unistress.dtos.EstadisticaEmocionDTO;
import com.upc.unistress.dtos.EvolucionEmocionDTO;
import com.upc.unistress.dtos.TendenciaEmocionalDTO;
import com.upc.unistress.interfaces.IEstadisticaEmocionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
@RequestMapping("/api/estadistica")
public class EstadisticaEmocionalController {

    @Autowired
    private IEstadisticaEmocionalService estadisticaService;

    // Promedio de nivel emocional por emoción
    @GetMapping("/promedio/{usuarioId}")

    public ResponseEntity<List<EstadisticaEmocionDTO>> promedioPorEmocion(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(estadisticaService.promedioNivelPorEmocion(usuarioId));
    }

    // Evolución de emociones en el tiempo
    @GetMapping("/evolucion/{usuarioId}")

    public ResponseEntity<List<EvolucionEmocionDTO>> evolucion(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(estadisticaService.evolucionEmociones(usuarioId));
    }
    // Evolucion de emociones filtradas por rango de fechas
    @GetMapping("/evolucion-rango/{id}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<EvolucionEmocionDTO>> evolucionEntreFechasPorId(
            @PathVariable("id") Long usuarioId,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {

        return ResponseEntity.ok(estadisticaService.evolucionEmocionesEntreFechas(usuarioId, fechaInicio, fechaFin));
    }

    // Endpoint para psiclogo
    @GetMapping

    public ResponseEntity<List<TendenciaEmocionalDTO>> verTendencias(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(estadisticaService.obtenerTendencias(inicio, fin));
    }

    @GetMapping("/estadistica_admin")

    public ResponseEntity<EstadisticaDTO> obtenerEstadisticas() {
        return ResponseEntity.ok(estadisticaService.obtenerEstadisticas());
    }

}
