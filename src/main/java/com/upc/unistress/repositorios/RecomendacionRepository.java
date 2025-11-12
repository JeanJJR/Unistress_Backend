package com.upc.unistress.repositorios;

import com.upc.unistress.entidades.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecomendacionRepository extends JpaRepository<Recomendacion, Long> {
    List<Recomendacion> findByTipo(String tipo);
    List<Recomendacion> findByRegistroEmocional_Usuario_Id(Long usuarioId);
    // Esta busca por el autor/creador (el psicólogo)
    List<Recomendacion> findByUsuario_Id(Long usuarioId);

}