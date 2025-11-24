package com.upc.unistress.servicios;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.upc.unistress.dtos.PerfilDTO;
import com.upc.unistress.dtos.PerfilDetalleDTO;
import com.upc.unistress.entidades.Perfil;
import com.upc.unistress.entidades.Suscripcion;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.interfaces.IPerfilService;
import com.upc.unistress.repositorios.PerfilRepository;
import com.upc.unistress.repositorios.SuscripcionRepository;
import com.upc.unistress.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PerfilService implements IPerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary  cloudinary;



    @Override
    public void insert(PerfilDTO perfilDTO) {
        Perfil perfil = modelMapper.map(perfilDTO, Perfil.class);
        perfilRepository.save(perfil);
    }

    @Override
    public List<PerfilDTO> list() {
        return perfilRepository.findAll()
                .stream()
                .map(perfil -> modelMapper.map(perfil, PerfilDTO.class))
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (perfilRepository.existsById(id)) {
            perfilRepository.deleteById(id);
        }
    }

    @Override
    public PerfilDTO listId(Long id) {
        return perfilRepository.findById(id)
                .map(perfil -> modelMapper.map(perfil, PerfilDTO.class))
                .orElseThrow(() -> new RuntimeException("Perfil con ID " + id + " no encontrado"));
    }

    @Override
    public Optional<PerfilDTO> findByUsuarioId(int usuarioId) {
        return perfilRepository.findByUsuario_Id(usuarioId)
                .map(perfil -> modelMapper.map(perfil, PerfilDTO.class));
    }

    @Override
    public List<PerfilDTO> listarPorTipoPerfil(String tipoPerfil) {
        return perfilRepository.findByTipoPerfil(tipoPerfil)
                .stream()
                .map(perfil -> modelMapper.map(perfil, PerfilDTO.class))
                .toList();
    }
    /*
    @Override
    public void actualizarFoto(Long perfilId, String fotoUrl){
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        perfil.setFotoUrl(fotoUrl);
        perfilRepository.save(perfil);
    }
    */

    @Override
    @Transactional
    public void actualizarFoto(int usuarioId, MultipartFile archivo) {
        Perfil perfil = perfilRepository.findByUsuario_Id(usuarioId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado para el Usuario ID " + usuarioId));

        try {
            Map uploadResult = cloudinary.uploader().upload(archivo.getBytes(), ObjectUtils.emptyMap());
            String url = (String) uploadResult.get("secure_url");

            System.out.println(" URL generada: " + url);
            perfil.setFotoUrl(url);
            perfilRepository.save(perfil);
            System.out.println(" Perfil actualizado con nueva foto");

        } catch (IOException e) {
            throw new RuntimeException("Error al subir la foto a Cloudinary", e);
        }
    }



    /*
    // actualziar foto real
    @Override
    public void actualizarFoto(Long perfilId, MultipartFile archivo) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado con ID " + perfilId));

        try {
            // Carpeta local donde guardar fotos
            String uploadDir = "uploads/";
            String fileName = perfilId + "_" + archivo.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // Crear directorio si no existe
            Files.createDirectories(filePath.getParent());

            // Guardar archivo
            Files.write(filePath, archivo.getBytes());

            // Guardar la URL relativa en BD
            perfil.setFotoUrl("/uploads/" + fileName);
            perfilRepository.save(perfil);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la foto", e);
        }
    }*/

    @Override
    public PerfilDetalleDTO obtenerPerfilPorUsuario(int usuarioId) {
        // Buscar el usuario
        Usuario usuario = usuarioRepository.findById((long) usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID " + usuarioId));

        // Buscar el perfil
        Perfil perfil = perfilRepository.findByUsuario_Id(usuarioId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado para el usuario " + usuarioId));

        // Mapear al DTO
        PerfilDetalleDTO dto = new PerfilDetalleDTO();
        dto.setUsuarioId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setCorreo(usuario.getCorreo());
        dto.setContrasena(usuario.getContrasena());
        dto.setTelefono(usuario.getTelefono());

        dto.setTipoPerfil(perfil.getTipoPerfil());
        dto.setUniversidad(perfil.getUniversidad());
        dto.setCarrera(perfil.getCarrera());
        dto.setCiclo(perfil.getCiclo());
        dto.setEstadoAcademico(perfil.getEstadoAcademico());
        dto.setEspecialidad(perfil.getEspecialidad());
        dto.setColegiatura(perfil.getColegiatura());
        dto.setAnosExperiencia(perfil.getAnosExperiencia());
        dto.setFotoUrl(perfil.getFotoUrl());




        return dto;
    }

    @Override
    public void actualizarPerfil(int usuarioId, PerfilDetalleDTO dto) {
        // Buscar usuario existente
        Usuario usuario = usuarioRepository.findById((long) usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String nuevoCorreo = dto.getCorreo().trim();
        String correoActual = usuario.getCorreo().trim();

        // Validar si el correo cambió
        if (!nuevoCorreo.equalsIgnoreCase(correoActual)) {
            boolean correoEnUso = usuarioRepository.findByCorreo(nuevoCorreo)
                    .filter(u -> !u.getId().equals(usuario.getId()))
                    .isPresent();

            if (correoEnUso) {
                throw new RuntimeException("El correo ya está en uso por otro usuario");
            }

            usuario.setCorreo(nuevoCorreo);
        }

        // Codificar contraseña si se envía
        if (dto.getContrasena() != null && !dto.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        }

        // Actualizar datos básicos
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setTelefono(dto.getTelefono());
        usuarioRepository.save(usuario);

        // Obtener rol del usuario para asignar tipoPerfil si se crea
        String rol = usuario.getRol().getTipoRol().trim().toUpperCase();

        // Buscar o crear perfil
        Perfil perfil = perfilRepository.findByUsuario_Id(usuarioId)
                .orElseGet(() -> {
                    Perfil nuevoPerfil = new Perfil();
                    nuevoPerfil.setUsuario(usuario);

                    // Asignar tipoPerfil automáticamente según rol
                    if ("ROLE_ESTUDIANTE".equals(rol)) {
                        nuevoPerfil.setTipoPerfil("ROLE_ESTUDIANTE");
                    } else if ("ROLE_PSICOLOGO".equals(rol)) {
                        nuevoPerfil.setTipoPerfil("ROLE_PSICOLOGO");
                    } else {
                        throw new RuntimeException("Rol no válido para crear perfil");
                    }

                    return nuevoPerfil;
                });

        // Actualizar datos del perfil
        perfil.setUniversidad(dto.getUniversidad());
        perfil.setCarrera(dto.getCarrera());
        perfil.setCiclo(dto.getCiclo());
        perfil.setEstadoAcademico(dto.getEstadoAcademico());
        perfil.setEspecialidad(dto.getEspecialidad());
        perfil.setColegiatura(dto.getColegiatura());
        perfil.setAnosExperiencia(dto.getAnosExperiencia());
        perfil.setDescripcion(dto.getDescripcion());

        perfilRepository.save(perfil);
    }
















}
