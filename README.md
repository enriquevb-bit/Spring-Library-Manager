# Sistema de Gestion de Biblioteca

Sistema de gestion de biblioteca desarrollado con Spring Boot que permite administrar libros, autores, miembros y préstamos a través de una API REST.

## Descripción

Esta aplicación es un sistema backend completo para la gestión de una biblioteca. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre las principales entidades de una biblioteca: libros, autores, miembros y préstamos.

El proyecto sigue las mejores prácticas de desarrollo con Spring Boot, incluyendo:
- Arquitectura en capas, usando el MVC de Spring.
- Uso de DTOs para transferencia de datos.
- Validación de datos de entrada.
- Paginación de resultados.
- Mapeo automático entre entidades y DTOs con MapStruct.
- Migraciones de base de datos con Flyway.
- Tests unitarios con JUnit y Mockito.
- Tests integración con Testcontainers.
- Optimistic Locking con @Version.

## Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| Java | 25 | Lenguaje de programación. |
| Spring Boot | 4.0.2 | Framework principal. |
| Spring Data JPA | - | Persistencia de datos. |
| Spring Validation | - | Validación de datos. |
| MySQL | 8.0 | Base de datos principal. |
| H2 Database | - | Base de datos en memoria para desarrollo. |
| Flyway | - | Migraciones de base de datos. |
| Lombok | - | Reducción de código boilerplate. |
| MapStruct | 1.6.3 | Mapeo entre entidades y DTOs. |
| Docker Compose | - | Orquestación de contenedores. |
| JUnit 5 | - | Framework de testing |
| Mockito | - | Mocking en tests unitarios |
| Testcontainers | - | Tests de integración con contenedores. |
| Maven | - | Gestión de dependencias. |

## Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 25** o superior.
- **IntelliJ IDEA** (Community o Ultimate).
- **Docker Desktop** (para la base de datos MySQL, incluye Docker y Docker Compose)
- **Git** (para clonar el repositorio).
- **Postman** (opcional, para probar la API).
- **MySQL Workbench** (opcional, para ver la base de datos y las relaciones entre tablas).

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/enriquevb-bit/Spring-Library-Manager.git
cd Spring-Library-Manager
```


### 2. Abrir en IntelliJ IDEA

1. Abre IntelliJ IDEA.
2. Selecciona **File > Open**.
3. Navega hasta la carpeta `biblioteca` y seleccionala.
4. Espera a que IntelliJ importe las dependencias de Maven.


### 3. Configurar la base de datos

El proyecto incluye un archivo `compose.yaml` para levantar MySQL con Docker:

```bash
docker compose up -d
```
También puedes ejecutar este comando en la terminal de IntelliJ.

Esto creará un contenedor MySQL con la siguiente configuración:
- **Base de datos:** librarydb
- **Usuario:** libraryAdmin
- **Password:** password
- **Puerto:** 3308 (mapeado al 3306 del contenedor)

## Ejecución

### Modo desarrollo (H2 en memoria)

Por defecto, la aplicación usa H2 como base de datos en memoria:

1. Abre la clase `BibliotecaApplication.java`
2. Haz clic en el icono verde **Run** (triangulo) junto al metodo `main`
3. O usa el atajo `Shift + F10`

### Modo con MySQL local

Para usar MySQL, activa el perfil `localmysql`:

1. En IntelliJ, haz clic en el nombre a la izquierda del botón **Run** y dale a **Edit Configurations**.
2. En **Active profiles** escribe: `localmysql` (asegúrate de haber seleccionado **BibliotecaApplication** en el selector de la izquierda).
3. Haz clic en **Apply** y luego **Run**

La aplicacion estará disponible en: `http://localhost:8080`

## API Endpoints

### Libros (Books)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/v1/book` | Listar todos los libros (paginado). |
| `GET` | `/api/v1/book/{id}` | Obtener libro por ID. |
| `POST` | `/api/v1/book` | Crear nuevo libro. |
| `PUT` | `/api/v1/book/{id}` | Actualizar libro completo. |
| `PATCH` | `/api/v1/book/{id}` | Actualizar libro parcialmente. |
| `DELETE` | `/api/v1/book/{id}` | Eliminar libro. |

**Parámetros de consulta para listar:**
- `title` - Filtrar por título.
- `isbn` - Filtrar por ISBN.
- `pageNumber` - Número de pagina (default: 0).
- `pageSize` - Tamaño de pagina (default: 25).

### Autores (Authors)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/v1/author` | Listar todos los autores (paginado). |
| `GET` | `/api/v1/author/{id}` | Obtener autor por ID. |
| `POST` | `/api/v1/author` | Crear nuevo autor. |
| `PUT` | `/api/v1/author/{id}` | Actualizar autor completo. |
| `PATCH` | `/api/v1/author/{id}` | Actualizar autor parcialmente. |
| `DELETE` | `/api/v1/author/{id}` | Eliminar autor. |

**Parámetros de consulta para listar:**
- `fullName` - Filtrar por nombre completo.
- `nationality` - Filtrar por nacionalidad.
- `pageNumber` - Número de pagina.
- `pageSize` - Tamaño de página.

### Miembros (Members)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/v1/member` | Listar todos los miembros (paginado). |
| `GET` | `/api/v1/member/{id}` | Obtener miembro por ID. |
| `POST` | `/api/v1/member` | Crear nuevo miembro. |
| `PUT` | `/api/v1/member/{id}` | Actualizar miembro completo. |
| `PATCH` | `/api/v1/member/{id}` | Actualizar miembro parcialmente. |
| `DELETE` | `/api/v1/member/{id}` | Eliminar miembro. |

**Parámetros de consulta para listar:**
- `name` - Filtrar por nombre.
- `email` - Filtrar por email.
- `pageNumber` - Número de página.
- `pageSize` - Tamaño de página.

## Ejemplos de Uso con Postman

### Configuración inicial de Postman

1. Abre Postman y crea una nueva colección llamada "Biblioteca API"
2. Configura una variable de entorno en el apartado **Environments** `base_url` con valor `http://localhost:8080`

### Obtener todos los libros

| Configuración | Valor |
|---------------|-------|
| **Método** | `GET` |
| **URL** | `{{base_url}}/api/v1/book` |

### Crear un libro

| Configuración | Valor |
|---------------|-------|
| **Método** | `POST` |
| **URL** | `{{base_url}}/api/v1/book` |

**Body:**
1. Selecciona la pestaña **Body**
2. Marca la opción **raw**
3. En el desplegable selecciona **JSON**
4. Escribe el JSON:
```json
{
    "isbn": "978-0134685991",
    "title": "Effective Java",
    "availableCopies": 3,
    "price": 45.99,
    "publishedDate": "2018-01-06"
}
```

### Buscar libros por título

| Configuración | Valor |
|---------------|-------|
| **Método** | `GET` |
| **URL** | `{{base_url}}/api/v1/book` |
| **Params** | `title=Java`, `pageSize=10` |

### Crear un autor

| Configuración | Valor |
|---------------|-------|
| **Método** | `POST` |
| **URL** | `{{base_url}}/api/v1/author` |

**Body (raw JSON):**
```json
{
    "fullName": "Joshua Bloch",
    "nationality": "American",
    "birthDate": "1961-08-28"
}
```

### Crear un miembro

| Configuración | Valor |
|---------------|-------|
| **Método** | `POST` |
| **URL** | `{{base_url}}/api/v1/member` |

**Body (raw JSON):**
```json
{
    "name": "Ana Martinez",
    "email": "ana.martinez@email.com",
    "memberState": "ACTIVE"
}
```

### Actualizar un libro (PUT)

| Configuración | Valor |
|---------------|-------|
| **Método** | `PUT` |
| **URL** | `{{base_url}}/api/v1/book/{id}` |

**Body (raw JSON):**
```json
{
    "isbn": "978-0134685991",
    "title": "Effective Java - 3rd Edition",
    "availableCopies": 5,
    "price": 49.99,
    "publishedDate": "2018-01-06"
}
```

### Eliminar un libro

| Configuración | Valor |
|---------------|-------|
| **Método** | `DELETE` |
| **URL** | `{{base_url}}/api/v1/book/{id}` |

## Modelo de Datos

### Ver la base de datos con MySQL Workbench

Si quieres visualizar los datos directamente en MySQL:

1. Abre **MySQL Workbench**
2. Crea una nueva conexión con el icono **+** junto a "MySQL Connections"
3. Configura los datos de conexión:

| Campo | Valor |
|-------|-------|
| Connection Name | Biblioteca (o el nombre que quieras) |
| Hostname | `127.0.0.1` |
| Port | `3308` |
| Username | `libraryAdmin` |
| Password | `password` (clic en "Store in Vault") |

4. Haz clic en **Test Connection** para verificar
5. Haz clic en **OK** para guardar
6. Doble clic en la conexion para abrirla
7. En el panel izquierdo verás la base de datos `librarydb` con todas las tablas

### Entidades Principales

#### Book (Libro)
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador unico |
| isbn | String | Codigo ISBN (unico) |
| title | String | Titulo del libro |
| price | BigDecimal | Precio |
| availableCopies | Integer | Copias disponibles |
| publishedDate | LocalDate | Fecha de publicacion |
| authors | Set<Author> | Autores del libro |
| genres | Set<Genre> | Generos del libro |

#### Author (Autor)
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador unico |
| fullName | String | Nombre completo |
| nationality | String | Nacionalidad |
| birthDate | LocalDate | Fecha de nacimiento |
| books | Set<Book> | Libros del autor |

#### Member (Miembro)
| Campo | Tipo | Descripcion |
|-------|------|-------------|
| id | UUID | Identificador unico |
| name | String | Nombre |
| email | String | Correo electronico |
| memberState | MemberState | Estado del miembro |
| registerDate | LocalDateTime | Fecha de registro |
| loans | Set<Loan> | Prestamos del miembro |

### Estados de Miembro (MemberState)
- `PENDING` - Registro pendiente de verificacion
- `ACTIVE` - Miembro activo con acceso completo
- `SUSPENDED` - Suspendido por libros vencidos o multas
- `BLOCKED` - Bloqueado por violaciones de politica
- `INACTIVE` - Cuenta inactiva o cancelada

### Estados de Préstamo (LoanState)
- `ACTIVE` - Prestamo activo
- `RETURNED` - Devuelto
- `OVERDUE` - Vencido
- `CANCELLED` - Cancelado

## Ejecución de Tests

### Desde IntelliJ IDEA

1. **Test individual**: Clic derecho sobre una clase de test > **Run**
2. **Todos los tests**: Clic derecho sobre la carpeta `test` > **Run 'All Tests'**
3. O usa el atajo `Ctrl + Shift + F10` sobre un archivo de test

### Tipos de tests

| Tipo | Archivos | Base de datos |
|------|----------|---------------|
| Unitarios | `*ControllerTest.java` | Mocks (Mockito) |
| Repositorios | `*RepositoryTest.java` | H2 en memoria |
| Integracion | `*ControllerIT.java` | H2 en memoria |
| Integracion MySQL | `MySqlIT.java` | MySQL (Testcontainers) |

**Nota:** Para ejecutar `MySqlIT.java` necesitas tener Docker corriendo, ya que usa Testcontainers para levantar MySQL.

## Configuración

### application.properties (Desarrollo con H2)

```properties
spring.application.name=biblioteca
spring.profiles.active=default
spring.jpa.hibernate.ddl-auto=create-drop
spring.flyway.enabled=false
spring.docker.compose.enabled=false
```

### application-localmysql.properties (MySQL)

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3308/librarydb
spring.datasource.username=libraryAdmin
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.flyway.enabled=false
spring.docker.compose.enabled=true
```

## Estructura del Proyecto

```
biblioteca/
├── src/
│   ├── main/
│   │   ├── java/enriquevb/biblioteca/
│   │   │   ├── BibliotecaApplication.java
│   │   │   ├── bootstrap/
│   │   │   ├── controllers/
│   │   │   ├── entities/
│   │   │   ├── models/
│   │   │   ├── mappers/
│   │   │   ├── repositories/
│   │   │   └── services/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-localmysql.properties
│   │       └── db/migration/
│   └── test/
├── compose.yaml
├── pom.xml
└── README.md
```

## Autor

- **enriquevb** - [GitHub](https://github.com/enriquevb-bit)

---

Desarrollado con Spring Boot.
