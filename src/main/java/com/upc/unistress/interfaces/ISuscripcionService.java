package com.upc.unistress.interfaces;

import com.upc.unistress.dtos.SuscripcionDTO;

import java.util.List;

public interface ISuscripcionService {
    void insertar(SuscripcionDTO dto);
    List<SuscripcionDTO> listar();
    void eliminar(Long id);
    SuscripcionDTO listId(Long id);
    List<SuscripcionDTO> listarPorUsuario(Long usuarioId);
    List<SuscripcionDTO> listarActivas();
    SuscripcionDTO actualizar(Long id, SuscripcionDTO dto);

}
