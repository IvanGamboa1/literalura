package com.alura.literalura.principal;

import com.alura.literalura.dto.DatosAutor;
import com.alura.literalura.dto.DatosLibro;
import com.alura.literalura.dto.DatosRespuesta;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository,
                     AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("""
                    
                    ------------------------------
                           LITERALURA
                    ------------------------------
                    
                    1 - Buscar libro por título
                    2 - Listar libros guardados
                    3 - Listar autores
                    4 - Listar autores vivos en un año
                    5 - Listar libros por idioma
                    0 - Salir
                    
                    Elija una opción:
                    """);

            opcion = teclado.nextInt();
            teclado.nextLine();

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
                    System.out.println("Cerrando aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void buscarLibro() {

        System.out.println("Ingrese el título del libro:");
        String titulo = teclado.nextLine();

        String json = consumoAPI.obtenerDatos(URL_BASE + titulo.replace(" ", "+"));

        DatosRespuesta respuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (respuesta.results().isEmpty()) {
            System.out.println("Libro no encontrado.");
            return;
        }

        DatosLibro datosLibro = respuesta.results().get(0);
        DatosAutor datosAutor = datosLibro.authors().get(0);

        Autor autor = new Autor(
                datosAutor.name(),
                datosAutor.birthYear(),
                datosAutor.deathYear()
        );

        autorRepository.save(autor);

        Libro libro = new Libro(
                datosLibro.title(),
                datosLibro.languages().get(0),
                datosLibro.download_count(),
                autor
        );

        libroRepository.save(libro);

        System.out.println("\nLibro guardado exitosamente:");
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autor: " + autor.getNombre());
        System.out.println("Idioma: " + libro.getIdioma());
        System.out.println("Descargas: " + libro.getNumeroDescargas());
    }

    private void listarLibros() {

        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados.");
            return;
        }

        libros.forEach(libro -> {
            System.out.println("------------------------------");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getNumeroDescargas());
        });
    }

    private void listarAutores() {

        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados.");
            return;
        }

        autores.forEach(autor -> {
            System.out.println("------------------------------");
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getAnoNacimiento());
            System.out.println("Fallecimiento: " + autor.getAnoFallecimiento());
        });
    }

    private void listarAutoresVivos() {

        System.out.println("Ingrese el año:");
        int ano = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = autorRepository
                .findByAnoNacimientoLessThanEqualAndAnoFallecimientoGreaterThanEqual(ano, ano);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
            return;
        }

        autores.forEach(autor ->
                System.out.println("Autor: " + autor.getNombre())
        );
    }

    private void listarLibrosPorIdioma() {

        System.out.println("""
                Ingrese el idioma:
                es - Español
                en - Inglés
                fr - Francés
                """);

        String idioma = teclado.nextLine();

        List<Libro> libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros en ese idioma.");
            return;
        }

        System.out.println("Cantidad de libros: " + libros.size());

        libros.forEach(libro ->
                System.out.println("Título: " + libro.getTitulo())
        );
    }
}
