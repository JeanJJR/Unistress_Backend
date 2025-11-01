package com.upc.unistress.interfaces;

import com.upc.unistress.dtos.RecomendacionDTO;

import java.util.List;

public interface IRecomendacionService {
    void insertar(RecomendacionDTO dto);
    List<RecomendacionDTO> listar();
    void eliminar(Long id);
    RecomendacionDTO listId(Long id);
    List<RecomendacionDTO> listarPorTipo(String tipo);
    List<RecomendacionDTO> listarPorUsuario(Long usuarioId);



}