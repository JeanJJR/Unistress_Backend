package com.upc.unistress.servicios;

import com.upc.unistress.dtos.RecomendacionDTO;
import com.upc.unistress.entidades.Notificacion;
import com.upc.unistress.entidades.Recomendacion;
import com.upc.unistress.entidades.RegistroEmocional;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.repositorios.NotificacionRepository;
import com.upc.unistress.repositorios.RecomendacionRepository;
import com.upc.unistress.repositorios.RegistroEmocionalRepository;
import com.upc.unistress.interfaces.IRecomendacionService;
import com.upc.unistress.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecomendacionService implements IRecomendacionService {

    @Autowired
    private RecomendacionRepository recomendacionRepository;

    @Autowired
    private RegistroEmocionalRepository registroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NotificacionRepository notificacionRepository;

    @Transactional
    @Override
    public void insertar(RecomendacionDTO dto) {
        Recomendacion recomendacion = modelMapper.map(dto, Recomendacion.class);

        // 1. Validar y obtener el registro emocional (Vincula con el Estudiante)
        if (dto.getRegistroEmocionalId() == null) {
            throw new RuntimeException("Debe especificar el ID del registro emocional");
        }

        RegistroEmocional registro = registroRepository.findById(dto.getRegistroEmocionalId())
                .orElseThrow(() -> new RuntimeException("Registro emocional no encontrado"));

        recomendacion.setRegistroEmocional(registro);

        // 2. Obtener al Psicólogo usando el DTO
        // Valida que el ID venga en el DTO
        if (dto.getUsuarioId() == null) {
            throw new RuntimeException("Debe especificar el ID del psicólogo (usuarioId)");
        }

        // Busca al psicólogo
        Usuario psicologo = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Psicólogo no encontrado con ID: " + dto.getUsuarioId()));

        // Asigna el psicólogo a la recomendación (Esto llena la columna usuario_id en BD)
        recomendacion.setUsuario(psicologo);

        // Guarda en la base de datos
        recomendacionRepository.save(recomendacion);

        // 3. Notificaciones
        Usuario estudiante = registro.getUsuario();

        // 3️ Obtener al psicólogo autenticado (el que emite la recomendación)
        //String correoPsicologo = SecurityContextHolder.getContext().getAuthentication().getName();
        //Usuario psicologo = usuarioRepository.findByCorreo(correoPsicologo)
          //      .orElseThrow(() -> new RuntimeException("Psicólogo no autenticado o no encontrado"));


        // Notificación para el estudiante
        Notificacion notificacionEstudiante = new Notificacion();
        notificacionEstudiante.setUsuario(estudiante);
        notificacionEstudiante.setMensaje("El psicólogo " + psicologo.getNombre() +
                " ha emitido una nueva recomendación basada en tu registro emocional del " +
                registro.getFechaRegistro().toLocalDate() + ".");
        notificacionEstudiante.setTipo("RECOMENDACION");
        notificacionEstudiante.setFechaEnvio(LocalDate.now());
        notificacionEstudiante.setEstado("NUEVA");
        notificacionRepository.save(notificacionEstudiante);

        // Notificación para el psicólogo
        Notificacion notificacionPsicologo = new Notificacion();
        notificacionPsicologo.setUsuario(psicologo);
        notificacionPsicologo.setMensaje("Has emitido una recomendación para el estudiante " +
                estudiante.getNombre() + ".");
        notificacionPsicologo.setTipo("RECOMENDACION");
        notificacionPsicologo.setFechaEnvio(LocalDate.now());
        notificacionPsicologo.setEstado("ENVIADA");
        notificacionRepository.save(notificacionPsicologo);
    }




    @Override
    public List<RecomendacionDTO> listar() {
        return recomendacionRepository.findAll()
                .stream()
                .map(r -> modelMapper.map(r, RecomendacionDTO.class))
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        recomendacionRepository.deleteById(id);
    }

    @Override
    public RecomendacionDTO listId(Long id) {
        return recomendacionRepository.findById(id)
                .map(r -> modelMapper.map(r, RecomendacionDTO.class))
                .orElseThrow(() -> new RuntimeException("Recomendación no encontrada"));
    }

    @Override
    public List<RecomendacionDTO> listarPorTipo(String tipo) {
        return recomendacionRepository.findByTipo(tipo)
                .stream()
                .map(r -> modelMapper.map(r, RecomendacionDTO.class))
                .toList();
    }
    @Override
    public List<RecomendacionDTO> listarPorUsuario(Long usuarioId) {
        return recomendacionRepository.findByRegistroEmocional_Usuario_Id(usuarioId)
                .stream()
                .map(r -> {
                    RecomendacionDTO dto = new RecomendacionDTO();
                    dto.setId(r.getId());
                    dto.setMensaje(r.getMensaje());
                    dto.setTipo(r.getTipo());
                    dto.setRegistroEmocionalId(r.getRegistroEmocional().getId());
                    dto.setUsuarioId(r.getRegistroEmocional().getUsuario().getId());
                    return dto;
                })
                .toList();
    }

    // Este método buscará por PSICÓLOGO (el autor)
    @Override
    public List<RecomendacionDTO> listarPorPsicologo(Long psicologoId) {
        return recomendacionRepository.findByUsuario_Id(psicologoId)
                .stream()
                .map(r -> {
                    // Usamos el mismo mapeo que 'listarPorUsuario'
                    RecomendacionDTO dto = new RecomendacionDTO();
                    dto.setId(r.getId());
                    dto.setMensaje(r.getMensaje());
                    dto.setTipo(r.getTipo());
                    dto.setRegistroEmocionalId(r.getRegistroEmocional().getId());
                    dto.setUsuarioId(r.getRegistroEmocional().getUsuario().getId());
                    // TODO: Aquí querrás el nombre del estudiante, no su ID
                    // dto.setNombreEstudiante(r.getRegistroEmocional().getUsuario().getNombre());
                    return dto;
                })
                .toList();
    }



}
