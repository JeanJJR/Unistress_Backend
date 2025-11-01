package com.upc.unistress.interfaces;

import com.upc.unistress.dtos.RegistroEmocionalDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface IRegistroEmocionalService {
    void insertar(RegistroEmocionalDTO dto);
    List<RegistroEmocionalDTO> listar();
    void eliminar(Long id);
    RegistroEmocionalDTO listId(Long id);
    List<RegistroEmocionalDTO> listarPorUsuario(Long usuarioId);
    List<RegistroEmocionalDTO> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);



}
