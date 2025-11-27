package com.upc.unistress.controllers;

import com.upc.unistress.dtos.NotificacionDTO;
import com.upc.unistress.interfaces.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api/notificacion")
public class NotificacionController {

    @Autowired
    private INotificacionService notificacionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<List<NotificacionDTO>> listar() {
        return ResponseEntity.ok(notificacionService.listar());
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<List<NotificacionDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<NotificacionDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.listId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<String> crear(@RequestBody NotificacionDTO dto) {
        notificacionService.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Notificación creada correctamente");
    }

    @PutMapping("/leer/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<String> marcarLeida(@PathVariable Long id) {
        notificacionService.marcarComoLeida(id);
        return ResponseEntity.ok("Notificación marcada como leída");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ESTUDIANTE')")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.ok("Notificación eliminada correctamente");
    }


}
