package com.upc.unistress.repositorios;

import com.upc.unistress.entidades.RegistroEmocional;
import com.upc.unistress.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistroEmocionalRepository extends JpaRepository<RegistroEmocional, Long> {
    List<RegistroEmocional> findByUsuario_Id(Long usuarioId);
    List<RegistroEmocional> findByFechaRegistroBetween(LocalDateTime inicio, LocalDateTime fin);
    // Promedio de nivel emocional por emoción
    @Query("SELECT r.emocion, AVG(r.nivel) FROM RegistroEmocional r " +
            "WHERE r.usuario.id = :usuarioId GROUP BY r.emocion")
    List<Object[]> promedioNivelPorEmocion(@Param("usuarioId") Long usuarioId);

    // Evolución de emociones en el tiempo
    @Query("SELECT r.fechaRegistro, r.emocion, r.nivel FROM RegistroEmocional r " +
            "WHERE r.usuario.id = :usuarioId ORDER BY r.fechaRegistro ASC")
    List<Object[]> evolucionEmociones(@Param("usuarioId") Long usuarioId);

    @Query("SELECT r.fechaRegistro, r.emocion, r.nivel FROM RegistroEmocional r " +
            "WHERE r.usuario.id = :usuarioId AND r.fechaRegistro BETWEEN :fechaInicio AND :fechaFin " +
            "ORDER BY r.fechaRegistro ASC")
    List<Object[]> evolucionEmocionesEntreFechas(@Param("usuarioId") Long usuarioId,
                                                 @Param("fechaInicio") java.time.LocalDateTime fechaInicio,
                                                 @Param("fechaFin") java.time.LocalDateTime fechaFin);
    // Promedio y conteo por emoción (para todos los estudiantes)
    @Query("SELECT r.emocion, AVG(r.nivel), COUNT(r) " +
            "FROM RegistroEmocional r " +
            "WHERE r.fechaRegistro BETWEEN :inicio AND :fin " +
            "GROUP BY r.emocion")
    List<Object[]> obtenerTendenciasEmocionales(LocalDateTime inicio, LocalDateTime fin);

    // Total de usuarios registrados
    @Query("SELECT COUNT(u) FROM Usuario u")
    Long totalUsuarios();

    // Total de sesiones registradas
    @Query("SELECT COUNT(s) FROM Sesion s")
    Long totalSesiones();

    // Total de psicólogos registrados
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol.tipoRol = 'PSICOLOGO'")
    Long totalPsicologos();

    // Total de estudiantes registrados
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol.tipoRol = 'ESTUDIANTE'")
    Long totalEstudiantes();

    @Query(value = "SELECT COUNT(*) > 0 FROM registro_emocional r " +
            "WHERE r.usuario_id = :usuarioId AND DATE(r.fecha_registro) = CURRENT_DATE",
            nativeQuery = true)
    boolean existeRegistroHoy(@Param("usuarioId") Long usuarioId);


    @Modifying
    @Query("DELETE FROM RegistroEmocional re WHERE re.usuario.id = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") Long usuarioId);









}