package com.upc.unistress.servicios;
import com.upc.unistress.dtos.RegistroEmocionalDTO;
import com.upc.unistress.entidades.RegistroEmocional;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.repositorios.RegistroEmocionalRepository;
import com.upc.unistress.repositorios.UsuarioRepository;
import com.upc.unistress.interfaces.IRegistroEmocionalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroEmocionalService implements IRegistroEmocionalService {

    @Autowired
    private RegistroEmocionalRepository registroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertar(RegistroEmocionalDTO dto) {
        RegistroEmocional registro = new RegistroEmocional();

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        registro.setUsuario(usuario);

        registro.setEmocion(dto.getEmocion());
        registro.setNivel(dto.getNivel());
        registro.setDescripcion(dto.getDescripcion());
        registro.setFechaRegistro(LocalDateTime.now()); // se guarda automático

        registroRepository.save(registro);
    }





    @Override
    public List<RegistroEmocionalDTO> listar() {
        return registroRepository.findAll()
                .stream()
                .map(r -> modelMapper.map(r, RegistroEmocionalDTO.class))
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        registroRepository.deleteById(id);
    }

    @Override
    public RegistroEmocionalDTO listId(Long id) {
        return registroRepository.findById(id)
                .map(r -> modelMapper.map(r, RegistroEmocionalDTO.class))
                .orElseThrow(() -> new RuntimeException("Registro emocional no encontrado"));
    }

    @Override
    public List<RegistroEmocionalDTO> listarPorUsuario(Long usuarioId) {
        return registroRepository.findByUsuario_Id(usuarioId)
                .stream()
                .map(r -> modelMapper.map(r, RegistroEmocionalDTO.class))
                .toList();
    }

    @Override
    public List<RegistroEmocionalDTO> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return registroRepository.findByFechaRegistroBetween(inicio, fin)
                .stream()
                .map(r -> modelMapper.map(r, RegistroEmocionalDTO.class))
                .toList();
    }









}

