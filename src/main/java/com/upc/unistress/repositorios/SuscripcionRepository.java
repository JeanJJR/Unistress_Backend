package com.upc.unistress.repositorios;

import com.upc.unistress.entidades.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findByUsuario_Id(Long usuarioId);

    List<Suscripcion> findByEstado(String estado);
    Optional<Suscripcion> findByUsuario_IdAndEstado(Long usuarioId, String estado);
    @Modifying
    @Query("DELETE FROM Suscripcion su WHERE su.usuario.id = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") Long usuarioId);



}
