package com.upc.unistress.servicios;
import com.upc.unistress.interfaces.ISuscripcionService;
import com.upc.unistress.dtos.SuscripcionDTO;
import com.upc.unistress.entidades.Suscripcion;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.repositorios.SuscripcionRepository;
import com.upc.unistress.repositorios.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuscripcionService implements ISuscripcionService {

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertar(SuscripcionDTO dto) {
        Suscripcion suscripcion = modelMapper.map(dto, Suscripcion.class);

        // Verificamos que el usuario exista antes de asignarlo
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + dto.getUsuarioId() + " no encontrado"));

        suscripcion.setUsuario(usuario);
        suscripcionRepository.save(suscripcion);
    }

    @Override
    public List<SuscripcionDTO> listar() {
        return suscripcionRepository.findAll()
                .stream()
                .map(s -> {
                    SuscripcionDTO dto = modelMapper.map(s, SuscripcionDTO.class);
                    dto.setUsuarioId(s.getUsuario().getId());
                    return dto;
                })
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        if (suscripcionRepository.existsById(id)) {
            suscripcionRepository.deleteById(id);
        }
    }

    @Override
    public SuscripcionDTO listId(Long id) {
        return suscripcionRepository.findById(id)
                .map(s -> {
                    SuscripcionDTO dto = modelMapper.map(s, SuscripcionDTO.class);
                    dto.setUsuarioId(s.getUsuario().getId());
                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("Suscripción con ID " + id + " no encontrada"));
    }

    @Override
    public List<SuscripcionDTO> listarPorUsuario(Long usuarioId) {
        return suscripcionRepository.findByUsuario_Id(usuarioId)
                .stream()
                .map(s -> {
                    SuscripcionDTO dto = modelMapper.map(s, SuscripcionDTO.class);
                    dto.setUsuarioId(s.getUsuario().getId());
                    return dto;
                })
                .toList();
    }
    @Override
    public List<SuscripcionDTO> listarActivas() {
        return suscripcionRepository.findByEstado("ACTIVO")
                .stream()
                .map(s -> modelMapper.map(s, SuscripcionDTO.class))
                .toList();
    }

}
