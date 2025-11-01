package com.upc.unistress.interfaces;

import com.upc.unistress.dtos.SesionDTO;

import java.time.LocalDate;
import java.util.List;

public interface ISesionService {

    void crearSesion(SesionDTO dto);

    List<SesionDTO> listar();

    List<SesionDTO> listarPorEstudianteYRango(Long estudianteId, LocalDate fechaInicio, LocalDate fechaFin);

    List<SesionDTO> listarHistorialPorEstudiante(Long estudianteId);

    void editarSesion(Long id, SesionDTO dto);

    void eliminar(Long id);

    List<SesionDTO> listarPorFecha(LocalDate fecha);

    List<SesionDTO> filtrarSesionesPorRangoAutenticado(LocalDate inicio, LocalDate fin);

    void cancelarSesion(Long sesionId,Long estudianteId);
}
