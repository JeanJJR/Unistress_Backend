package com.upc.unistress.repositorios;

import com.upc.unistress.entidades.TestEmocional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TestEmocionalRepository extends JpaRepository<TestEmocional, Long> {
    List<TestEmocional> findByUsuario_Id(Long usuarioId);
    List<TestEmocional> findByFechaBetween(LocalDate inicio, LocalDate fin);
    List<TestEmocional> findByUsuario_IdOrderByFechaDesc(Long usuarioId);
}
