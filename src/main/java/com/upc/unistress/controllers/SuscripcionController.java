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
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
@RequestMapping("/api/suscripcion")
public class SuscripcionController {

    @Autowired
    private ISuscripcionService suscripcionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping

    public ResponseEntity<List<SuscripcionDTO>> listar() {
        List<SuscripcionDTO> suscripciones = suscripcionService.listar()
                .stream()
                .map(s -> modelMapper.map(s, SuscripcionDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(suscripciones);
    }

    @GetMapping("/{id}")

    public ResponseEntity<SuscripcionDTO> obtenerPorId(@PathVariable Long id) {
        SuscripcionDTO dto = suscripcionService.listId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/usuario/{usuarioId}")

    public ResponseEntity<List<SuscripcionDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(suscripcionService.listarPorUsuario(usuarioId));
    }

    @PostMapping

    public ResponseEntity<String> registrar(@RequestBody SuscripcionDTO dto) {
        suscripcionService.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Suscripción creada correctamente");
    }

    @PutMapping

    public ResponseEntity<String> editar(@RequestBody SuscripcionDTO dto) {
        suscripcionService.insertar(dto);
        return ResponseEntity.ok("Suscripción actualizada correctamente");
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        suscripcionService.eliminar(id);
        return ResponseEntity.ok("Suscripción eliminada correctamente");
    }
    @GetMapping("/activas")

    public ResponseEntity<List<SuscripcionDTO>> listarActivas() {
        return ResponseEntity.ok(suscripcionService.listarActivas());
    }


}