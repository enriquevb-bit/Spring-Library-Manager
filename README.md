> **Leer en español:** [README.es.md](README.es.md)


# Library Management System

Library management system built with Spring Boot that allows managing books, authors, members,genres and loans through a REST API, secured with OAuth2 and a custom authorization server.

## Description

This application is a complete backend system for managing a library. It supports CRUD operations (Create, Read, Update, Delete) on the main library entities: books, authors, members, genres and loans. It also includes business logic for creating and returning loans with automatic inventory management.

The project is split into two modules:

- **backend-principal**: Library REST API (OAuth2 Resource Server). Runs on port `8080`.
- **library-auth-server**: OAuth2 Authorization Server with Spring Authorization Server. Runs on port `9000`.

The project follows Spring Boot development best practices, including:
- Layered architecture using Spring MVC.
- DTOs for data transfer.
- Input data validation.
- Custom exceptions with meaningful HTTP status codes.
- Paginated results.
- Automatic entity-DTO mapping with MapStruct.
- Database migrations with Flyway.
- Security with OAuth2 (Authorization Server + Resource Server with JWT).
- Unit tests with JUnit and Mockito.
- Integration tests with Testcontainers.
- Optimistic Locking with @Version.
- API documentation with OpenAPI 3 and Swagger UI.
- Transactional business logic for loan operations.

## Technologies

| Technology | Version | Description |
|------------|---------|-------------|
| Java | 25 | Programming language. |
| Spring Boot | 4.0.2 | Main framework. |
| Spring Data JPA | - | Data persistence. |
| Spring Validation | - | Data validation. |
| Spring Security | - | Security and authentication. |
| Spring Authorization Server | - | OAuth2/OIDC authorization server. |
| Spring OAuth2 Resource Server | - | API protection with JWT. |
| springdoc-openapi | 3.0.1 | OpenAPI 3 documentation and Swagger UI. |
| MySQL | 8.0 | Main database. |
| H2 Database | - | In-memory database for development. |
| Flyway | - | Database migrations. |
| Lombok | - | Boilerplate code reduction. |
| MapStruct | 1.6.3 | Entity-DTO mapping. |
| Docker Compose | - | Container orchestration. |
| JUnit 5 | - | Testing framework. |
| Mockito | - | Unit test mocking. |
| Testcontainers | - | Integration tests with containers. |
| Maven | - | Dependency management. |

## Prerequisites

Make sure you have the following installed before running the project:

- **Java 25** or higher.
- **IntelliJ IDEA** (Community or Ultimate).
- **Docker Desktop** (for the MySQL database; includes Docker and Docker Compose).
- **Git** (to clone the repository).
- **Postman** (optional, for testing the API).
- **MySQL Workbench** (optional, for browsing the database and table relationships).

## Installation

### 1. Clone the repository

```bash
git clone https://github.com/enriquevb-bit/Spring-Library-Manager.git
cd Spring-Library-Manager
```

### 2. Open in IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Select **File > Open**.
3. Navigate to the `Spring-Library-Manager` folder and select it.
4. Wait for IntelliJ to import the Maven dependencies for both modules.

### 3. Set up the database

The project includes a `compose.yaml` file inside `backend-principal` to spin up MySQL with Docker:

```bash
cd backend-principal
docker compose up -d
```
You can also run this command from IntelliJ's built-in terminal.

This will create a MySQL container with the following configuration:
- **Database:** librarydb
- **User:** libraryAdmin
- **Password:** password
- **Port:** 3308 (mapped to container port 3306)

## Running the Application

> **Important:** You must run **both modules** for the application to work correctly. The Auth Server must be running before making requests to the API.

### 1. Start the Auth Server (`library-auth-server`)

1. Open the `LibraryAuthServerApplication.java` class (inside `library-auth-server`)
2. Click the green **Run** icon (triangle) next to the `main` method
3. Verify it starts on port **9000**

### 2. Start the Main Backend (`backend-principal`)

#### Development mode (H2 in-memory)

By default, the application uses H2 as an in-memory database:

1. Open the `BibliotecaApplication.java` class (inside `backend-principal`)
2. Click the green **Run** icon (triangle) next to the `main` method
3. Or use the shortcut `Shift + F10`

#### Local MySQL mode

To use MySQL, activate the `localmysql` profile:

1. In IntelliJ, click the name to the left of the **Run** button and select **Edit Configurations**.
2. In **Active profiles** type: `localmysql` (make sure **BibliotecaApplication** is selected on the left).
3. Click **Apply** and then **Run**

The API will be available at: `http://localhost:8080`
The Auth Server will be available at: `http://localhost:9000`

## API Documentation (OpenAPI / Swagger)

The project includes interactive API documentation automatically generated with **springdoc-openapi**.

### Runtime access

With the application running, you can access:

| Resource | URL |
|----------|-----|
| **Swagger UI** | `http://localhost:8080/swagger-ui/index.html` |
| **OpenAPI JSON** | `http://localhost:8080/v3/api-docs` |
| **OpenAPI YAML** | `http://localhost:8080/v3/api-docs.yaml` |

> **Note:** Documentation endpoints are public (no OAuth2 authentication required). All other API endpoints require a valid JWT token.

### YAML file generation with Maven

The project is configured with the `springdoc-openapi-maven-plugin` to automatically generate an `oa3.yaml` file with the OpenAPI specification during Maven's `verify` phase:

```bash
mvn verify
```

Or from IntelliJ IDEA: open the **Maven** panel (right sidebar) > **biblioteca** > **Lifecycle** > double-click **verify**.

This temporarily starts the application, downloads the specification from `/v3/api-docs.yaml` and generates the `oa3.yaml` file in the project's `target` directory.

> **Note:** For this to work, the Auth Server (`library-auth-server`) must be running on port 9000, since the application needs to connect to the OAuth2 issuer on startup.

## Postman OAuth2 Setup

All API requests require a **JWT token** issued by the Auth Server. Below is how to configure Postman to obtain it automatically.

### Option 1: Client Credentials (recommended for Postman testing)

This flow obtains a token directly without requiring a user login.

1. In Postman, create a new collection called "Biblioteca API".
2. Right-click the collection > **Edit** > **Authorization** tab.
3. Configure the following fields:

| Field | Value |
|-------|-------|
| **Auth Type** | `OAuth 2.0` |
| **Grant Type** | `Client Credentials` |
| **Access Token URL** | `http://localhost:9000/oauth2/token` |
| **Client ID** | `oidc-client` |
| **Client Secret** | `secret` |
| **Scope** | `openid` |
| **Client Authentication** | `Send as Basic Auth header` |

4. Click **Get New Access Token**.
5. Postman will automatically obtain a JWT token. Click **Use Token**.
6. All requests within the collection will inherit this token if you select **Inherit auth from parent** in their **Authorization** tab.

### Option 2: Authorization Code (interactive login flow)

This flow redirects to a login page where you enter a username and password.

1. Configure the collection's authorization with these fields:

| Field | Value |
|-------|-------|
| **Auth Type** | `OAuth 2.0` |
| **Grant Type** | `Authorization Code` |
| **Auth URL** | `http://localhost:9000/oauth2/authorize` |
| **Access Token URL** | `http://localhost:9000/oauth2/token` |
| **Client ID** | `oidc-client` |
| **Client Secret** | `secret` |
| **Callback URL** | `http://127.0.0.1:8080/login/oauth2/code/oidc-client` |
| **Scope** | `openid profile` |
| **Client Authentication** | `Send as Basic Auth header` |

2. Click **Get New Access Token**.
3. A browser window will open with the login form. Use the following credentials:

| Field | Value |
|-------|-------|
| **Username** | `user` |
| **Password** | `password` |

4. Accept the consent for the requested scopes.
5. Postman will receive the token. Click **Use Token**.

### Environment setup

Optionally, configure an environment variable under Postman's **Environments** section:

| Variable | Value |
|----------|-------|
| `base_url` | `http://localhost:8080` |

## API Endpoints

### Books

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/book` | List all books (paginated). |
| `GET` | `/api/v1/book/{id}` | Get book by ID. |
| `POST` | `/api/v1/book` | Create a new book. |
| `PUT` | `/api/v1/book/{id}` | Full update of a book. |
| `PATCH` | `/api/v1/book/{id}` | Partial update of a book. |
| `DELETE` | `/api/v1/book/{id}` | Delete a book. |

**Query parameters for listing:**
- `title` - Filter by title.
- `isbn` - Filter by ISBN.
- `pageNumber` - Page number (default: 0).
- `pageSize` - Page size (default: 25).

### Authors

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/author` | List all authors (paginated). |
| `GET` | `/api/v1/author/{id}` | Get author by ID. |
| `POST` | `/api/v1/author` | Create a new author. |
| `PUT` | `/api/v1/author/{id}` | Full update of an author. |
| `PATCH` | `/api/v1/author/{id}` | Partial update of an author. |
| `DELETE` | `/api/v1/author/{id}` | Delete an author. |

**Query parameters for listing:**
- `fullName` - Filter by full name.
- `nationality` - Filter by nationality.
- `pageNumber` - Page number.
- `pageSize` - Page size.

### Genres

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/genre` | List all genres (paginated). |
| `GET` | `/api/v1/genre/{id}` | Get genre by ID. |
| `POST` | `/api/v1/genre` | Create a new genre. |
| `PUT` | `/api/v1/genre/{id}` | Full update of a genre. |
| `PATCH` | `/api/v1/genre/{id}` | Partial update of a genre. |
| `DELETE` | `/api/v1/genre/{id}` | Delete a genre. |

**Query parameters for listing:**
- `name` - Filter by name.
- `pageNumber` - Page number.
- `pageSize` - Page size.

### Members

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/member` | List all members (paginated). |
| `GET` | `/api/v1/member/{id}` | Get member by ID. |
| `POST` | `/api/v1/member` | Create a new member. |
| `PUT` | `/api/v1/member/{id}` | Full update of a member. |
| `PATCH` | `/api/v1/member/{id}` | Partial update of a member. |
| `DELETE` | `/api/v1/member/{id}` | Delete a member. |

**Query parameters for listing:**
- `name` - Filter by name.
- `email` - Filter by email.
- `pageNumber` - Page number.
- `pageSize` - Page size.

### Loans

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/loan` | List all loans (paginated). |
| `GET` | `/api/v1/loan/{id}` | Get loan by ID. |
| `POST` | `/api/v1/loan` | Create a new loan (basic CRUD). |
| `PUT` | `/api/v1/loan/{id}` | Full update of a loan. |
| `PATCH` | `/api/v1/loan/{id}` | Partial update of a loan. |
| `DELETE` | `/api/v1/loan/{id}` | Delete a loan. |

**Query parameters for listing:**
- `loanState` - Filter by loan state (`ACTIVE`, `RETURNED`, `OVERDUE`, `CANCELLED`).
- `pageNumber` - Page number.
- `pageSize` - Page size.

### Loan Business Logic

These endpoints implement the core loan management business rules.

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/member/{memberId}/loan` | Create a loan for a member with inventory validation. |
| `PATCH` | `/api/v1/loan/{loanId}/return` | Return a loan and restore book copies. |

#### Create Loan (`POST /api/v1/member/{memberId}/loan`)

Creates a new loan for a specific member. This is a transactional operation that:

1. Validates the member exists and is in `ACTIVE` state.
2. Validates all requested books exist.
3. Validates there are enough available copies for each book.
4. Decrements `availableCopies` for each borrowed book.
5. Creates the loan with state `ACTIVE`, `loanDate` set to now and `expiringDate` set to 14 days from now.

**Request body:**
```json
[
    {
        "bookId": "uuid-of-book-1",
        "quantity": 1
    },
    {
        "bookId": "uuid-of-book-2",
        "quantity": 2
    }
]
```

**Responses:**
- `201 Created` - Loan created successfully (returns loan with Location header).
- `400 Bad Request` - Member is not active (`MemberNotActiveException`).
- `400 Bad Request` - Not enough copies available (`NotEnoughCopiesException`).
- `404 Not Found` - Member or book not found (`NotFoundException`).

#### Return Loan (`PATCH /api/v1/loan/{loanId}/return`)

Returns a loan and restores book inventory. This is a transactional operation that:

1. Validates the loan exists.
2. Validates the loan is in `ACTIVE` state.
3. Increments `availableCopies` for each book in the loan.
4. Sets loan state to `RETURNED` and `dueDate` to the current timestamp.

**Responses:**
- `204 No Content` - Loan returned successfully.
- `400 Bad Request` - Loan was already returned (`LoanAlreadyReturnedException`).
- `404 Not Found` - Loan not found (`NotFoundException`).

## Custom Exceptions

The application uses custom exceptions with Spring's `@ResponseStatus` for meaningful error responses:

| Exception | HTTP Status | Description |
|-----------|-------------|-------------|
| `NotFoundException` | `404 Not Found` | Requested resource does not exist. |
| `NotEnoughCopiesException` | `400 Bad Request` | Not enough available copies of a book to fulfill the loan. |
| `MemberNotActiveException` | `400 Bad Request` | Member is not in `ACTIVE` state and cannot borrow books. |
| `LoanAlreadyReturnedException` | `400 Bad Request` | Loan has already been returned and cannot be returned again. |


## Postman Usage Examples

> **Remember:** All requests require a valid OAuth2 token. Set up authorization at the collection level as described in the [Postman OAuth2 Setup](#postman-oauth2-setup) section and make sure each request has **Inherit auth from parent** selected in its Authorization tab.

### Get all books

| Setting | Value |
|---------|-------|
| **Method** | `GET` |
| **URL** | `{{base_url}}/api/v1/book` |

### Create a book

| Setting | Value |
|---------|-------|
| **Method** | `POST` |
| **URL** | `{{base_url}}/api/v1/book` |

**Body:**
1. Select the **Body** tab
2. Choose **raw**
3. Select **JSON** from the dropdown
4. Enter the JSON:
```json
{
    "isbn": "978-0134685991",
    "title": "Effective Java",
    "availableCopies": 3,
    "price": 45.99,
    "publishedDate": "2018-01-06"
}
```

### Search books by title

| Setting | Value |
|---------|-------|
| **Method** | `GET` |
| **URL** | `{{base_url}}/api/v1/book` |
| **Params** | `title=Java`, `pageSize=10` |


### Update a book (PUT)

| Setting | Value |
|---------|-------|
| **Method** | `PUT` |
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

### Delete a book

| Setting | Value |
|---------|-------|
| **Method** | `DELETE` |
| **URL** | `{{base_url}}/api/v1/book/{id}` |

### Create a member

| Setting | Value |
|---------|-------|
| **Method** | `POST` |
| **URL** | `{{base_url}}/api/v1/member` |

**Body (raw JSON):**
```json
{
    "name": "Ana Martinez",
    "email": "ana.martinez@email.com",
    "memberState": "ACTIVE"
}
```

### Create a loan for a member (business logic)

| Setting | Value |
|---------|-------|
| **Method** | `POST` |
| **URL** | `{{base_url}}/api/v1/member/{memberId}/loan` |

**Body (raw JSON):**
```json
[
    {
        "bookId": "uuid-of-book",
        "quantity": 1
    }
]
```

> Replace `{memberId}` with the UUID of an `ACTIVE` member and `uuid-of-book` with a valid book UUID.

### Return a loan

| Setting | Value |
|---------|-------|
| **Method** | `PATCH` |
| **URL** | `{{base_url}}/api/v1/loan/{loanId}/return` |

> Replace `{loanId}` with the UUID of an `ACTIVE` loan. No request body required.

## Data Model

### Browsing the database with MySQL Workbench

To view data directly in MySQL:

1. Open **MySQL Workbench**
2. Create a new connection with the **+** icon next to "MySQL Connections"
3. Set the connection details:

| Field | Value |
|-------|-------|
| Connection Name | Biblioteca (or any name you prefer) |
| Hostname | `127.0.0.1` |
| Port | `3308` |
| Username | `libraryAdmin` |
| Password | `password` (click "Store in Vault") |

4. Click **Test Connection** to verify
5. Click **OK** to save
6. Double-click the connection to open it
7. In the left panel you will see the `librarydb` database with all tables

### Main Entities

#### Book
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Unique identifier |
| isbn | String | ISBN code (unique) |
| title | String | Book title |
| price | BigDecimal | Price |
| availableCopies | Integer | Available copies |
| publishedDate | LocalDate | Publication date |
| authors | Set\<Author\> | Book authors |
| genres | Set\<Genre\> | Book genres |

#### Author
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Unique identifier |
| fullName | String | Full name |
| nationality | String | Nationality |
| birthDate | LocalDate | Date of birth |
| books | Set\<Book\> | Author's books |

#### Genre
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Unique identifier |
| name | String | Genre name |
| description | String | Genre description |
| books | Set\<Book\> | Books in this genre (many-to-many) |

#### Member
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Unique identifier |
| name | String | Name |
| email | String | Email address |
| memberState | MemberState | Member status |
| registerDate | LocalDateTime | Registration date |
| loans | Set\<Loan\> | Member's loans |

#### Loan
| Field | Type | Description |
|-------|------|-------------|
| id | UUID | Unique identifier |
| loanState | LoanState | Loan status |
| loanDate | LocalDateTime | Date the loan was created |
| expiringDate | LocalDateTime | Date the loan expires |
| dueDate | LocalDateTime | Date the loan was returned |
| member | Member | Member who borrowed (many-to-one) |
| loanLines | Set\<LoanLine\> | Items in this loan (one-to-many, cascade delete) |

### Member States (MemberState)
- `PENDING` - Registration pending verification
- `ACTIVE` - Active member with full access
- `SUSPENDED` - Suspended due to overdue books or fines
- `BLOCKED` - Blocked due to policy violations
- `INACTIVE` - Inactive or cancelled account

### Loan States (LoanState)
- `ACTIVE` - Active loan
- `RETURNED` - Returned
- `OVERDUE` - Overdue
- `CANCELLED` - Cancelled

## Running Tests

### From IntelliJ IDEA

1. **Single test**: Right-click on a test class > **Run**
2. **All tests**: Right-click on the `test` folder > **Run 'All Tests'**
3. Or use the shortcut `Ctrl + Shift + F10` on a test file

### Test types

| Type | Files | Database |
|------|-------|----------|
| Unit | `*ControllerTest.java` | Mocks (Mockito) |
| Repository | `*RepositoryTest.java` | H2 in-memory |
| Integration | `*ControllerIT.java` | H2 in-memory |
| MySQL Integration | `MySqlIT.java` | MySQL (Testcontainers) |

**Note:** To run `MySqlIT.java` you need Docker running, as it uses Testcontainers to spin up MySQL.

## Configuration

### application.properties (H2 development) - backend-principal

```properties
spring.application.name=biblioteca
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9000
spring.profiles.active=default
spring.jpa.hibernate.ddl-auto=create-drop
logging.level.enriquevb.biblioteca=debug
spring.flyway.enabled=false
spring.docker.compose.enabled=false
spring.jpa.show-sql: true
```

### application-localmysql.properties (MySQL) - backend-principal

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3308/librarydb
spring.datasource.username=libraryAdmin
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.flyway.enabled=false
spring.docker.compose.enabled=true
```

### application.properties - library-auth-server

```properties
spring.application.name=library-auth-server
server.port=9000
```



## Author

- **enriquevb** - [GitHub](https://github.com/enriquevb-bit)

---

Built with Spring Boot.
