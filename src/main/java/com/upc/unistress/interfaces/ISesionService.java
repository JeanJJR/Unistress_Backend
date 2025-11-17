package com.upc.unistress.interfaces;

import com.upc.unistress.dtos.SesionDTO;

import java.time.LocalDate;
import java.util.List;

public interface ISesionService {

    SesionDTO crearSesion(SesionDTO dto);

    List<SesionDTO> listar();

    List<SesionDTO> listarPorEstudianteYRango(Long estudianteId, LocalDate fechaInicio, LocalDate fechaFin);

    List<SesionDTO> listarHistorialPorEstudiante(Long estudianteId);

    SesionDTO editarSesion(Long id, SesionDTO dto);

    void aceptarSesion(Long id);

    void eliminar(Long id);

    List<SesionDTO> listarPorFecha(LocalDate fecha);

    List<SesionDTO> filtrarSesionesPorRangoAutenticado(LocalDate inicio, LocalDate fin);

    public void cancelarSesion(Long sesionId,Long estudianteId);
}
