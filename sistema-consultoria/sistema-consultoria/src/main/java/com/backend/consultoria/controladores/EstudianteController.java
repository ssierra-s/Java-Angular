package com.backend.consultoria.controladores;

import com.backend.consultoria.entidades.Estudiante;
import com.backend.consultoria.servicios.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin("*")
public class EstudianteController {
    @Autowired
    private EstudianteService service;

    @GetMapping
    public List<Estudiante> listarEstudiantes() {
        return service.listarEstudiantes();
    }

    // Obtener un estudiante por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiantePorId(@PathVariable String id) {
        Optional<Estudiante> estudianteOpt = service.obtenerEstudiantePorId(id);
        if (estudianteOpt.isPresent()) {
            return ResponseEntity.ok(estudianteOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar estudiante existente
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarEstudiante(@PathVariable String id, @RequestBody Estudiante estudianteActualizado) {
        Optional<Estudiante> estudianteOpt = service.obtenerEstudiantePorId(id);
        if (estudianteOpt.isPresent()) {
            Estudiante estudianteExistente = estudianteOpt.get();

            // Actualizar campos
            estudianteExistente.setTipoDocumento(estudianteActualizado.getTipoDocumento());
            estudianteExistente.setNombres(estudianteActualizado.getNombres());
            estudianteExistente.setApellidos(estudianteActualizado.getApellidos());
            estudianteExistente.setFechaNacimiento(estudianteActualizado.getFechaNacimiento());
            estudianteExistente.setEdad(estudianteActualizado.getEdad());
            estudianteExistente.setGradoActual(estudianteActualizado.getGradoActual());
            estudianteExistente.setEmail(estudianteActualizado.getEmail());
            estudianteExistente.setTelefonoFijo(estudianteActualizado.getTelefonoFijo());
            estudianteExistente.setCelular(estudianteActualizado.getCelular());

            Estudiante estudianteGuardado = service.guardarEstudianteA(estudianteExistente);
            return ResponseEntity.ok(estudianteGuardado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/")
    public ResponseEntity<?> guardarEstudiante(@RequestBody Estudiante estudiante) {
        try {
            Estudiante nuevoEstudiante = service.guardarEstudiante(estudiante);
            return ResponseEntity.ok(nuevoEstudiante);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable String id) {
        Optional<Estudiante> estudianteOpt = service.obtenerEstudiantePorId(id);
        if (estudianteOpt.isPresent()) {
            service.eliminarEstudiante(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener estudiantes de Grado 11
    @GetMapping("/grado11")
    public ResponseEntity<List<Estudiante>> obtenerEstudiantesPorGrado11() {
        List<Estudiante> estudiantes = service.obtenerEstudiantesPorGrado11();
        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(estudiantes);
        }
    }

    // Obtener estudiantes con edad entre 15 y 17 años
    @GetMapping("/edad15a17")
    public ResponseEntity<List<Estudiante>> obtenerEstudiantesPorEdad15a17() {
        List<Estudiante> estudiantes = service.obtenerEstudiantesPorEdadEntre15y17();
        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(estudiantes);
        }
    }

}

