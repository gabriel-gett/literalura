package com.challenge.literalura.principal;

import com.challenge.literalura.model.Datos;
import com.challenge.literalura.service.ConsumoAPI;
import com.challenge.literalura.service.ConvierteDatos;

import java.util.Scanner;

public class Principal {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private static final String URL_BASE = "https://gutendex.com/books/";
    Scanner teclado = new Scanner(System.in);

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ELIGE UNA OPCION
                    1.- Buscar libro por titulo.
                    2.- Listar libros registrados.
                    3.- Listar autores registrados.
                    4.- Listar autores vivos en un año.
                    5.- Listar libros por idioma
                    0.- Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autoresVivos();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 0:
                    System.out.println("saliendo...");
                    break;
                default:
                    System.out.println("opcion invalida");
            }
        }
    }
    
}
