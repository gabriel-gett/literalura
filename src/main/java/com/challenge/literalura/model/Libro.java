package com.challenge.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String idiomas;
    private Double descargas;
    private String autor;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autor = getFirstAuthor(datosLibro).getNombre();
        this.idiomas = getFirstLanguage(datosLibro);
        this.descargas = datosLibro.descargas();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public Autor getFirstAuthor(DatosLibro datosLibro) {
        DatosAutor datosAutor = datosLibro.autor().getFirst();
        return new Autor(datosAutor);
    }

    public String getFirstLanguage(DatosLibro datosLibro) {
        return datosLibro.idiomas().get(0);
    }

    @Override
    public String toString() {
        return "**** Informacion del libro ****" +
                "\n\tTitulo: " + titulo +
                "\n\tAutor: " + autor +
                "\n\tidioma: " + idiomas +
                "\n\tDescargas: " + descargas;
    }
}
