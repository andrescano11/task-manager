# Task Manager

## Descripción
Gestor de tareas (backend) desarrollado con Spring Boot. Este proyecto permite la gestión de tareas, incluyendo la creación, actualización, eliminación y consulta de tareas. También incluye funcionalidades de autenticación y autorización.

## Tecnologías
- Java
- Spring Boot
- Maven
- JPA (Java Persistence API)
- SQL

## Estructura del Proyecto
- `src/main/java/com/sena/taskmanager/controller`: Controladores REST para manejar las solicitudes HTTP.
- `src/main/java/com/sena/taskmanager/dto`: Clases DTO (Data Transfer Object) para transferir datos entre las capas.
- `src/main/java/com/sena/taskmanager/entity`: Entidades JPA que representan las tablas de la base de datos.
- `src/main/java/com/sena/taskmanager/repository`: Repositorios JPA para interactuar con la base de datos.
- `src/main/java/com/sena/taskmanager/service`: Servicios que contienen la lógica de negocio.

## Endpoints
### Autenticación
- `POST /api/v*/auth/register/{userRole}`: Registrar un nuevo usuario.
- `POST /api/v*/auth/login`: Iniciar sesión.

### Tareas
- `POST /api/v*/task`: Crear una nueva tarea.
- `GET /api/v*/task`: Obtener todas las tareas.
- `GET /api/v*/task/{taskId}`: Obtener una tarea por su ID.
- `PUT /api/v*/task/{taskId}`: Actualizar una tarea.
- `DELETE /api/v*/task/{taskId}`: Eliminar una tarea.

### Checks
- `POST /api/v*/{taskId}/check`: Crear un nuevo check.
- `GET /api/v*/{taskId}/check`: Obtener todos los checks de una tarea.
- `GET /api/v*/{taskId}/check/{checkId}`: Obtener un check por su ID.
- `PUT /api/v*/{taskId}/check/{checkId}`: Actualizar un check.
- `DELETE /api/v*/{taskId}/check/{checkId}`: Eliminar un check.

### Tags
- `POST /api/v*/tag`: Crear un nuevo tag.
- `GET /api/v*/tag`: Obtener todos los tags.
- `GET /api/v*/tag/{tagName}`: Obtener un tag por su nombre.
- `PUT /api/v*/tag/{tagName}`: Actualizar un tag.
- `DELETE /api/v*/tag/{tagName}`: Eliminar un tag

## Instalación
1. Clonar el repositorio:
    ```sh
    git clone https://github.com/andrescano11/task-manager.git
    ```
2. Navegar al directorio del proyecto:
    ```sh
    cd task-manager
    ```
3. Compilar el proyecto con Maven:
    ```sh
    mvn clean install
    ```
4. Ejecutar la aplicación:
    ```sh
    mvn spring-boot:run
    ```

## Configuración
- Asegúrese de configurar las propiedades de la base de datos en el archivo `application.yml` ubicado en `src/main/resources`.
- Una vez configurada la base de datos, ejecute la aplicación y se crearán automáticamente las tablas necesarias en la base de datos.
- Solo durante la primera vez, en el script SQL `src/main/resources/data.sql` se encuentran los datos precargados para la base de datos. Puede modificar este script según sus necesidades. 
  Para poderlo ejecutar, debe colocar `always` en la línea `spring.datasource.initialization-mode` en el archivo `application.yml`. Una vez esté ejecutado, deja esta línea nuevamente con `never`.
- También puedes ejecutar manualmente el script SQL `src/main/resources/data.sql` en tu base de datos para insertar los datos precargados.
