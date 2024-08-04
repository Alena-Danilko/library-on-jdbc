# Library Management System
The project is a web application for managing library operations. It allows librarians to register readers, books assign and release.

## Features
- Reader Registration: Librarians can register new readers into the system.
- Book Lending: Lend books to registered readers, keeping track of due dates and current holdings.
- Book Release: Manage the return of books, updating the system accordingly.

## Technologies
- Java 8+
- Maven
- PostgreSQL
- Spring MVC, JDBC
- Thymeleaf
- Hibernate
- JUnit 5

## Installation
1. Clone the repository: git clone https://github.com/Alena-Danilko/library-on-jdbc.git
2. Navigate to the project directory: cd library-on-jdbc
3. Configure the database connection in spring-library/src/main/resources/database.properties
url=jdbc:postgresql://localhost:5432/your_database_name
user=your_username
password=your_password
4. Build the project: mvn clean install

## Usage
1. Run the application: mvn spring-boot:run
2. The API will be available at http://localhost:8080
## Endpoints
### Books
GET /books - Get all books from the database.

GET /books/{id} - Get details of a specific book.

GET /books/new - Display form to add a new book.

POST /books - Create a new book.

GET /books/{id}/edit - Display form to edit a book.

PATCH /books/{id} - Update a book.

DELETE /books/{id} - Delete a book.

PATCH /books/{id}/release - Pemove owner book.

PATCH /books/{id}/assign - Assign a book to a person.

### Reader
GET /people - Get all readers from the database.

GET /people/{id} - Get data about a specific reader and a list of his books.

GET /people/new - Display form to register a new reader.

POST /people - Register a new reader.

GET /people/{id}/edit - Display form to edit a reader.

PATCH /people/{id} - Update information about reader.

DELETE /people/{id} - Delete a reader.

## Project structure
### Main code
src/main/java/com/example - Application source code.

src/main/java/com/example/config - Classes for configuration application.

src/main/java/com/example/controller - Controller classes for for handling web requests.

src/main/java/com/example/dao - Data Access Objects.

src/main/java/com/example/models - Data Models.

src/main/java/com/example/util - Helper classes for validation.

src/main/resources/database.properties - Configuration file.

### Testing
src/main/test/com/example/springLibraryTest/ - Tests for the application source code.

src/main/java/com/example/springLibraryTest/controllerTest - Tests for controller classes.

src/main/java/com/example/springLibraryTest/daoTest - Test for DAO (Data Access Object).

src/main/java/com/example/springLibraryTest/modelsTest - Tests for Data Models.

src/main/java/com/example/springLibraryTest/utilTest - Tests for helper classes for validation.
