package com.upc.unistress.config;

import com.upc.unistress.entidades.BancoPregunta;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.repositorios.BancoPreguntaRepository;
import com.upc.unistress.repositorios.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreguntasInitializer {

    @Bean
    CommandLineRunner initPreguntas(BancoPreguntaRepository bancoPreguntaRepository,
                                    UsuarioRepository usuarioRepository) {
        return args -> {
            // Buscar al psicólogo que creará las preguntas (ejemplo: Lucía)
            Usuario psicologo = usuarioRepository.findByCorreo("lucia.psico@upc.edu.pe")
                    .orElseThrow(() -> new RuntimeException("Psicólogo no encontrado"));

            if (!"ROLE_PSICOLOGO".equalsIgnoreCase(psicologo.getRol().getTipoRol())) {
                throw new RuntimeException("El usuario no tiene rol de psicólogo.");
            }

            // ====== CREAR PREGUNTAS DEL TEST EMOCIONAL ======
            crearPregunta("Me he sentido motivado para realizar mis actividades diarias.", psicologo, bancoPreguntaRepository);
            crearPregunta("He tenido dificultades para concentrarme en mis estudios o trabajo.", psicologo, bancoPreguntaRepository);
            crearPregunta("Me he sentido con energía suficiente durante la semana.", psicologo, bancoPreguntaRepository);
            crearPregunta("He experimentado pensamientos negativos de manera frecuente.", psicologo, bancoPreguntaRepository);
            crearPregunta("He podido manejar adecuadamente situaciones de estrés.", psicologo, bancoPreguntaRepository);
            crearPregunta("He sentido apoyo emocional de las personas cercanas a mí.", psicologo, bancoPreguntaRepository);
            crearPregunta("Me he sentido ansioso o preocupado sin una razón clara.", psicologo, bancoPreguntaRepository);
            crearPregunta("He disfrutado de las actividades que normalmente me gustan.", psicologo, bancoPreguntaRepository);
            crearPregunta("He tenido problemas para dormir o descansar adecuadamente.", psicologo, bancoPreguntaRepository);
            crearPregunta("Me he sentido capaz de controlar mis emociones en situaciones difíciles.", psicologo, bancoPreguntaRepository);

            System.out.println("Banco de 10 preguntas emocionales inicializado correctamente.");
        };
    }

    private void crearPregunta(String enunciado, Usuario psicologo, BancoPreguntaRepository repo) {
        BancoPregunta pregunta = new BancoPregunta();
        pregunta.setEnunciado(enunciado);
        pregunta.setPsicologo(psicologo); // asigna el psicólogo creador
        repo.save(pregunta);
    }
}
