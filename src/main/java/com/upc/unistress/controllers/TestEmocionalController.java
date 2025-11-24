package com.upc.unistress.controllers;

import com.upc.unistress.dtos.TestEmocionalDTO;
import com.upc.unistress.interfaces.ITestEmocionalService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/test")
public class TestEmocionalController {

    @Autowired
    private ITestEmocionalService service;

    @GetMapping
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<TestEmocionalDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<String> registrar(@RequestBody TestEmocionalDTO dto) {
        service.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Test emocional registrado correctamente");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Test emocional eliminado correctamente");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<TestEmocionalDTO> listarId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<TestEmocionalDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @GetMapping("/rango-fechas")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<TestEmocionalDTO>> listarPorRangoFechas(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin) {
        return ResponseEntity.ok(service.listarPorRangoFechas(inicio, fin));
    }
    // pregutnas a resolver por parte del estudiante

    @PostMapping("/resolver")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<TestEmocionalDTO> resolver(@RequestBody TestEmocionalDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.resolver(dto));
    }

    @GetMapping("/promedio/{usuarioId}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<Double> obtenerPromedioPorUsuario(@PathVariable Long usuarioId) {
        Double promedio = service.obtenerPromedioPorUsuario(usuarioId);
        return ResponseEntity.ok(promedio);
    }



}
