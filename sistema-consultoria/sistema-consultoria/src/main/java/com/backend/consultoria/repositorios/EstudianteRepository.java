package com.backend.consultoria.repositorios;

import com.backend.consultoria.entidades.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, String> {

    @Query("SELECT e FROM Estudiante e WHERE e.gradoActual= 11")
    List<Estudiante> findAllByGrado11();

    @Query("SELECT e FROM Estudiante e WHERE e.edad BETWEEN 15 AND 17")
    List<Estudiante> findAllByEdadBetween();

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Estudiante e WHERE e.numeroIdentidad = :numeroIdentidad")
    boolean existsByNumeroIdentidad(@Param("numeroIdentidad") String numeroIdentidad);
}
