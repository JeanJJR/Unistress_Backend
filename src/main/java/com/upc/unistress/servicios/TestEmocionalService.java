package com.upc.unistress.servicios;

import com.upc.unistress.dtos.TestEmocionalDTO;
import com.upc.unistress.entidades.BancoPregunta;
import com.upc.unistress.entidades.PreguntaTest;
import com.upc.unistress.entidades.TestEmocional;
import com.upc.unistress.entidades.Usuario;
import com.upc.unistress.interfaces.ITestEmocionalService;
import com.upc.unistress.repositorios.BancoPreguntaRepository;
import com.upc.unistress.repositorios.TestEmocionalRepository;
import com.upc.unistress.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class TestEmocionalService implements ITestEmocionalService {

    @Autowired
    private TestEmocionalRepository repository;
    @Autowired
    private BancoPreguntaRepository bancoRepo;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void insertar(TestEmocionalDTO dto) {
        TestEmocional test = modelMapper.map(dto, TestEmocional.class);
        repository.save(test);
    }

    @Override
    public List<TestEmocionalDTO> listar() {
        return repository.findAll()
                .stream()
                .map(test -> modelMapper.map(test, TestEmocionalDTO.class))
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public TestEmocionalDTO listId(Long id) {
        return repository.findById(id)
                .map(test -> modelMapper.map(test, TestEmocionalDTO.class))
                .orElseThrow(() -> new RuntimeException("Test emocional con ID " + id + " no encontrado"));
    }

    @Override
    public List<TestEmocionalDTO> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuario_Id(usuarioId)
                .stream()
                .map(test -> modelMapper.map(test, TestEmocionalDTO.class))
                .toList();
    }

    @Override
    public List<TestEmocionalDTO> listarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return repository.findByFechaBetween(inicio, fin)
                .stream()
                .map(test -> modelMapper.map(test, TestEmocionalDTO.class))
                .toList();
    }
    private String calcularNivelEstres(int puntaje) {
        if (puntaje <= 10) return "Bajo";
        if (puntaje <= 20) return "Moderado";
        return "Alto";
    }
    @Transactional
    @Override
    public TestEmocionalDTO resolver(TestEmocionalDTO dto) {
        TestEmocional test = new TestEmocional();
        Usuario estudiante = new Usuario();
        estudiante.setId(dto.getUsuarioId());
        test.setUsuario(estudiante);
        test.setFecha(LocalDate.now());

        // Selección aleatoria de preguntas
        List<BancoPregunta> todasPreguntas = bancoRepo.findAll(); // <- usar instancia
        Collections.shuffle(todasPreguntas);
        int numeroPreguntas = 5; // cantidad de preguntas a tomar
        List<BancoPregunta> preguntasSeleccionadas = todasPreguntas.stream()
                .limit(numeroPreguntas)
                .toList();

        // Construcción de PreguntaTest
        List<PreguntaTest> preguntas = preguntasSeleccionadas.stream().map(bp -> {
            PreguntaTest pt = new PreguntaTest();
            pt.setTest(test);
            pt.setPregunta(bp.getEnunciado());
            pt.setPuntaje(0); // asignar puntaje según respuesta del usuario
            return pt;
        }).toList();

        int puntajeTotal = preguntas.stream().mapToInt(PreguntaTest::getPuntaje).sum();
        test.setPreguntas(preguntas);
        test.setPuntajeTotal(puntajeTotal);
        test.setNivelEstres(calcularNivelEstres(puntajeTotal));

        repository.save(test);
        return modelMapper.map(test, TestEmocionalDTO.class);
    }







}