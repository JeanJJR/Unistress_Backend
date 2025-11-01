package com.upc.unistress.repositorios;

import com.upc.unistress.entidades.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findBySuscripcion_Id(Long suscripcionId);

}