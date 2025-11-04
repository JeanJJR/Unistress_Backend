package com.upc.unistress.servicios;

import com.upc.unistress.dtos.EstudianteDTO;
import com.upc.unistress.dtos.PsicologoDTO;
import com.upc.unistress.dtos.UsuarioDTO;
import com.upc.unistress.entidades.Perfil;
import com.upc.unistress.entidades.Rol;
import com.upc.unistress.entidades.Suscripcion;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.interfaces.IUsuarioService;
import com.upc.unistress.repositorios.PerfilRepository;
import com.upc.unistress.repositorios.RolRepository;
import com.upc.unistress.repositorios.SuscripcionRepository;
import com.upc.unistress.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SuscripcionRepository suscripcionRepository;


    @Autowired
    private RolRepository rolRepository;

    @Override
    public void insert(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuarioRepository.save(usuario);
    }

    @Override
    public List<UsuarioDTO> list() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
    }

    @Override
    public UsuarioDTO listId(Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));
    }

    @Override
    public Optional<UsuarioDTO> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class));
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override
    public List<UsuarioDTO> listarPorRol(String tipoRol) {
        return usuarioRepository.findByRol_TipoRol(tipoRol)
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .toList();
    }

    // registrar estudiante
    @Override
    @Transactional
    public void registrarEstudiante(EstudianteDTO dto) {
        Rol rol = rolRepository.findByTipoRol("ROLE_ESTUDIANTE")
                .orElseThrow(() -> new RuntimeException("Rol ESTUDIANTE no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContraseña(passwordEncoder.encode(dto.getContraseña()));
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(rol);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Perfil perfil = new Perfil();
        perfil.setUsuario(usuarioGuardado);
        perfil.setTipoPerfil("ROLE_ESTUDIANTE");
        perfil.setUniversidad(dto.getUniversidad());
        perfil.setCarrera(dto.getCarrera());
        perfil.setCiclo(dto.getCiclo());
        perfil.setEstadoAcademico(dto.getEstadoAcademico());

        perfilRepository.save(perfil);
        // 3. Crear suscripción FREE por defecto
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setUsuario(usuarioGuardado);
        suscripcion.setTipo("FREE");
        suscripcion.setEstado("ACTIVA");

        LocalDate hoy = LocalDate.now();
        suscripcion.setFechaInicio(hoy);
        suscripcion.setFechaFin(hoy.plusMonths(1)); // por ejemplo, duración 1 mes

        suscripcionRepository.save(suscripcion);
    }

    // registrar psicologo
    @Override
    @Transactional
    public void registrarPsicologo(PsicologoDTO psicologodto) {
        // Buscar el rol PSICOLOGO
        Rol rol = rolRepository.findByTipoRol("ROLE_PSICOLOGO")
                .orElseThrow(() -> new RuntimeException("Rol PSICOLOGO no encontrado"));

        // Crear Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(psicologodto.getNombre());
        usuario.setApellidos(psicologodto.getApellidos());
        usuario.setCorreo(psicologodto.getCorreo());
        usuario.setContraseña(passwordEncoder.encode(psicologodto.getContraseña()));
        usuario.setTelefono(psicologodto.getTelefono());
        usuario.setRol(rol);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Crear Perfil
        Perfil perfil = new Perfil();
        perfil.setUsuario(usuarioGuardado);
        perfil.setTipoPerfil("ROLE_PSICOLOGO");
        perfil.setEspecialidad(psicologodto.getEspecialidad());
        perfil.setColegiatura(psicologodto.getColegiatura());
        perfil.setAnosExperiencia(psicologodto.getAñosExperiencia());

        perfilRepository.save(perfil);
    }

    // listar psicoloogo disponibles
    @Override
    public List<UsuarioDTO> listarPsicologosDisponibles() {
        return usuarioRepository.findPsicologosDisponibles()
                .stream()
                .map(u -> modelMapper.map(u, UsuarioDTO.class))
                .toList();
    }
}
