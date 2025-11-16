package com.upc.unistress.repositorios;

import com.upc.unistress.entidades.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    // Buscar perfil por ID de usuario
    Optional<Perfil> findByUsuario_Id(int usuarioId);

    // Listar perfiles por tipo (ESTUDIANTE o PSICOLOGO)
    List<Perfil> findByTipoPerfil(String tipoPerfil);

    @Modifying
    @Query("DELETE FROM Perfil p WHERE p.usuario.id = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") Long usuarioId);

}