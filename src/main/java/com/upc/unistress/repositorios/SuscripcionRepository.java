package com.upc.unistress.repositorios;

import com.upc.unistress.entidades.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findByUsuario_Id(Long usuarioId);

    List<Suscripcion> findByEstado(String estado);
    Optional<Suscripcion> findByUsuario_IdAndEstado(Long usuarioId, String estado);

}
