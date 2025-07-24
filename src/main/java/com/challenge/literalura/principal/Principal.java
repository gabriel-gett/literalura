package com.challenge.literalura.principal;

import com.challenge.literalura.model.Autor;
import com.challenge.literalura.model.DatosLibro;
import com.challenge.literalura.model.Libro;
import com.challenge.literalura.model.Resultados;
import com.challenge.literalura.repository.AutorRepository;
import com.challenge.literalura.repository.LibroRepository;
import com.challenge.literalura.service.ConsumoAPI;
import com.challenge.literalura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    List<Libro> libros;
    List<Autor> autores;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu(){
        final var menu = """
                *****  Seleccione una opcion. *****
                
                1.- Buscar libro por titulo
                2.- Listar libros registrados
                3.- Listar autores registrados
                4.- Listar autores vivos por año
                5.- listar libros por lenguaje
                0.- salir
                """;
        var opcion = -1;
        while (opcion != 0) {
            System.out.println(menu);
            System.out.println("opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("opcion invalida");
                    break;
            }
        }
        System.out.println("****************************************");
    }
    private void buscarLibro() {
        System.out.print("ingrese el titulo que desea buscar: ");
        String busqueda = scanner.nextLine();
        var json = consumoAPI.getData(busqueda.replace(" ", "%20"));
        var data = convierteDatos.getData(json, Resultados.class);
        if (data.results().isEmpty()) {
            System.out.println("""
                    *****************************
                        Libro no encontrado
                    *****************************
                    """);
        } else {
            DatosLibro datosLibro = data.results().getFirst();
            Libro libro = new Libro(datosLibro);
            Autor autor = new Autor().getFirstAuthor(datosLibro);
            saveData(libro, autor);

            System.out.println("------LIBRO--------");
            System.out.println("Título: " + datosLibro.titulo());
            System.out.println("Autor: " + (datosLibro.autor().isEmpty() ? "Desconocido" : datosLibro.autor().get(0).nombre()));
            System.out.println("Idioma: " + datosLibro.idiomas().get(0));
            System.out.println("Número de descargas: " + datosLibro.descargas());


        }
    }
    private void saveData(Libro libro, Autor autor) {
        Optional<Libro> bookFound = libroRepository.findByTituloContains(libro.getTitulo());
        if (bookFound.isPresent()) {
            System.out.println("""
                    ********************************
                    Este libro ya fue registrado
                    ********************************
                    """);
        } else {
            try {
                libroRepository.save(libro);
                System.out.println("""
                        *****************************
                        libro registrado con exito
                        *****************************""");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        Optional<Autor> authorFound = autorRepository.findByNombreContains(autor.getNombre());
        if (authorFound.isPresent()) {
            System.out.println("""
                    *******************************
                    este autor ya fue registrado
                    *******************************
                    """);
        } else {
            try {
                autorRepository.save(autor);
                System.out.println("""
                        *******************************
                        autor registrado
                        *******************************
                        """);
            } catch (Exception e) {
                System.out.println("Error message: " + e.getMessage());
            }
        }
    }
    private void listarLibros() {
        System.out.println("libros registrados\n---------------------");
        libros = libroRepository.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutores() {
        System.out.println("autores registrados\n-----------------------");
        autores = autorRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        System.out.print("lista de autores vivos por año... ingrese el año: ");
        Integer year = Integer.valueOf(scanner.nextLine());
        autores = autorRepository
                .findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(year, year);
        if (autores.isEmpty()) {
            System.out.println("autores no encontrados");
        } else {
            autores.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("listar libros por idioma\n----------------------");
        System.out.println("""
                \n\t---- seleccione el idioma ----
                \ten - English
                \tes - Spanish
                \tfr - French
                \tpt - Portuguese
                """);
        String lang = scanner.nextLine();
        libros = libroRepository.findByIdiomasContains(lang);
        if (libros.isEmpty()) {
            System.out.println("libros no encontrados");
        } else {
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(System.out::println);
        }
    }
}
