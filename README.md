# VideoClub — Java 17 · Spring Boot 3

Proyecto de aprendizaje inspirado en el diagrama de clases UML incluido en `images/uml.png`. Se implementaron las **siete operaciones de la clase VideoClub** con una API REST y separación Controller → Service → Repository → JPA.

## Modelo

- `Cliente`: identificador, documento, nombre, teléfono y email.
- `Producto`: clase base con código, título y precio.
- `Juego` y `Pelicula`: especializaciones JPA de `Producto` con estrategia `JOINED`.
- `Alquiler`: vínculo con `Cliente` y `Producto`, fechas y estado.
- Spring administra los servicios como singletons; **no** se creó un singleton estático manual aunque el UML lo menciona.
- `GeneroPelicula` y las operaciones de alquiler no forman parte de las siete operaciones del controlador UML; su implementación queda pendiente.

## API implementada

| UML | Método HTTP | Ruta |
| --- | --- | --- |
| AgregarCliente | POST | `/api/clientes` |
| ModificarCliente | PUT | `/api/clientes/{codigo}` |
| EliminarCliente | DELETE | `/api/clientes/{codigo}` |
| MostrarClientes | GET | `/api/clientes?documento=ABC&nombre=Ana` |
| BuscarCliente | GET | `/api/clientes/{codigo}` |
| AgregarJuego | POST | `/api/juegos` |
| ModificarJuego | PUT | `/api/juegos/{codigo}` |

Los códigos de las rutas de cliente/juego son los identificadores de base de datos (`Long`); el atributo `codigo` de `Producto` permanece como campo de dominio. Las búsquedas devuelven `404` cuando no existe el registro, `400` para entradas inválidas, y `409` si se intenta eliminar un cliente con alquileres asociados.

**Compatibilidad:** también se conservan las rutas anteriores `POST /api/save-cliente` y `GET /api/get-client?cliente_id=1`.

### Crear cliente

```bash
curl -X POST http://localhost:8080/api/clientes -H 'Content-Type: application/json' -d '{"documento":"ABC123","nombre":"Ana García","telefono":"555-0101","email":"ana@example.com"}'
```

### Crear juego

```bash
curl -X POST http://localhost:8080/api/juegos -H 'Content-Type: application/json' -d '{"codigo":101,"titulo":"Juego de ejemplo","precio":199.0,"plataforma":"PC","genero":"Aventura"}'
```

## Desarrollo local

1. Instala **JDK 17** y **MySQL**; crea la base `videoclub`.
2. Configura `DB_USERNAME` y `DB_PASSWORD` como variables de entorno locales.
3. Ejecuta `./gradlew bootRun` (Windows: `gradlew.bat bootRun`).
4. Para pruebas: `./gradlew test` (usando H2 en memoria; no requiere MySQL).

**Importante:** los cambios en la herencia `Producto/Juego/Pelicula`, las fechas de `Alquiler` y el campo `Cliente.documento` alteran el esquema. Usa una base de datos de desarrollo limpia o crea migraciones antes de conservar datos preexistentes. No utilices `ddl-auto=update` para migrar datos de producción.

## Pruebas

- Unitarias para altas, modificaciones, búsquedas, borrados y validaciones.
- Integración JPA para herencia y filtros de clientes.
- GitHub Actions ejecuta `./gradlew test` en PR y push a `main`.

## Alcance y siguientes mejoras

Este es un proyecto didáctico, no un sistema de alquiler listo para producción: faltan autenticación, operaciones de préstamos/devoluciones, migraciones de esquema, API OpenAPI y pruebas de extremo a extremo. Las siete operaciones descritas se implementaron; deben verificarse con las pruebas de CI antes de fusionar.

![UML de VideoClub](images/uml.png)
