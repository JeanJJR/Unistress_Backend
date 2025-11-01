package com.upc.unistress.servicios;

import com.upc.unistress.dtos.NotificacionDTO;
import com.upc.unistress.entidades.Notificacion;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.interfaces.INotificacionService;
import com.upc.unistress.repositorios.NotificacionRepository;
import com.upc.unistress.repositorios.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificacionService implements INotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertar(NotificacionDTO dto) {
        Notificacion n = new Notificacion();

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + dto.getUsuarioId() + " no encontrado"));

        n.setUsuario(usuario);
        n.setMensaje(dto.getMensaje());
        n.setTipo(dto.getTipo());
        n.setFechaEnvio(dto.getFechaEnvio() != null ? dto.getFechaEnvio() : LocalDate.now());
        n.setEstado(dto.getEstado() != null ? dto.getEstado() : "PENDIENTE");

        notificacionRepository.save(n);
    }

    @Override
    public List<NotificacionDTO> listar() {
        return notificacionRepository.findAll()
                .stream()
                .map(n -> {
                    NotificacionDTO dto = modelMapper.map(n, NotificacionDTO.class);
                    dto.setUsuarioId(n.getUsuario().getId());
                    return dto;
                })
                .toList();
    }

    @Override
    public List<NotificacionDTO> listarPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuario_Id(usuarioId)
                .stream()
                .map(n -> {
                    NotificacionDTO dto = modelMapper.map(n, NotificacionDTO.class);
                    dto.setUsuarioId(n.getUsuario().getId());
                    return dto;
                })
                .toList();
    }

    @Override
    public void marcarComoLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notificacion.setEstado("LEIDA");
        notificacionRepository.save(notificacion);
    }

    @Override
    public void eliminar(Long id) {
        if (notificacionRepository.existsById(id)) {
            notificacionRepository.deleteById(id);
        }
    }

    @Override
    public NotificacionDTO listId(Long id) {
        return notificacionRepository.findById(id)
                .map(n -> {
                    NotificacionDTO dto = modelMapper.map(n, NotificacionDTO.class);
                    dto.setUsuarioId(n.getUsuario().getId());
                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("Notificación con ID " + id + " no encontrada"));
    }





}
