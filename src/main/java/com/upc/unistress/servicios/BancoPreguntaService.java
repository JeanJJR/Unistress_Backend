package com.upc.unistress.servicios;

import com.upc.unistress.dtos.BancoPreguntaDTO;
import com.upc.unistress.entidades.BancoPregunta;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.interfaces.IBancoPreguntaService;
import com.upc.unistress.repositorios.BancoPreguntaRepository;
import com.upc.unistress.repositorios.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BancoPreguntaService implements IBancoPreguntaService {

    @Autowired
    private BancoPreguntaRepository repository;



    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertar(BancoPreguntaDTO dto) {
        // Obtener el email (username) del usuario autenticado
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar al usuario autenticado
        Usuario psicologo = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar que tenga rol de psicólogo
        if (!"ROLE_PSICOLOGO".equalsIgnoreCase(psicologo.getRol().getTipoRol())) {
            throw new RuntimeException("Solo los psicólogos pueden crear preguntas.");
        }

        // Crear y guardar la pregunta
        BancoPregunta pregunta = new BancoPregunta();
        pregunta.setEnunciado(dto.getEnunciado());
        pregunta.setPsicologo(psicologo); //Asigna el usuario autenticado

        repository.save(pregunta);
    }

    @Override
    public void actualizar(Long id, BancoPreguntaDTO dto) {
        BancoPregunta pregunta = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));

        pregunta.setEnunciado(dto.getEnunciado());

        repository.save(pregunta);
    }





    @Override
    public List<BancoPreguntaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, BancoPreguntaDTO.class))
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public BancoPreguntaDTO listId(Long id) {
        return repository.findById(id)
                .map(p -> modelMapper.map(p, BancoPreguntaDTO.class))
                .orElseThrow(() -> new RuntimeException("Pregunta con ID " + id + " no encontrada"));
    }





    @Override
    public List<BancoPreguntaDTO> listarPorPsicologo(Long psicologoId) {
        return repository.findByPsicologo_Id(psicologoId)
                .stream()
                .map(p -> modelMapper.map(p, BancoPreguntaDTO.class))
                .toList();
    }

}
