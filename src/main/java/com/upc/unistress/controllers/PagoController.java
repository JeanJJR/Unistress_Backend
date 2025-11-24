package com.upc.unistress.controllers;

import com.upc.unistress.dtos.PagoDTO;
import com.upc.unistress.interfaces.IPagoService;
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
@RequestMapping("/api/pago")
public class PagoController {

    @Autowired
    private IPagoService pagoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<PagoDTO>> listar() {
        List<PagoDTO> pagos = pagoService.listar()
                .stream()
                .map(p -> modelMapper.map(p, PagoDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<PagoDTO> obtenerPorId(@PathVariable Long id) {
        PagoDTO dto = pagoService.listId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/suscripcion/{suscripcionId}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<PagoDTO>> listarPorSuscripcion(@PathVariable Long suscripcionId) {
        return ResponseEntity.ok(pagoService.listarPorSuscripcion(suscripcionId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<String> registrar(@RequestBody PagoDTO dto) {
        pagoService.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pago registrado correctamente");
    }

    @PutMapping
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<String> editar(@RequestBody PagoDTO dto) {
        pagoService.insertar(dto);
        return ResponseEntity.ok("Pago actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.ok("Pago eliminado correctamente");
    }

}
