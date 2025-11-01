package com.upc.unistress.controllers;

import com.upc.unistress.dtos.RolDTO;
import com.upc.unistress.interfaces.IRolService;
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
@RequestMapping("/api/rol")
public class RolController {

    @Autowired
    private IRolService rolService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping

    public ResponseEntity<List<RolDTO>> listar() {
        List<RolDTO> roles = rolService.listar()
                .stream()
                .map(r -> modelMapper.map(r, RolDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }

    @PostMapping

    public ResponseEntity<String> registrar(@RequestBody RolDTO dto) {
        rolService.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Rol registrado correctamente");
    }

    @PutMapping

    public ResponseEntity<String> editar(@RequestBody RolDTO dto) {
        rolService.insertar(dto);
        return ResponseEntity.ok("Rol actualizado correctamente");
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        rolService.eliminar(id);
        return ResponseEntity.ok("Rol eliminado exitosamente");
    }

    @GetMapping("/{id}")

    public ResponseEntity<RolDTO> listarId(@PathVariable("id") Long id) {
        RolDTO rol = rolService.listId(id);
        return ResponseEntity.ok(rol);
    }

    @GetMapping("/buscar")

    public ResponseEntity<RolDTO> buscarPorNombre(@RequestParam String tipoRol) {
        RolDTO rol = rolService.findByTipoRol(tipoRol);
        return ResponseEntity.ok(rol);
    }
}
