package com.upc.unistress.controllers;

import com.upc.unistress.dtos.RegistroEmocionalDTO;
import com.upc.unistress.interfaces.IRegistroEmocionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
@RequestMapping("/api/registro-emocional")
public class RegistroEmocionalController {

    @Autowired
    private IRegistroEmocionalService registroService;

    @GetMapping

    public ResponseEntity<List<RegistroEmocionalDTO>> listar() {
        return ResponseEntity.ok(registroService.listar());
    }

    @PostMapping

    public ResponseEntity<String> registrar(@RequestBody RegistroEmocionalDTO dto) {
        registroService.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registro emocional creado correctamente");
    }

    @PutMapping
    public ResponseEntity<String> actualizar(@RequestBody RegistroEmocionalDTO dto) {
        registroService.insertar(dto);
        return ResponseEntity.ok("Registro emocional actualizado correctamente");
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        registroService.eliminar(id);
        return ResponseEntity.ok("Registro emocional eliminado correctamente");
    }

    @GetMapping("/{id}")

    public ResponseEntity<RegistroEmocionalDTO> listarId(@PathVariable Long id) {
        return ResponseEntity.ok(registroService.listId(id));
    }

    @GetMapping("/usuario/{usuarioId}")

    public ResponseEntity<List<RegistroEmocionalDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(registroService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/rango-fechas")

    public ResponseEntity<List<RegistroEmocionalDTO>> listarPorFechas(@RequestParam LocalDateTime inicio,
                                                                      @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(registroService.listarPorRangoFechas(inicio, fin));
    }


}
