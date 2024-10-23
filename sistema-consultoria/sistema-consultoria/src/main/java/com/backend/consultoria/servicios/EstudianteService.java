package com.backend.consultoria.servicios;

import com.backend.consultoria.entidades.Estudiante;
import com.backend.consultoria.repositorios.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository repository;

    public List<Estudiante> listarEstudiantes() {
        return repository.findAll();
    }

    public Optional<Estudiante> obtenerEstudiantePorId(String id) {
        return repository.findById(id);
    }

    public Estudiante guardarEstudiante(Estudiante estudiante) {
        // Verificar si ya existe un estudiante con el mismo número de identidad
        if (repository.existsByNumeroIdentidad(estudiante.getNumeroIdentidad())) {
            throw new IllegalArgumentException("Ya existe un estudiante con el mismo número de identidad.");
        }
        return repository.save(estudiante);
    }

    public Estudiante guardarEstudianteA(Estudiante estudiante) {

        return repository.save(estudiante);
    }

    public void eliminarEstudiante(String id) {
        repository.deleteById(id);
    }

    public List<Estudiante> obtenerEstudiantesPorGrado11() {
        return repository.findAllByGrado11();
    }

    public List<Estudiante> obtenerEstudiantesPorEdadEntre15y17() {
        return repository.findAllByEdadBetween();
    }
}
