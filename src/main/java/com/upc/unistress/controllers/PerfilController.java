package com.upc.unistress.controllers;

import com.upc.unistress.dtos.PerfilDTO;
import com.upc.unistress.dtos.PerfilDetalleDTO;
import com.upc.unistress.interfaces.IPerfilService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
@RequestMapping("/api/perfil")
public class PerfilController {

    @Autowired
    private IPerfilService perfilService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<List<PerfilDTO>> listar() {
        List<PerfilDTO> perfiles = perfilService.list()
                .stream()
                .map(p -> modelMapper.map(p, PerfilDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(perfiles);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<String> registrar(@RequestBody PerfilDTO dto) {
        perfilService.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Perfil registrado correctamente");
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<String> editar(@RequestBody PerfilDTO dto) {
        perfilService.insert(dto);
        return ResponseEntity.ok("Perfil actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        perfilService.delete(id);
        return ResponseEntity.ok("Perfil eliminado exitosamente");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<PerfilDTO> listarId(@PathVariable("id") Long id) {
        PerfilDTO perfil = perfilService.listId(id);
        return ResponseEntity.ok(perfil);
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<Optional<PerfilDTO>> buscarPorUsuario(@PathVariable("usuarioId") int usuarioId) {
        Optional<PerfilDTO> perfil = perfilService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(perfil);
    }

    @GetMapping("/por-tipo")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<List<PerfilDTO>> listarPorTipo(@RequestParam String tipoPerfil) {
        List<PerfilDTO> perfiles = perfilService.listarPorTipoPerfil(tipoPerfil);
        return ResponseEntity.ok(perfiles);
    }
    /*
    // Subir foto (en este ejemplo, recibimos solo la URL)
    @PutMapping("/{perfilId}/foto")

    public ResponseEntity<String> actualizarFoto(@PathVariable Long perfilId, @RequestParam String fotoUrl) {
        perfilService.actualizarFoto(perfilId, fotoUrl);
        return ResponseEntity.ok("Foto de perfil actualizada correctamente");
    }
*/
    /*
    ///  subir foto reales eso ya lo vere si lo aplico o no en el fronted
    @PutMapping("/{id}/foto")

    public ResponseEntity<String> actualizarFoto(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {
        perfilService.actualizarFoto(id, archivo);
        return ResponseEntity.ok("Foto actualizada correctamente");
    }
    */
    @PostMapping("/perfilusuario/{usuarioId}/foto")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<?> actualizarFoto(
            @PathVariable int usuarioId,
            @RequestParam("archivo") MultipartFile archivo) {
        perfilService.actualizarFoto(usuarioId, archivo);
        return ResponseEntity.ok("Foto actualizada");
    }




    // Obtener perfil detallado por usuario
    @GetMapping("/perfilusuario/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<PerfilDetalleDTO> obtenerPerfilPorUsuario(@PathVariable int id) {
        PerfilDetalleDTO perfil = perfilService.obtenerPerfilPorUsuario(id);
        return ResponseEntity.ok(perfil);
    }

     // actualziar perfil
    @PutMapping("/perfilusuario/{id}")
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN','ESTUDIANTE')")
    public ResponseEntity<String> actualizarPerfil(
            @PathVariable int id,
            @RequestBody PerfilDetalleDTO dto) {
        perfilService.actualizarPerfil(id, dto);
        return ResponseEntity.ok("Perfil actualizado correctamente");
    }











}
