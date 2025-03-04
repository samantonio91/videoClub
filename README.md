# Sistema de Videoclub - Microservicio Spring Boot 3 con Java 17 y MySQL

Este proyecto es un ejercicio de aprendizaje donde he desarrollado un sistema de videoclub basado en un diagrama UML, implementado como un microservicio Spring Boot 3 con Java 17 y persistencia de datos en MySQL utilizando JPA.

## Descripción

El objetivo principal de este proyecto es practicar y demostrar mis habilidades en:

* **Diseño basado en UML**: Traducir un diagrama de clases UML a una implementación funcional.
* **Desarrollo de microservicios**: Construir una aplicación modular y escalable utilizando Spring Boot 3.
* **Java 17**: Aprovechar las características y mejoras del lenguaje Java más reciente.
* **Persistencia de datos**: Utilizar JPA para interactuar con una base de datos MySQL.  **implementando el patrón DAO para la capa de acceso a datos.**
* **Patrón Singleton**: Implementar el patrón Singleton en la clase controladora `VideoClub`.
* * **Transferencia de datos**: **Utilizar un DTO para la persistencia de la relación entre Cliente y Alquiler.**

El sistema permite gestionar clientes, productos (juegos y películas) y alquileres, siguiendo las relaciones y funcionalidades definidas en el diagrama UML proporcionado.

## Stack Tecnológico

* **Lenguaje**: Java 17
* **Framework**: Spring Boot 3
* **Base de datos**: MySQL
* **Persistencia**: JPA (Hibernate)
* **Patrón de diseño**: Singleton

## Patrones de Diseño

* **DAO (Data Access Object):** Se utiliza para la capa de acceso a datos, separando la lógica de negocio de la lógica de persistencia.
* **DTO (Data Transfer Object):** Se utiliza para la transferencia de datos entre la capa de presentación y la capa de persistencia, específicamente para la relación entre Cliente y Alquiler.
* **Singleton:** Se implementa en la clase controladora `VideoClub` para garantizar una única instancia.

## Funcionalidades Principales

* Gestión de clientes: Agregar, modificar, eliminar, buscar y mostrar clientes.
* Gestión de productos (juegos y películas): Agregar, modificar.
* Gestión de alquileres: Registrar alquileres, incluyendo la relación con clientes y productos.
* Consulta de información: Mostrar listados de clientes y productos.

## Diagrama UML

[Incluye aquí una imagen o enlace al diagrama UML que utilizaste como base para el proyecto]

## Cómo Ejecutar el Proyecto

1.  Asegúrate de tener instalado Java 17 y MySQL.
2.  Clona este repositorio.
3.  Crea una base de datos MySQL con el nombre especificado en `application.properties`.
4.  Configura las credenciales de la base de datos en `application.properties`.
5.  Ejecuta la aplicación Spring Boot desde tu IDE o utilizando Gradle: `mvn spring-boot:run`.

## Próximos Pasos

* Implementar pruebas unitarias y de integración.
* Agregar validaciones y manejo de errores.
* Mejorar la interfaz de usuario (si se incluye una).
* Implementar servicios REST para exponer la funcionalidad del microservicio.
* Agregar documentación de la API utilizando Swagger o OpenAPI.

## Autor

[samantonio91] ([samantonio91](https://github.com/samantonio91))

## Contacto

[sam.antonio.91@gmail.com]

## Licencia

[1.0.0]