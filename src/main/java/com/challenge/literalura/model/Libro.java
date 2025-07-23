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
    private String autor;
    private String idiomas;
    private Double descargas;

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

    public String getTitle() {
        return titulo;
    }

    public void setTitle(String title) {
        this.titulo = title;
    }

    public String getAuthor() {
        return autor;
    }

    public void setAuthor(String author) {
        this.autor = author;
    }

    public String getLanguage() {
        return idiomas;
    }

    public void setLanguage(String language) {
        this.idiomas = language;
    }

    public Double getDownloads() {
        return descargas;
    }

    public void setDownloads(Double downloads) {
        this.descargas = downloads;
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
        return "**** Book Info ****" +
                "\n\tTitulo: " + titulo +
                "\n\tAutor: " + autor +
                "\n\tidioma: " + idiomas +
                "\n\tDescargas: " + descargas;
    }
}
