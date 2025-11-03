package com.upc.unistress.config;

import com.upc.unistress.entidades.*;
import com.upc.unistress.repositorios.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RolRepository rolRepository,
                                   UsuarioRepository usuarioRepository,
                                   SuscripcionRepository suscripcionRepository,
                                   MetodoPagoRepository metodoPagoRepository,
                                   PerfilRepository perfilRepository) {
        return args -> {

            // ====== CREAR ROLES ======
            Rol rolAdmin = new Rol();
            rolAdmin.setTipoRol("ROLE_ADMIN");
            rolRepository.save(rolAdmin);

            Rol rolEstudiante = new Rol();
            rolEstudiante.setTipoRol("ROLE_ESTUDIANTE");
            rolRepository.save(rolEstudiante);

            Rol rolPsicologo = new Rol();
            rolPsicologo.setTipoRol("ROLE_PSICOLOGO");
            rolRepository.save(rolPsicologo);

            // ====== CREAR USUARIOS ======
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            Usuario admin = new Usuario();
            admin.setNombre("Admin");
            admin.setApellidos("General");
            admin.setCorreo("admin@upc.edu.pe");
            admin.setContraseña(passwordEncoder.encode("123456"));
            admin.setTelefono("999999999");
            admin.setRol(rolAdmin);
            usuarioRepository.save(admin);
            // No perfil ni suscripción

            Usuario carlos = new Usuario();
            carlos.setNombre("Carlos");
            carlos.setApellidos("Pérez");
            carlos.setCorreo("carlos@upc.edu.pe");
            carlos.setContraseña(passwordEncoder.encode("123456"));
            carlos.setTelefono("987654321");
            carlos.setRol(rolEstudiante);
            usuarioRepository.save(carlos);
            crearSuscripcionBasica(carlos, suscripcionRepository);
            crearPerfilEstudiante(carlos, perfilRepository);

            Usuario lucia = new Usuario();
            lucia.setNombre("Lucía");
            lucia.setApellidos("Ramírez");
            lucia.setCorreo("lucia.psico@upc.edu.pe");
            lucia.setContraseña(passwordEncoder.encode("123456"));
            lucia.setTelefono("912345678");
            lucia.setRol(rolPsicologo);
            usuarioRepository.save(lucia);
            crearPerfilPsicologo(lucia, perfilRepository);

            // Métodos de pago
            crearMetodoPago("Tarjeta de crédito", metodoPagoRepository);
            crearMetodoPago("Yape", metodoPagoRepository);

            System.out.println("Roles, Usuarios, Perfiles, Suscripciones y Métodos de Pago inicializados correctamente.");
        };
    }

    private void crearSuscripcionBasica(Usuario usuario, SuscripcionRepository repo) {
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setUsuario(usuario);
        suscripcion.setFechaInicio(LocalDate.now());
        suscripcion.setFechaFin(LocalDate.now().plusMonths(1));
        suscripcion.setEstado("ACTIVA");
        suscripcion.setTipo("BASICA");
        repo.save(suscripcion);
    }

    private void crearMetodoPago(String tipo, MetodoPagoRepository repo) {
        MetodoPago metodo = new MetodoPago();
        metodo.setTipoMetodo(tipo);
        repo.save(metodo);
    }

    private void crearPerfilEstudiante(Usuario usuario, PerfilRepository repo) {
        Perfil perfil = new Perfil(
                usuario,
                "ESTUDIANTE",
                "Universidad Peruana de Ciencias Aplicadas",
                "Ingeniería de Sistemas",
                "7",
                "Regular",
                null, // especialidad
                null, // colegiatura
                null, // añosExperiencia
                "https://res.cloudinary.com/dzgdttwjj/image/upload/v1762152783/m5swju7acyd2zr51sobc.jpg",
                "Estudiante comprometido con su formación académica."
        );
        repo.save(perfil);
    }

    private void crearPerfilPsicologo(Usuario usuario, PerfilRepository repo) {
        Perfil perfil = new Perfil(
                usuario,
                "PSICOLOGO",
                null,
                null,
                null,
                null,
                "Psicología Clínica",
                "12345-CMP",
                5,
                "https://example.com/foto-psicologo.jpg",
                "Psicóloga con experiencia en terapia cognitivo-conductual."
        );
        repo.save(perfil);
    }
}
