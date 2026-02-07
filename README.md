# Sistema de Gestion de Biblioteca

Sistema de gestion de biblioteca desarrollado con Spring Boot que permite administrar libros, autores, miembros y préstamos a través de una API REST, protegida con OAuth2 y un servidor de autorización propio.

## Descripción

Esta aplicación es un sistema backend completo para la gestión de una biblioteca. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre las principales entidades de una biblioteca: libros, autores, miembros y préstamos.

El proyecto está dividido en dos módulos:

- **backend-principal**: API REST de la biblioteca (Resource Server OAuth2). Se ejecuta en el puerto `8080`.
- **library-auth-server**: Servidor de Autorización OAuth2 con Spring Authorization Server. Se ejecuta en el puerto `9000`.

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
- Seguridad con OAuth2 (Authorization Server + Resource Server con JWT).
- Documentación de la API con OpenAPI 3 y Swagger UI.

## Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| Java | 25 | Lenguaje de programación. |
| Spring Boot | 4.0.2 | Framework principal. |
| Spring Data JPA | - | Persistencia de datos. |
| Spring Validation | - | Validación de datos. |
| Spring Security | - | Seguridad y autenticación. |
| Spring Authorization Server | - | Servidor de autorización OAuth2/OIDC. |
| Spring OAuth2 Resource Server | - | Protección de la API con JWT. |
| springdoc-openapi | 3.0.1 | Documentación OpenAPI 3 y Swagger UI. |
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
3. Navega hasta la carpeta `Spring-Library-Manager` y seleccionala.
4. Espera a que IntelliJ importe las dependencias de Maven de ambos módulos.


### 3. Configurar la base de datos

El proyecto incluye un archivo `compose.yaml` dentro de `backend-principal` para levantar MySQL con Docker:
Ejecuta este comando en la terminal de IntelliJ del proyecto:
```bash
docker compose up -d
```


Esto creará un contenedor MySQL con la siguiente configuración:
- **Base de datos:** librarydb
- **Usuario:** libraryAdmin
- **Password:** password
- **Puerto:** 3308 (mapeado al 3306 del contenedor)

## Ejecución

> **Importante:** Debes ejecutar **ambos módulos** para que la aplicación funcione correctamente. El Auth Server debe estar corriendo antes de hacer peticiones a la API.

### 1. Iniciar el Auth Server (`library-auth-server`)

1. Abre la clase `LibraryAuthServerApplication.java` (dentro de `library-auth-server`)
2. Haz clic en el icono verde **Run** (triangulo) junto al metodo `main`
3. Verifica que arranca en el puerto **9000**

### 2. Iniciar el Backend Principal (`backend-principal`)

#### Modo desarrollo (H2 en memoria)

Por defecto, la aplicación usa H2 como base de datos en memoria:

1. Abre la clase `BibliotecaApplication.java` (dentro de `backend-principal`)
2. Haz clic en el icono verde **Run** (triangulo) junto al método `main`
3. O usa el atajo `Shift + F10`

### Modo con MySQL local

Para usar MySQL, activa el perfil `localmysql`:

1. En IntelliJ, haz clic en el nombre a la izquierda del botón **Run** y dale a **Edit Configurations**.
2. En **Active profiles** escribe: `localmysql` (asegúrate de haber seleccionado **BibliotecaApplication** en el selector de la izquierda).
3. Haz clic en **Apply** y luego **Run**

La aplicacion estará disponible en: `http://localhost:8080`
El Auth Server estará disponible en: `http://localhost:9000`

## Configuración de Postman con OAuth2

Todas las peticiones a la API requieren un **token JWT** emitido por el Auth Server. A continuación se explica cómo configurar Postman para obtenerlo automáticamente.

### Client Credentials

Este flujo obtiene un token directamente sin necesidad de iniciar sesión con usuario y contraseña.

1. En Postman, crea una nueva colección llamada "Biblioteca API".
2. Haz clic derecho en la colección > **Edit** > pestaña **Authorization**.
3. Configura los siguientes campos:

| Campo | Valor |
|-------|-------|
| **Auth Type** | `OAuth 2.0` |
| **Grant Type** | `Client Credentials` |
| **Access Token URL** | `http://localhost:9000/oauth2/token` |
| **Client ID** | `oidc-client` |
| **Client Secret** | `secret` |
| **Scope** | `openid` |
| **Client Authentication** | `Send as Basic Auth header` |

4. Haz clic en **Get New Access Token**.
5. Postman obtendrá un token JWT automáticamente. Haz clic en **Use Token**.
6. Todas las peticiones dentro de la colección heredarán este token si en su pestaña **Authorization** seleccionas **Inherit auth from parent**.

### Configuración del entorno

Opcionalmente, configura una variable de entorno en el apartado **Environments** de Postman:

| Variable | Valor |
|----------|-------|
| `base_url` | `http://localhost:8080` |


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

## Documentación de la API (OpenAPI / Swagger)

El proyecto incluye documentación interactiva de la API generada automáticamente con **springdoc-openapi**.

### Acceso en tiempo de ejecución

Con la aplicación arrancada, puedes acceder a:

| Recurso | URL |
|---------|-----|
| **Swagger UI** | `http://localhost:8080/swagger-ui/index.html` |
| **OpenAPI JSON** | `http://localhost:8080/v3/api-docs` |
| **OpenAPI YAML** | `http://localhost:8080/v3/api-docs.yaml` |

> **Nota:** Los endpoints de documentación son públicos (no requieren autenticación OAuth2). El resto de la API sí requiere un token JWT válido.

### Generación del archivo YAML con Maven

El proyecto está configurado con el `springdoc-openapi-maven-plugin` para generar un archivo `oa3.yaml` con la especificación OpenAPI de forma automática durante la fase `verify` de Maven:

```bash
mvn verify
```

O desde IntelliJ IDEA: abre el panel **Maven** (barra lateral derecha) > **biblioteca** > **Lifecycle** > doble clic en **verify**.

Esto arranca la aplicación temporalmente, descarga la especificación desde `/v3/api-docs.yaml` y genera el archivo `oa3.yaml` en el directorio `target` del proyecto.

> **Nota:** Para que la generación funcione, el Auth Server (`library-auth-server`) debe estar corriendo en el puerto 9000, ya que la aplicación necesita conectar con el issuer OAuth2 al arrancar.


## Ejemplos de Uso con Postman

> **Recuerda:** Todas las peticiones requieren un token OAuth2 válido. Configura la autorización a nivel de colección como se explica en la sección [Configuración de Postman con OAuth2](#configuración-de-postman-con-oauth2) y asegúrate de que cada petición tenga **Inherit auth from parent** en su pestaña Authorization.

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
