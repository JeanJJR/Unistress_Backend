package com.upc.unistress.repositorios;

import com.upc.unistress.entidades.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByPsicologo_Id(Long psicologoId);
    List<Sesion> findByEstudiante_Id(Long estudianteId);
    List<Sesion> findByFecha(LocalDate fecha);
    boolean existsByPsicologoIdAndFechaAndHora(Long psicologoId, LocalDate fecha, LocalTime hora);
    @Modifying
    @Query("DELETE FROM Sesion s WHERE s.estudiante.id = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") Long usuarioId);


}
