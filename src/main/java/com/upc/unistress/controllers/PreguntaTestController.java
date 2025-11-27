package com.upc.unistress.controllers;

import com.upc.unistress.dtos.PreguntaTestDTO;
import com.upc.unistress.interfaces.IPreguntaTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api/pregunta")
public class PreguntaTestController {

    @Autowired
    private IPreguntaTestService service;

    @GetMapping

    public ResponseEntity<List<PreguntaTestDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ADMIN')")
    public ResponseEntity<String> registrar(@RequestBody PreguntaTestDTO dto) {
        service.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pregunta registrada correctamente");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Pregunta eliminada correctamente");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<PreguntaTestDTO> listarId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listId(id));
    }

    @GetMapping("/test/{testId}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<List<PreguntaTestDTO>> listarPorTest(@PathVariable Long testId) {
        return ResponseEntity.ok(service.listarPorTest(testId));
    }
}
