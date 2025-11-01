package com.upc.unistress.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "perfil")
public class Perfil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación 1:1 con Usuario
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "tipo_perfil", nullable = false, length = 20)
    private String tipoPerfil; // 'ESTUDIANTE' o 'PSICOLOGO'

    @Column(length = 100)
    private String universidad;

    @Column(length = 100)
    private String carrera;

    @Column(length = 20)
    private String ciclo;

    @Column(name = "estado_academico", length = 20)
    private String estadoAcademico;

    @Column(length = 100)
    private String especialidad;

    @Column(length = 50)
    private String colegiatura;

    @Column(name = "años_experiencia")
    private Integer añosExperiencia;

    @Column(name="foto_url")
    private String fotoUrl;

    @Column(name="descripcion")
    private String descripcion;



    public Perfil(Usuario usuario, String tipoPerfil, String universidad, String carrera, String ciclo,
                  String estadoAcademico, String especialidad, String colegiatura,
                  Integer añosExperiencia, String fotoUrl, String descripcion) {
        this.usuario = usuario;
        this.tipoPerfil = tipoPerfil;
        this.universidad = universidad;
        this.carrera = carrera;
        this.ciclo = ciclo;
        this.estadoAcademico = estadoAcademico;
        this.especialidad = especialidad;
        this.colegiatura = colegiatura;
        this.añosExperiencia = añosExperiencia;
        this.fotoUrl = fotoUrl;
        this.descripcion = descripcion;
    }
}
