package com.upc.unistress.servicios;

import com.upc.unistress.dtos.SesionDTO;
import com.upc.unistress.entidades.Notificacion;
import com.upc.unistress.entidades.Sesion;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.interfaces.ISesionService;
import com.upc.unistress.repositorios.NotificacionRepository;
import com.upc.unistress.repositorios.SesionRepository;
import com.upc.unistress.repositorios.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SesionService implements ISesionService {

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void crearSesion(SesionDTO dto) {
        // Validar que el psicólogo existe
        Usuario psicologo = usuarioRepository.findById(dto.getPsicologoId())
                .orElseThrow(() -> new RuntimeException("Psicólogo no encontrado"));

        // Validar que el estudiante existe
        Usuario estudiante = usuarioRepository.findById(dto.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Validar roles
        if (!"ROLE_PSICOLOGO".equalsIgnoreCase(psicologo.getRol().getTipoRol())) {
            throw new RuntimeException("El usuario con ID " + dto.getPsicologoId() + " no es un psicólogo válido.");
        }

        if (!"ROLE_ESTUDIANTE".equalsIgnoreCase(estudiante.getRol().getTipoRol())) {
            throw new RuntimeException("El usuario con ID " + dto.getEstudianteId() + " no es un estudiante válido.");
        }

        // Validar que el horario no esté ocupado
        boolean ocupado = sesionRepository.existsByPsicologoIdAndFechaAndHora(
                dto.getPsicologoId(), dto.getFecha(), dto.getHora());
        if (ocupado) {
            throw new RuntimeException("El horario seleccionado ya está ocupado. Por favor, elige otro.");
        }

        // Crear sesión
        Sesion sesion = new Sesion();
        sesion.setPsicologo(psicologo);
        sesion.setEstudiante(estudiante);
        sesion.setFecha(dto.getFecha());
        sesion.setHora(dto.getHora());
        sesion.setMensaje("Sesión creada");
        sesion.setEstado("PENDIENTE");

        sesionRepository.save(sesion);

        // Crear notificación para el psicólogo
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(psicologo);
        notificacion.setMensaje("Nueva sesión pendiente con el estudiante " + estudiante.getNombre());
        notificacion.setTipo("CITA");
        notificacion.setFechaEnvio(LocalDate.now());
        notificacion.setEstado("PENDIENTE");
        notificacionRepository.save(notificacion);
    }




    @Override
    public List<SesionDTO> listar() {
        return sesionRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    public List<SesionDTO> listarPorEstudianteYRango(Long estudianteId, LocalDate fechaInicio, LocalDate fechaFin) {
        return sesionRepository.findByEstudiante_Id(estudianteId)
                .stream()
                .filter(s -> !s.getFecha().isBefore(fechaInicio) && !s.getFecha().isAfter(fechaFin))
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    public List<SesionDTO> listarHistorialPorEstudiante(Long estudianteId) {
        return sesionRepository.findByEstudiante_Id(estudianteId)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }



    @Override
    public void editarSesion(Long id, SesionDTO dto) {
        Sesion sesion = sesionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesion no encontrada"));

        sesion.setFecha(dto.getFecha());
        sesion.setHora(dto.getHora());

        sesionRepository.save(sesion);
    }

    @Override
    public void eliminar(Long id) {
        if (sesionRepository.existsById(id)) {
            sesionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Sesión no encontrada");
        }
    }

    @Override
    public List<SesionDTO> listarPorFecha(LocalDate fecha) {
        return sesionRepository.findByFecha(fecha)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    private SesionDTO convertirADTO(Sesion s) {
        SesionDTO dto = modelMapper.map(s, SesionDTO.class);
        dto.setPsicologoId(s.getPsicologo().getId());
        dto.setEstudianteId(s.getEstudiante().getId());
        //String nombrePsicologo=s.getPsicologo().getNombre() + " "+s.getPsicologo().getApellidos();
        //String nombreEstudiante=s.getEstudiante().getNombre() + " "+s.getEstudiante().getApellidos();
        //dto.setPsicologoNombreCompleto(nombrePsicologo);
        //dto.setEstudianteNombreCompleto(nombreEstudiante);
        return dto;
    }

    @Override
    public List<SesionDTO> filtrarSesionesPorRangoAutenticado(LocalDate inicio, LocalDate fin) {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario estudiante = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return sesionRepository.findByEstudiante_Id(estudiante.getId())
                .stream()
                .filter(s -> !s.getFecha().isBefore(inicio) && !s.getFecha().isAfter(fin))
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    public void cancelarSesion(Long sesionId, Long estudianteId) {
        Usuario estudiante = usuarioRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Sesion sesion = sesionRepository.findById(sesionId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        if (!sesion.getEstudiante().getId().equals(estudiante.getId())) {
            throw new RuntimeException("No tienes permiso para cancelar esta sesión");
        }

        sesion.setEstado("CANCELADA");
        sesionRepository.save(sesion);

        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(sesion.getPsicologo());
        notificacion.setMensaje("El estudiante " + estudiante.getNombre() + " canceló su sesión programada.");
        notificacion.setTipo("CANCELACIÓN");
        notificacion.setFechaEnvio(LocalDate.now());
        notificacion.setEstado("ENVIADA");
        notificacionRepository.save(notificacion);
    }

}
