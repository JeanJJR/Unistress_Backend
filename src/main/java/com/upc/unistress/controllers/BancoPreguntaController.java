package com.upc.unistress.controllers;
import com.upc.unistress.dtos.BancoPreguntaDTO;
import com.upc.unistress.interfaces.IBancoPreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200",
allowCredentials = "true",
exposedHeaders = "Authorization")
@RequestMapping("/api/banco")
public class BancoPreguntaController {

    @Autowired
    private IBancoPreguntaService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<List<BancoPreguntaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    @PreAuthorize("hasRole('PSICOLOGO')")
    public ResponseEntity<String> registrar(@RequestBody BancoPreguntaDTO dto) {
        service.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pregunta registrada correctamente");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PSICOLOGO')")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Pregunta eliminada correctamente");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PSICOLOGO')")
    public ResponseEntity<BancoPreguntaDTO> listarId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listId(id));
    }


    @GetMapping("/psicologo/{psicologoId}")
    @PreAuthorize("hasRole('PSICOLOGO')")
    public ResponseEntity<List<BancoPreguntaDTO>> listarPorPsicologo(@PathVariable Long psicologoId) {
        return ResponseEntity.ok(service.listarPorPsicologo(psicologoId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PSICOLOGO')")
    public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody BancoPreguntaDTO dto) {
        service.actualizar(id, dto);
        return ResponseEntity.ok("Pregunta actualizada correctamente");
    }




}