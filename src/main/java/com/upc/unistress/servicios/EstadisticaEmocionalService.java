package com.upc.unistress.servicios;

import com.upc.unistress.dtos.EstadisticaDTO;
import com.upc.unistress.dtos.EstadisticaEmocionDTO;
import com.upc.unistress.dtos.EvolucionEmocionDTO;
import com.upc.unistress.dtos.TendenciaEmocionalDTO;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.interfaces.IEstadisticaEmocionalService;
import com.upc.unistress.repositorios.RegistroEmocionalRepository;
import com.upc.unistress.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class EstadisticaEmocionalService implements IEstadisticaEmocionalService {
    @Autowired
    private RegistroEmocionalRepository registroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<EstadisticaEmocionDTO> promedioNivelPorEmocion(Long usuarioId) {
        return registroRepository.promedioNivelPorEmocion(usuarioId)
                .stream()
                .map(obj -> new EstadisticaEmocionDTO(
                        (String) obj[0],
                        ((Double) obj[1])
                ))
                .toList();
    }

    @Override
    public List<EvolucionEmocionDTO> evolucionEmociones(Long usuarioId) {
        return registroRepository.evolucionEmociones(usuarioId)
                .stream()
                .map(obj -> new EvolucionEmocionDTO(
                        (LocalDate) ((java.time.LocalDateTime) obj[0]).toLocalDate(),
                        (String) obj[1],
                        (Integer) obj[2]
                ))
                .toList();
    }

    @Override
    public List<EvolucionEmocionDTO> evolucionEmocionesEntreFechas(Long usuarioId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!"ROLE_ESTUDIANTE".equalsIgnoreCase(usuario.getRol().getTipoRol())) {
            throw new RuntimeException("Solo los estudiantes pueden consultar su evolución emocional.");
        }

        return registroRepository.evolucionEmocionesEntreFechas(usuarioId, fechaInicio, fechaFin)
                .stream()
                .map(obj -> new EvolucionEmocionDTO(
                        ((LocalDateTime) obj[0]).toLocalDate(),
                        (String) obj[1],
                        (Integer) obj[2]
                ))
                .toList();
    }


    public List<TendenciaEmocionalDTO> obtenerTendencias(LocalDateTime inicio, LocalDateTime fin) {
        return registroRepository.obtenerTendenciasEmocionales(inicio, fin)
                .stream()
                .map(row -> new TendenciaEmocionalDTO(
                        (String) row[0],
                        (Double) row[1],
                        (Long) row[2]
                ))
                .toList();
    }
    @Override
    public EstadisticaDTO obtenerEstadisticas() {
        EstadisticaDTO dto = new EstadisticaDTO();
        dto.setTotalUsuarios(registroRepository.totalUsuarios());
        dto.setTotalPsicologos(registroRepository.totalPsicologos());
        dto.setTotalEstudiantes(registroRepository.totalEstudiantes());
        dto.setTotalSesiones(registroRepository.totalSesiones());
        return dto;
    }


}
