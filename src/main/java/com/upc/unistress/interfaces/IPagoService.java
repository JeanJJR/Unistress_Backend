package com.upc.unistress.interfaces;

import com.upc.unistress.dtos.PagoDTO;

import java.util.List;

public interface IPagoService {
    void insertar(PagoDTO dto);
    List<PagoDTO> listar();
    void eliminar(Long id);
    PagoDTO listId(Long id);
    List<PagoDTO> listarPorSuscripcion(Long suscripcionId);
    // Registrar un pago por estudiante autenticado

}
