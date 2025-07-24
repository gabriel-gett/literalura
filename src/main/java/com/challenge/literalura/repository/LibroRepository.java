package com.challenge.literalura.repository;

import com.challenge.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContains(String titulo);
    List<Libro> findByIdiomasContains(String idiomas);
}
