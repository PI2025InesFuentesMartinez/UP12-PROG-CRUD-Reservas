# Proyecto CRUD Intermodular

**Autora:** Inés Fuentes Martínez

## Descripción General

Este proyecto consiste en la implementación completa de un sistema CRUD (Crear, Leer, Actualizar, Borrar) para todas las entidades de la base de datos de un proyecto intermodular. La aplicación permite gestionar los datos a través de una interfaz de línea de comandos (CLI), interactuando directamente con una base de datos relacional mediante JDBC.

## Arquitectura

El proyecto sigue una arquitectura basada en el patrón **Modelo-Vista-Controlador (MVC)**, complementada con el patrón **Data Access Object (DAO)** para abstraer y encapsular de manera limpia todos los accesos a la base de datos.

*   **Modelo (`model`):** Representa la estructura de datos, las entidades y la lógica de negocio básica.
*   **Vista (`view`):** Maneja la interfaz de usuario por consola (menús de navegación y presentación de datos).
*   **Controlador (`controller`):** Coordina la interacción entre las Vistas y los DAOs, procesando las entradas del usuario y delegando las operaciones correspondientes.
*   **DAO (`dao`):** Gestiona las operaciones CRUD directamente contra la base de datos.

## Estructura del Proyecto

La estructura de paquetes se basa en la recomendación de "Estructura de un proyecto 2 con JDBC":

*   `app`: Contiene la clase `Main`, que sirve como punto de entrada de la aplicación.
*   `model`: Clases de datos (POJOs) que representan las tablas de la BD.
*   `dao`: Clases responsables de ejecutar las consultas SQL.
*   `view`: Interfaces de usuario por terminal.
*   `controller`: Clases que enlazan y coordinan las vistas con los DAOs.

### Flujo de Ejecución Base
1. La clase `Main` instancia el `AppController`.
2. `AppController` muestra el menú principal al usuario.
3. Según la opción seleccionada, el flujo se delega al controlador específico de la entidad (ej. `UsuarioController`, `ReservaController`).
4. El controlador específico muestra su submenú mediante su vista, interactúa con el DAO para persistir o recuperar información, y devuelve los resultados a la pantalla.

## Decisiones de Diseño e Implementación

A continuación, se detallan las decisiones técnicas más relevantes tomadas durante el desarrollo:

### 1. Modelos y Herencia (`Usuario`, `Administrador`, `UsuarioNormal`)
*   **Estructura Base:** Cada modelo incluye sus atributos encapsulados, múltiples constructores, getters, setters y la sobreescritura del método `toString()` (fundamental para la depuración y para mostrar la información legible en consola).
*   **Gestión de Constructores:**
    *   **Constructor Vacío:** Esencial para resolver problemas de inicialización al aplicar herencia. En Java, al usar `super()` en una clase hija, se invoca por defecto al constructor sin parámetros del padre.
    *   **Constructor sin ID:** Utilizado para la creación de nuevos registros, dejando que la base de datos asigne el ID automáticamente.
    *   **Constructor con ID:** Utilizado para instanciar objetos a partir de registros leídos de la base de datos (encadenando constructores para reutilizar código).
*   **Herencia de Usuarios:** Las clases `Administrador` y `UsuarioNormal` heredan de la clase base `Usuario`. Mediante el uso de constantes de tipos de usuario definidas en la clase padre y la llamada a `super()`, se asigna automáticamente el rol correcto al instanciar los objetos.

### 2. Acceso a Datos (DAOs) y Conexión
*   **Patrón Singleton en `BDConnection`:** Se implementó este patrón para garantizar que exista una única conexión activa a la base de datos en toda la aplicación, centralizando las credenciales de acceso y los métodos de conexión/desconexión.
*   **Estructura Unificada de DAOs:**
    *   Las sentencias SQL (`SELECT`, `INSERT`, `UPDATE`, `DELETE`) están centralizadas como constantes (`public static final String`) al inicio de cada clase para facilitar su lectura y mantenimiento.
    *   Se implementan los cuatro métodos del CRUD además de una lectura global (`readAll`).
    *   **Método de Mapeo Interno:** Para evitar código repetitivo al leer desde la base de datos, se extrajo la lógica de conversión en un método privado que toma una fila del `ResultSet` y la mapea a un objeto del modelo.

### 3. Vistas y Controladores
*   **Vistas:** Definen cómo se presenta la información. Separando la lógica de presentación de la lógica de negocio y control.
*   **Controladores:** Actúan como intermediarios. Implementan estructuras `switch` robustas para gestionar las opciones del usuario, recolectar datos desde la consola (Vista) e inyectarlos en los métodos correspondientes (DAO).

## Base de Datos
El proyecto incluye un script SQL de inicialización con el modelo Entidad-Relación predefinido. Es importante notar que, debido a las pruebas intensivas de las operaciones CRUD (inserciones, modificaciones y borrados), los datos actuales en ejecución pueden diferir de los registros iniciales del script.

## Retos Superados y Aprendizajes

El desarrollo de este CRUD ha sido un ejercicio exhaustivo que me ha permitido asentar profundamente conceptos clave de programación:

1.  **Manejo Profundo de DAOs y JDBC:** El mayor desafío técnico no fue redactar las sentencias SQL, sino comprender cómo interactúa Java con la base de datos a nivel interno: la gestión de parámetros en `PreparedStatement`, el manejo de identificadores generados por la base de datos y la iteración correcta sobre los `ResultSet`.
2.  **Sincronización en los Controladores:** La cantidad de lógica requerida para asegurar que los datos fluyeran sin errores desde la vista hacia el DAO (y viceversa) supuso un volumen de trabajo considerable y requirió una depuración atenta para evitar errores de estado.

**Conclusión:**
Aunque el desarrollo moderno de software ofrece herramientas y frameworks potentes (como **Spring**) que abstraen y simplifican enormemente la creación de un CRUD, la construcción de esta arquitectura desde sus cimientos ha sido vital. Me ha proporcionado una comprensión sólida de "qué ocurre por debajo", un conocimiento esencial para cualquier desarrollador.
