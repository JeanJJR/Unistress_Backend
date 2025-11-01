package com.upc.unistress.repositorios;

import com.upc.unistress.entidades.BancoPregunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BancoPreguntaRepository extends JpaRepository<BancoPregunta, Long> {

    List<BancoPregunta> findByPsicologo_Id(Long psicologoId);
}
