package com.upc.unistress.servicios;

import com.upc.unistress.dtos.PagoDTO;
import com.upc.unistress.entidades.MetodoPago;
import com.upc.unistress.entidades.Pago;
import com.upc.unistress.entidades.Suscripcion;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.interfaces.IPagoService;
import com.upc.unistress.repositorios.MetodoPagoRepository;
import com.upc.unistress.repositorios.PagoRepository;
import com.upc.unistress.repositorios.SuscripcionRepository;
import com.upc.unistress.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService implements IPagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertar(PagoDTO dto) {
        Pago pago = modelMapper.map(dto, Pago.class);

        // Validar si existe la sucripcion
        Suscripcion suscripcion = suscripcionRepository.findById(dto.getSuscripcionId())
                .orElseThrow(() -> new RuntimeException("Suscripción con ID " + dto.getSuscripcionId() + " no encontrada"));

        // Validar si existe metodo de pago
        MetodoPago metodoPago = metodoPagoRepository.findById(dto.getMetodoPagoId())
                .orElseThrow(() -> new RuntimeException("Método de pago con ID " + dto.getMetodoPagoId() + " no encontrado"));

        pago.setSuscripcion(suscripcion);
        pago.setMetodoPago(metodoPago);

        pagoRepository.save(pago);
    }

    @Override
    public List<PagoDTO> listar() {
        return pagoRepository.findAll()
                .stream()
                .map(p -> {
                    PagoDTO dto = modelMapper.map(p, PagoDTO.class);
                    dto.setSuscripcionId(p.getSuscripcion().getId());
                    dto.setMetodoPagoId(p.getMetodoPago().getId());
                    return dto;
                })
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        if (pagoRepository.existsById(id)) {
            pagoRepository.deleteById(id);
        }
    }

    @Override
    public PagoDTO listId(Long id) {
        return pagoRepository.findById(id)
                .map(p -> {
                    PagoDTO dto = modelMapper.map(p, PagoDTO.class);
                    dto.setSuscripcionId(p.getSuscripcion().getId());
                    dto.setMetodoPagoId(p.getMetodoPago().getId());
                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("Pago con ID " + id + " no encontrado"));
    }

    @Override
    public List<PagoDTO> listarPorSuscripcion(Long suscripcionId) {
        return pagoRepository.findBySuscripcion_Id(suscripcionId)
                .stream()
                .map(p -> {
                    PagoDTO dto = modelMapper.map(p, PagoDTO.class);
                    dto.setSuscripcionId(p.getSuscripcion().getId());
                    dto.setMetodoPagoId(p.getMetodoPago().getId());
                    return dto;
                })
                .toList();
    }

}
