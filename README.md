# Literalura 📚

## Descripción
Literalura es una aplicación de consola desarrollada en **Java 17** con **Spring Boot 3.2.3** que permite consumir información de libros desde la API pública [Gutendex](https://gutendex.com/) y almacenarla en una base de datos **PostgreSQL**.

El objetivo del proyecto es demostrar habilidades en:

- Consumo de APIs con `HttpClient`
- Conversión de JSON a objetos Java usando **Jackson**
- Persistencia de datos con **Spring Data JPA**
- Uso de **Derived Queries**
- Creación de aplicaciones **100% por consola** con menús interactivos

---

## Tecnologías utilizadas

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| Java | 17 | Lenguaje de programación principal |
| Spring Boot | 3.2.3 | Framework para crear la aplicación y manejar dependencias |
| Maven | 4.0.0 | Gestión de dependencias y compilación |
| PostgreSQL | 15+ | Base de datos para almacenar libros y autores |
| Jackson | 2.16 | Librería para convertir JSON a objetos Java |
| HttpClient | Java 17+ | Para consumir APIs externas |
| IntelliJ IDEA | 2025.2 | IDE utilizado |

---

## Estructura del proyecto

```text
com.alura.literalura
│
├── dto
│   ├── DatosAutor.java
│   ├── DatosLibro.java
│   └── DatosRespuesta.java
│
├── model
│   ├── Autor.java
│   └── Libro.java
│
├── repository
│   ├── AutorRepository.java
│   └── LibroRepository.java
│
├── service
│   ├── ConsumoAPI.java
│   └── ConvierteDatos.java
│
├── principal
│   └── Principal.java
│
└── LiteraluraApplication.java