package com.upc.unistress.controllers;

import com.upc.unistress.dtos.RecomendacionDTO;
import com.upc.unistress.interfaces.IRecomendacionService;
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
@RequestMapping("/api/recomendacion")
public class RecomendacionController {

    @Autowired
    private IRecomendacionService recomendacionService;

    @GetMapping

    public ResponseEntity<List<RecomendacionDTO>> listar() {
        return ResponseEntity.ok(recomendacionService.listar());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<String> registrar(@RequestBody RecomendacionDTO dto) {
        recomendacionService.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Recomendación registrada correctamente");
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<String> actualizar(@RequestBody RecomendacionDTO dto) {
        recomendacionService.insertar(dto);
        return ResponseEntity.ok("Recomendación actualizada correctamente");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        recomendacionService.eliminar(id);
        return ResponseEntity.ok("Recomendación eliminada correctamente");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<RecomendacionDTO> listarId(@PathVariable Long id) {
        return ResponseEntity.ok(recomendacionService.listId(id));
    }

    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<List<RecomendacionDTO>> listarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(recomendacionService.listarPorTipo(tipo));
    }
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<List<RecomendacionDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(recomendacionService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/psicologo/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<List<RecomendacionDTO>> listarPorPsicologo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(recomendacionService.listarPorPsicologo(id));
    }


}
