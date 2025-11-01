package com.upc.unistress.interfaces;

import com.upc.unistress.dtos.BancoPreguntaDTO;
import java.util.List;

public interface IBancoPreguntaService {
    void insertar(BancoPreguntaDTO dto);
    List<BancoPreguntaDTO> listar();
    void eliminar(Long id);
    BancoPreguntaDTO listId(Long id);
    List<BancoPreguntaDTO> listarPorPsicologo(Long psicologoId);
    void actualizar(Long id, BancoPreguntaDTO dto);


}