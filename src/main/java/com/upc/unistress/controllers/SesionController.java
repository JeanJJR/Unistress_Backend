package com.upc.unistress.controllers;

import com.upc.unistress.dtos.SesionDTO;
import com.upc.unistress.interfaces.ISesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
@RequestMapping("/api/sesion")
public class SesionController {

    @Autowired
    private ISesionService sesionService;

    // 1. Agendar sesión
    @PreAuthorize("hasRole('ESTUDIANTE')")
    @PostMapping
    public ResponseEntity<SesionDTO> crear(@RequestBody SesionDTO dto) {
        SesionDTO sesionCreada = sesionService.crearSesion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sesionCreada);
    }

    // 2. Listar todas las sesiones (admin)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<SesionDTO>> listar() {
        return ResponseEntity.ok(sesionService.listar());
    }

    // 3. Listar proximas sesiones de un estudiante en un rango de fechas
    @GetMapping("/estudiante/{id}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<SesionDTO>> listarPorEstudianteYRango(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(sesionService.listarPorEstudianteYRango(id, fechaInicio, fechaFin));
    }

    // 4. Listar historial de sesiones
    @PreAuthorize("hasAnyRole('PSICOLOGO','ESTUDIANTE')")
    @GetMapping("/historial/estudiante/{id}")
    public ResponseEntity<List<SesionDTO>> historialPorEstudiante(@PathVariable Long id) {
        List<SesionDTO> historial = sesionService.listarHistorialPorEstudiante(id);
        return ResponseEntity.ok(historial);
    }


    // 5. Eliminar sesión - ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        sesionService.eliminar(id);
    }



    // 6. Filtrar por rango de fechas (ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/mis-sesiones/filtrar")
    public ResponseEntity<List<SesionDTO>> filtrarMisSesionesPorRango(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(sesionService.filtrarSesionesPorRangoAutenticado(inicio, fin));
    }

    // 7. Aceptar sesión
    @PreAuthorize("hasRole('PSICOLOGO')")
    @PutMapping("/aceptar/{id}")
    public ResponseEntity<String> aceptarSesion(@PathVariable Long id) {
        sesionService.aceptarSesion(id);
        return ResponseEntity.ok("Sesión aceptada correctamente");
    }

    //8.-Cancelar sesión
    @PreAuthorize("hasRole('ESTUDIANTE')")
    @DeleteMapping ("/cancelar/{id}")
    public ResponseEntity<String> cancelarSesion(@PathVariable Long id, @RequestBody SesionDTO dto) {

        sesionService.cancelarSesion(id, dto.getEstudianteId());
        return ResponseEntity.ok("Sesión cancelada correctamente");
    }

}
