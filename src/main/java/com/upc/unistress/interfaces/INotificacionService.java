package com.upc.unistress.interfaces;

import com.upc.unistress.dtos.NotificacionDTO;

import java.util.List;

public interface INotificacionService {

    // Crear nueva notificación
    void insertar(NotificacionDTO dto);

    // Listar todas las notificaciones
    List<NotificacionDTO> listar();

    // Listar notificaciones de un usuario específico
    List<NotificacionDTO> listarPorUsuario(Long usuarioId);

    // Marcar notificación como leída
    void marcarComoLeida(Long id);

    // Eliminar notificación
    void eliminar(Long id);

    // Buscar por ID
    NotificacionDTO listId(Long id);

}
