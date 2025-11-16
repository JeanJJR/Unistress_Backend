package com.upc.unistress.repositorios;

import com.upc.unistress.entidades.BancoPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BancoPreguntaRepository extends JpaRepository<BancoPregunta, Long> {

    List<BancoPregunta> findByPsicologo_Id(Long psicologoId);
    @Modifying
    @Query("DELETE FROM BancoPregunta bp WHERE bp.psicologo.id = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") Long usuarioId);

}
