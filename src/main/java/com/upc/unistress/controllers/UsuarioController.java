package com.upc.unistress.controllers;

import com.upc.unistress.dtos.EstudianteDTO;
import com.upc.unistress.dtos.PsicologoDTO;
import com.upc.unistress.dtos.UsuarioDTO;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.interfaces.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;
// AMBOS USUARIOS
    @GetMapping

    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<UsuarioDTO> usuarios = usuarioService.list()
                .stream()
                .map(u -> modelMapper.map(u, UsuarioDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping

    public ResponseEntity<String> registrar(@RequestBody UsuarioDTO dto) {
        usuarioService.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente");
    }

    @PutMapping

    public ResponseEntity<String> editar(@RequestBody UsuarioDTO dto) {
        usuarioService.insert(dto); // tambien se puede usar save para guardar o actualizar, pero recomentable es insert
        return ResponseEntity.ok("Usuario actualizado correctamente");
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        usuarioService.delete(id);
        return ResponseEntity.ok("Usuario eliminado exitosamente");
    }

    @GetMapping("/{id}")

    public ResponseEntity<UsuarioDTO> listarId(@PathVariable("id") Long id) {
        UsuarioDTO usuario = usuarioService.listId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/buscar")

    public ResponseEntity<Optional<UsuarioDTO>> buscarPorCorreo(@RequestParam String correo) {
        Optional<UsuarioDTO> usuario = usuarioService.findByCorreo(correo);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/por-rol")

    public ResponseEntity<List<UsuarioDTO>> listarPorRol(@RequestParam String tipoRol) {
        List<UsuarioDTO> usuarios = usuarioService.listarPorRol(tipoRol);
        return ResponseEntity.ok(usuarios);
    }

    // registrar estudiante
    @PostMapping("/estudiante")

    public ResponseEntity<String> registrarEstudiante(@RequestBody EstudianteDTO dto) {
        usuarioService.registrarEstudiante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Estudiante registrado correctamente");
    }

    // registrar psicologo
    @PostMapping("/psicologo")

    public ResponseEntity<String> registrarPsicologo(@RequestBody PsicologoDTO dto) {
        usuarioService.registrarPsicologo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Psicologo registrado correctamente");
    }

    // psicologos disponibles
    // Nuevo endpoint para listar psicólogos disponibles

    @GetMapping("/psicologos")
    public ResponseEntity<List<UsuarioDTO>> listarPsicologos() {
        return ResponseEntity.ok(usuarioService.listarPorRol("ROLE_PSICOLOGO"));
    }

    // Listar estudiantes disponibles
    @GetMapping("/estudiantes")

    public ResponseEntity<List<UsuarioDTO>> listarEstudiantes() {
        return ResponseEntity.ok(usuarioService.listarPorRol("ROLE_ESTUDIANTE"));
    }


}
