package com.upc.unistress.interfaces;

import com.upc.unistress.dtos.EstudianteDTO;
import com.upc.unistress.dtos.PsicologoDTO;
import com.upc.unistress.dtos.UsuarioDTO;
import com.upc.unistress.entidades.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    // Registrar nuevo usuario
    void insert(UsuarioDTO usuarioDTO);

    // Listar todos los usuarios
    List<UsuarioDTO> list();

    // Eliminar usuario
    void delete(Long id);

    // Buscar usuario por id
    UsuarioDTO listId(Long id);

    // Buscar usuario por correo (para login)
    Optional<UsuarioDTO> findByCorreo(String correo);

    // Validar si el correo ya existe
    boolean existsByCorreo(String correo);

    // Listar usuarios por rol (ADMIN, ESTUDIANTE, PSICOLOGO)
    List<UsuarioDTO> listarPorRol(String tipoRol);

    // registrar estudiante
    void registrarEstudiante(EstudianteDTO dto);

    // registrar psicologo
    void registrarPsicologo(PsicologoDTO psicologodto);

    List<UsuarioDTO> listarPsicologosDisponibles();
}
