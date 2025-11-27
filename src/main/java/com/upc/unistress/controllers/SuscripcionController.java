package com.upc.unistress.controllers;

import com.upc.unistress.dtos.SuscripcionDTO;
import com.upc.unistress.interfaces.ISuscripcionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api/suscripcion")
public class SuscripcionController {

    @Autowired
    private ISuscripcionService suscripcionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<SuscripcionDTO>> listar() {
        List<SuscripcionDTO> suscripciones = suscripcionService.listar()
                .stream()
                .map(s -> modelMapper.map(s, SuscripcionDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(suscripciones);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<SuscripcionDTO> obtenerPorId(@PathVariable Long id) {
        SuscripcionDTO dto = suscripcionService.listId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<SuscripcionDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(suscripcionService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<String> registrar(@RequestBody SuscripcionDTO dto) {
        suscripcionService.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Suscripción creada correctamente");
    }

    @PutMapping
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<String> editar(@RequestBody SuscripcionDTO dto) {
        suscripcionService.insertar(dto);
        return ResponseEntity.ok("Suscripción actualizada correctamente");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        suscripcionService.eliminar(id);
        return ResponseEntity.ok("Suscripción eliminada correctamente");
    }
    @GetMapping("/activas")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<SuscripcionDTO>> listarActivas() {
        return ResponseEntity.ok(suscripcionService.listarActivas());
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<SuscripcionDTO> actualizar(
            @PathVariable Long id,
            @RequestBody SuscripcionDTO dto) {

        SuscripcionDTO actualizado = suscripcionService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }


}