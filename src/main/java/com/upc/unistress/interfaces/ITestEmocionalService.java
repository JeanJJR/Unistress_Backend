package com.upc.unistress.interfaces;

import com.upc.unistress.dtos.TestEmocionalDTO;
import java.time.LocalDate;
import java.util.List;

public interface ITestEmocionalService {
    void insertar(TestEmocionalDTO dto);
    List<TestEmocionalDTO> listar();
    void eliminar(Long id);
    TestEmocionalDTO listId(Long id);
    List<TestEmocionalDTO> listarPorUsuario(Long usuarioId);
    List<TestEmocionalDTO> listarPorRangoFechas(LocalDate inicio, LocalDate fin);

    TestEmocionalDTO resolver(TestEmocionalDTO dto);
}
