# Spring Boot GraphQL API for Employee & Department Management  

## 📖 Overview  

A robust Spring Boot application that exposes **Employee and Department** data via a powerful **GraphQL API** to handle CRUD operations for **Employee** and **Department** entities. 

This project implements `One-To-Many` relationships between **Employee** as the parent entity and the child entities **DepartmentEmployee, SalaryEmployee, and TitleEmployee**. The relationship is managed using Spring Data JPA with Hibernate, and the project utilizes `EmbeddedId` for `composite primary keys` in the relationship tables.  

### 🔗 Relationships  

The following is the relationship between tables:  

- Department ↔ DepartmentEmployee (`One-to-Many`)
- Employee ↔ DepartmentEmployee (`One-to-Many`)
- Employee ↔ SalaryEmployee (`One-to-Many`)
- Employee ↔ TitleEmployee (`One-to-Many`)

### 🔢 EmbeddedId  

These tables are managed using `EmbeddedId` to define composite primary keys:  

- department_employee (employee_id, department_id)
- salary (employee_id, from_date)
- title (employee_id, title, from_date)


All API access is securely protected using an **API Key** passed via HTTP headers. This ensures that only authorized clients can access protected resources. The API expects an `X-API-KEY` header to be included in every request. If the API key is missing or invalid, the server will respond with `Unauthorized` message. This mechanism secures the **GraphQL endpoint (`/graphql`)** and any other sensitive routes by verifying the provided key against a pre-configured value stored securely in the application's configuration.  


### 💡 Why GraphQL ?  

**GraphQL** offers several advantages over traditional **REST APIs**, making it a compelling choice for API development:  

- **Precise Data Retrieval** – Clients can request exactly the data they need, reducing over-fetching and under-fetching issues common in REST. This leads to more efficient data usage and optimized network performance.  
- **Single Endpoint** – Unlike REST, which often requires multiple endpoints for different resources, GraphQL operates through a single endpoint. This simplifies API interactions and reduces the complexity of managing numerous endpoints.  
- **Flexible and Strongly Typed Schema** – GraphQL uses a type system to describe data, allowing clients to understand available data and operations clearly. This enhances development efficiency and reduces errors.  
- **Efficient Data Aggregation** – Clients can fetch related data in a single request, minimizing the need for multiple roundtrips to the server. This is particularly beneficial for complex systems where data resides in different sources.  
- **API Evolution Without Versioning** – GraphQL enables APIs to evolve by adding new fields and types without impacting existing queries. Deprecated fields can be managed gracefully, reducing the need for versioning.  

These features make GraphQL a powerful alternative to REST, offering more efficient, flexible, and developer-friendly API interactions.  

---

## 🤖 Tech Stack  

The technology used in this project are:  

| Dependency                            | Description                                                                                                   |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------|
| **Spring Boot Starter Web**           | Provides foundational web support, enabling HTTP handling and server-side capabilities for GraphQL over HTTP. |
| **Spring Boot Starter GraphQL**       | Integrates GraphQL Java into the Spring ecosystem.                                                            |
| **GraphQL Java Extended Scalars**     | Provides additional scalar types such as `DateTime`, `URL`, and `BigDecimal` for GraphQL Java.                |
| **Spring Boot Starter Validation**    | Adds support for Java Bean Validation via annotations like `@NotNull`, `@Size`.                               |
| **Spring Boot Starter Security**      | Provides components for securing the application.                                                             |
| **PostgreSQL**                        | Relational database for persisting employee, department, salary, and title data.                              |
| **Hibernate**                         | Simplifies database interactions via ORM.                                                                     |
| **Lombok**                            | Reduces boilerplate code through annotations.                                                                 |

---

## 🧱 Architecture Overview  

The project is organized into the following package structure:  

```bash
📁 graphql-employee-management/
└── 📂src/
    └── 📂main/
        ├── 📂docker/
        │   ├── 📂app/                     # Dockerfile for Spring Boot application (runtime container)
        │   │   └── Dockerfile              # Uses base image, copies JAR/dependencies, defines ENTRYPOINT
        │   └── 📂postgres/                # Custom PostgreSQL Docker image (optional)
        │       ├── Dockerfile              # Extends from postgres:17, useful for init customization
        │       └── init.sql                # SQL script to create database, user, and grant permissions
        ├── 📂java/
        │   ├── 📂config/
        │   │   ├── 📂graphql/              # Configuration specific to GraphQL (e.g., scalars, schema wiring, error handling)
        │   │   └── 📂security/             # Security-related configuration (e.g., API key filters, providers, SecurityFilterChain)
        │   ├── 📂controller/               # REST API endpoints (e.g., EmployeeController, DepartmentController)
        │   ├── 📂dto/                      # Data Transfer Objects for requests/responses
        │   ├── 📂entity/                   # JPA entity classes mapped to database tables
        │   ├── 📂mapper/                   # MapStruct or manual mappers between DTO and entity
        │   ├── 📂repository/               # Spring Data JPA interfaces for database access
        │   └── 📂service/                  # Business logic layer
        │       └── 📂impl/                 # Service implementation classes
        └── 📂resources/
            ├── 📂graphql/                  # GraphQL schema definitions (`.graphqls`) used by the application
            ├── application.properties       # Application configuration (DB, profiles, etc.)
            └── import.sql                   # SQL file for seeding database on startup
```
---


## 🛠️ Installation & Setup  

Follow these steps to set up and run the project locally:  

### ✅ Prerequisites

Make sure the following tools are installed on your system:

| Tool                                                                  | Description                                                   | Required      |
|-----------------------------------------------------------------------|---------------------------------------------------------------|---------------|
| [Java 17+](https://adoptium.net/)                                     | Java Development Kit (JDK) to run the Spring application      | ✅            |
| [PostgreSQL](https://www.postgresql.org/)                             | Relational database to persist application data               | ✅            |
| [Make](https://www.gnu.org/software/make/)                            | Automation tool for tasks like `make run-app`                 | ✅            |
| [Docker](https://www.docker.com/)                                     | To run services like PostgreSQL in isolated containers        | ⚠️ *optional* |
| [GraphQL Playground](https://github.com/graphql/graphql-playground)   | GUI tool to interactively test GraphQL queries and mutations  | ⚠️ *optional* |

### ☕ 1. Install Java 17  

1. Ensure **Java 17** is installed on your system. You can verify this with:  

```bash
java --version
```  

2. If Java is not installed, follow one of the methods below based on your operating system:  

#### 🐧 Linux  

**Using apt (Ubuntu/Debian-based)**:  

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```  

#### 🪟 Windows  
1. Use [https://adoptium.net](https://adoptium.net) to download and install **Java 17 (Temurin distribution recommended)**.  

2. After installation, ensure `JAVA_HOME` is set correctly and added to the `PATH`.  

3. You can check this with:  

```bash
echo $JAVA_HOME
```  

### 🐘 2. Install PostgreSQL  
1. Install PostgreSQL if it’s not already available on your machine:  
    - Use [https://www.postgresql.org/download/](https://www.postgresql.org/download/) to download PostgreSQL.  

2. Once installed, create the following databases:  
```sql
CREATE DATABASE employees;  
```  

These databases are used for development and automated testing, respectively.  

### 🧰 3. Install `make` (Optional but Recommended)  
This project uses a `Makefile` to streamline common tasks.  

Install `make` if not already available:  

#### 🐧 Linux  

Install `make` using **APT**  

```bash
sudo apt update
sudo apt install make
```  

You can verify installation with:   
```bash
make --version
```  

#### 🪟 Windows  

If you're using **PowerShell**:  

- Install [Chocolatey](https://chocolatey.org/install) (if not installed):  
```bash
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.SecurityProtocolType]::Tls12; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
```  

- Verify `Chocolatey` installation:  
```bash
choco --version
```  

- Install `make` via `Chocolatey`:  
```bash
choco install make
```  

After installation, **restart your terminal** or ensure `make` is available in your `PATH`.  

### 🔁 4. Clone the Project  

Clone the repository:  

```bash
git clone https://github.com/yoanesber/Spring-Boot-Graphql-Employee-Management.git
cd Spring-Boot-Graphql-Employee-Management
```  

### ⚙️ 5. Configure Application Properties  

Set up your `application.properties` in `src/main/resources`:  

```properties
# application configuration
spring.application.name=graphql-employee-management
server.port=8080
spring.profiles.active=development

## datasource configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/employees
spring.datasource.username=appuser
spring.datasource.password=app@123
spring.datasource.driver-class-name=org.postgresql.Driver
spring.sql.init.mode=always

## hibernate configuration
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=true

# API Key configuration
spring.graphql.api-key=4e1f2d3a4b5c6d7e8f9g0h1i2j3k4l5m6n7o8p9
```

- **🔐 Notes**:  Ensure that:  
  - Database URLs, username, and password are correct.  
  - `spring.datasource.username=appuser`, `spring.datasource.password=app@123`: It's strongly recommended to create a dedicated database user instead of using the default postgres superuser.


### 👤 6. Create Dedicated PostgreSQL User (Recommended)

For security reasons, it's recommended to avoid using the default postgres superuser. Use the following SQL script to create a dedicated user (`appuser`) and assign permissions:

```sql
-- Create appuser and database
CREATE USER appuser WITH PASSWORD 'app@123';

-- Allow user to connect to database
GRANT CONNECT ON DATABASE employees TO appuser;

-- Grant permissions on public schema
GRANT USAGE, CREATE ON SCHEMA public TO appuser;

-- Grant all permissions on existing tables
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO appuser;

-- Grant all permissions on sequences (if using SERIAL/BIGSERIAL ids)
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO appuser;

-- Ensure future tables/sequences will be accessible too
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO appuser;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO appuser;
```

Update your `application.properties` accordingly:
```properties
spring.datasource.username=appuser
spring.datasource.password=app@123
```


## 🚀 7. Running the Application  

This section provides step-by-step instructions to run the application either **locally** or via **Docker containers**.

- **Notes**:  
  - All commands are defined in the `Makefile`.
  - To run using `make`, ensure that `make` is installed on your system.
  - To run the application in containers, make sure `Docker` is installed and running.


### 🔧 Run Locally (Non-containerized)

Ensure PostgreSQL are running locally, then:

```bash
make dev
```

### 🐳 Run Using Docker

To build and run all services (PostgreSQL, Spring app):

```bash
make docker-start-all
```

To stop and remove all containers:

```bash
make docker-stop-all
```

- **Notes**:  
  - Before running the application inside Docker, make sure to update your `application.properties`
    - Replace `localhost` with the appropriate **container name** for services like PostgreSQL.  
    - For example:
      - Change `localhost:5432` to `graphql-postgres:5432`

### 🟢 Application is Running

Now your application is accessible at:
```bash
http://localhost:8080
```

---

## 🧪 Test the GraphQL API  

Once the application is running (at `http://localhost:8080/graphql`), you can test the GraphQL API using tools like:  

- [GraphQL Playground](https://github.com/graphql/graphql-playground/releases)  
- [Postman](https://www.postman.com/downloads)  


All GraphQL requests to this API must include a valid `X-API-KEY` header for authentication. This API key serves as a security mechanism to restrict unauthorized access to the protected resources. When testing queries or mutations using tools like **Postman** or **GraphQL Playground**, make sure to add the following header to every request:  

**Headers:**  

```json
{
  "X-API-KEY":"4e1f2d3a4b5c6d7e8f9g0h1i2j3k4l5m6n7o8p9a"
}
```

**Note:** If the API key is missing or invalid, the server will respond with an authentication error and deny access to the requested operation.  

**Missing API Key Response:**  

```json
{
  "error": {
    "error": "Unauthorized: Missing API key"
  }
}
```

**Invalid API Key Response:**  

```json
{
  "error": {
    "error": "Unauthorized: Invalid API key"
  }
}
```

All API operations are served through a single HTTP endpoint:

```bash
http://localhost:8080/graphql
```

### 🏢 Department API  

#### 1. Create new departments with valid data  

**GraphQL Mutation:**  

```graphql
mutation SaveDepartment {
  saveDepartment(
    input: { id: "d011", deptName: "Security", createdBy: "1", active: true }
  ) {
    id
    deptName
    active
  }
}
```

**Successful Response:**  

```json
{
  "data": {
    "saveDepartment": {
      "id": "d011",
      "deptName": "Security",
      "active": true
    }
  }
}
```

#### 2. Create new departments with existing data   

**GraphQL Mutation:**  

```graphql
mutation SaveDepartment {
  saveDepartment(
    input: { id: "d011", deptName: "Security", createdBy: "1", active: true }
  ) {
    id
    deptName
    active
  }
}
```

**Successful Response:**  

```json
{
  "data": {
    "saveDepartment": {
      "id": "d011",
      "deptName": "Security",
      "active": true
    }
  }
}
```

**📝 Note**:
The system internally handles cases where the provided department `id` already exists in the database:
- The system will fetch the existing department record with the given ID.
- It will then update the existing record's details using the values provided in the GraphQL mutation (`deptName`, `active`, etc.).
- The `updatedBy` field will be set using the value of `createdBy` from the request.
- If the ID **does not exist**, a new record will be created as usual.

This approach provides a form of idempotent upsert behavior, where the request can act as either a create or update depending on the presence of the record.

#### 3. Retrieve all or specific departments by ID  

**GraphQL Query:**  

```graphql
query GetDepartmentById {
  getDepartmentById(id: "d011") {
    id
    deptName
    active
  }
}
```

**Successful Response:**  

```json
{
  "data": {
    "getDepartmentById": {
      "id": "d011",
      "deptName": "Security",
      "active": true
    }
  }
}
```

#### 4. Update department details  

**GraphQL Mutation:**  

```graphql
mutation UpdateDepartment {
  updateDepartment(
    id: "d011"
    input: { deptName: "Operation", active: true, updatedBy: "2" }
  ) {
    id
    deptName
    active
  }
}
```

**Successful Response:**  

```json
{
  "data": {
    "updateDepartment": {
      "id": "d011",
      "deptName": "Operation",
      "active": true
    }
  }
}
```

#### 5. Delete departments  

**GraphQL Mutation:**  

```graphql
mutation DeleteDepartment {
  deleteDepartment(id: "d011")
}
```

**Successful Response:**  

```json
{
  "data": {
    "deleteDepartment": true
  }
}
```


### 👨‍💼👩‍💼 Employee API  

#### 1. Create a new employee with initial department, salary, and title  

**GraphQL Mutation:**  

```graphql
mutation SaveEmployee {
  saveEmployee(
    input: {
      birthDate: "1990-08-01"
      firstName: "Michael"
      lastName: "jordan"
      gender: "M"
      hireDate: "2000-01-01"
      active: true
      createdBy: "1"
      departments: [
        { 
            departmentId: "d001", 
            fromDate: "2001-01-01", 
            toDate: "2005-03-30" 
        }
      ]
      salaries: [
        { 
            amount: 60116, 
            fromDate: "2001-01-01", 
            toDate: "2005-12-31" 
        }
      ]
      titles: [
        {
          title: "Senior Engineer"
          fromDate: "2001-01-01"
          toDate: "2005-12-31"
        }
      ]
    }
  ) {
    id
    birthDate
    firstName
    lastName
    gender
    hireDate
    active
    departments {
      departmentId
      fromDate
      toDate
    }
    salaries {
      amount
      fromDate
      toDate
    }
    titles {
      title
      fromDate
      toDate
    }
  }
}
```

**Successful Response:**  

```json
{
  "data": {
    "saveEmployee": {
      "id": 11,
      "birthDate": "1990-08-01",
      "firstName": "Michael",
      "lastName": "jordan",
      "gender": "M",
      "hireDate": "2000-01-01",
      "active": true,
      "departments": [
        {
          "departmentId": "d001",
          "fromDate": "2001-01-01",
          "toDate": "2005-03-30"
        }
      ],
      "salaries": [
        {
          "amount": 60116,
          "fromDate": "2001-01-01",
          "toDate": "2005-12-31"
        }
      ],
      "titles": [
        {
          "title": "Senior Engineer",
          "fromDate": "2001-01-01",
          "toDate": "2005-12-31"
        }
      ]
    }
  }
}
```

#### 2. Retrieve an employee by ID and check embedded child collections  

**GraphQL Query:**  

```graphql
query GetEmployeeById {
  getEmployeeById(id: "11") {
    id
    birthDate
    firstName
    lastName
    gender
    hireDate
    active
    departments {
      departmentId
      fromDate
      toDate
    }
    salaries {
      amount
      fromDate
      toDate
    }
    titles {
      title
      fromDate
      toDate
    }
  }
}
```

**Successful Response:**  

```json
{
  "data": {
    "getEmployeeById": {
      "id": 11,
      "birthDate": "1990-08-01",
      "firstName": "Michael",
      "lastName": "jordan",
      "gender": "M",
      "hireDate": "2000-01-01",
      "active": true,
      "departments": [
        {
          "departmentId": "d001",
          "fromDate": "2001-01-01",
          "toDate": "2005-03-30"
        }
      ],
      "salaries": [
        {
          "amount": 60116,
          "fromDate": "2001-01-01",
          "toDate": "2005-12-31"
        }
      ],
      "titles": [
        {
          "title": "Senior Engineer",
          "fromDate": "2001-01-01",
          "toDate": "2005-12-31"
        }
      ]
    }
  }
}
```

#### 3. Update an employee’s personal info and modify department/salary/title histories  

**GraphQL Mutation:**  

```graphql
mutation UpdateEmployee {
  updateEmployee(
    id: 11
    input: {
      birthDate: "1990-08-01"
      firstName: "Michael"
      lastName: "jordan"
      gender: "M"
      hireDate: "2000-01-01"
      active: true
      createdBy: "1"
      departments: [
        { 
            departmentId: "d001", 
            fromDate: "2001-01-01", 
            toDate: "2005-03-30" 
        }
        { 
            departmentId: "d002", 
            fromDate: "2006-01-01", 
            toDate: "2007-03-30" 
        }
      ]
      salaries: [
        { 
            amount: 60116, 
            fromDate: "2001-01-01", 
            toDate: "2005-12-31" 
        }
        { 
            amount: 78010, 
            fromDate: "2006-01-01", 
            toDate: "2007-03-30" 
        }
      ]
      titles: [
        {
          title: "Senior Engineer"
          fromDate: "2001-01-01"
          toDate: "2005-12-31"
        }
        {
            title: "Lead Engineer", 
            fromDate: "2006-01-01", 
            toDate: "2007-03-30" 
        }
      ]
    }
  ) {
    id
    birthDate
    firstName
    lastName
    gender
    hireDate
    active
    departments {
      departmentId
      fromDate
      toDate
    }
    salaries {
      amount
      fromDate
      toDate
    }
    titles {
      title
      fromDate
      toDate
    }
  }
}
```

**Successful Response:**  

```json
{
  "data": {
    "updateEmployee": {
      "id": 11,
      "birthDate": "1990-08-01",
      "firstName": "Michael",
      "lastName": "jordan",
      "gender": "M",
      "hireDate": "2000-01-01",
      "active": true,
      "departments": [
        {
          "departmentId": "d001",
          "fromDate": "2001-01-01",
          "toDate": "2005-03-30"
        },
        {
          "departmentId": "d002",
          "fromDate": "2006-01-01",
          "toDate": "2007-03-30"
        }
      ],
      "salaries": [
        {
          "amount": 60116,
          "fromDate": "2001-01-01",
          "toDate": "2005-12-31"
        },
        {
          "amount": 78010,
          "fromDate": "2006-01-01",
          "toDate": "2007-03-30"
        }
      ],
      "titles": [
        {
          "title": "Senior Engineer",
          "fromDate": "2001-01-01",
          "toDate": "2005-12-31"
        },
        {
          "title": "Lead Engineer",
          "fromDate": "2006-01-01",
          "toDate": "2007-03-30"
        }
      ]
    }
  }
}
```

#### 4. Remove one child entity (e.g., one salary history record) during update and ensure proper orphan removal  

**GraphQL Mutation:**  

```graphql
mutation UpdateEmployee {
  updateEmployee(
    id: 11
    input: {
      birthDate: "1990-08-01"
      firstName: "Michael"
      lastName: "jordan"
      gender: "M"
      hireDate: "2000-01-01"
      active: true
      createdBy: "1"
      departments: [
        { 
            departmentId: "d002", 
            fromDate: "2006-01-01", 
            toDate: "2007-03-30" 
        }
      ]
      salaries: [
        { 
            amount: 78010, 
            fromDate: "2006-01-01", 
            toDate: "2007-03-30" 
        }
      ]
      titles: [
        { 
            title: "Lead Engineer", 
            fromDate: "2006-01-01", 
            toDate: "2007-03-30" 
        }
      ]
    }
  ) {
    id
    birthDate
    firstName
    lastName
    gender
    hireDate
    active
    departments {
      departmentId
      fromDate
      toDate
    }
    salaries {
      amount
      fromDate
      toDate
    }
    titles {
      title
      fromDate
      toDate
    }
  }
}
```

**Successful Response:**  

```json
{
  "data": {
    "updateEmployee": {
      "id": 11,
      "birthDate": "1990-08-01",
      "firstName": "Michael",
      "lastName": "jordan",
      "gender": "M",
      "hireDate": "2000-01-01",
      "active": true,
      "departments": [
        {
          "departmentId": "d002",
          "fromDate": "2006-01-01",
          "toDate": "2007-03-30"
        }
      ],
      "salaries": [
        {
          "amount": 78010,
          "fromDate": "2006-01-01",
          "toDate": "2007-03-30"
        }
      ],
      "titles": [
        {
          "title": "Lead Engineer",
          "fromDate": "2006-01-01",
          "toDate": "2007-03-30"
        }
      ]
    }
  }
}
```

#### 5. Attempt to assign employee to non-existent department (should return validation error)  

**GraphQL Mutation:**  

```graphql
mutation UpdateEmployee {
  updateEmployee(
    id: 11
    input: {
      birthDate: "1990-08-01"
      firstName: "Michael"
      lastName: "jordan"
      gender: "M"
      hireDate: "2000-01-01"
      active: true
      createdBy: "1"
      departments: [
        {
          departmentId: "INVALID_DEPARTMENT"
          fromDate: "2006-01-01"
          toDate: "2007-03-30"
        }
      ]
      salaries: [
        { 
            amount: 78010, 
            fromDate: "2006-01-01", 
            toDate: "2007-03-30" 
        }
      ]
      titles: [
        { 
            title: "Lead Engineer", 
            fromDate: "2006-01-01", 
            toDate: "2007-03-30" 
        }
      ]
    }
  ) {
    id
    birthDate
    firstName
    lastName
    gender
    hireDate
    active
    departments {
      departmentId
      fromDate
      toDate
    }
    salaries {
      amount
      fromDate
      toDate
    }
    titles {
      title
      fromDate
      toDate
    }
  }
}
```

**Successful Response:**  

```json
{
  "errors": [
    {
      "message": "Department not found: INVALID_DEPARTMENT",
      "locations": [
        {
          "line": 2,
          "column": 3
        }
      ],
      "path": [
        "updateEmployee"
      ],
      "extensions": {
        "classification": "DataFetchingException"
      }
    }
  ],
  "data": {
    "updateEmployee": null
  }
}
```

#### 6. Delete an employee and verify cascading deletion of related child records  

**GraphQL Mutation:**  

```graphql
mutation DeleteEmployee {
  deleteEmployee(id: "11")
}
```

**Successful Response:**  

```json
{
  "data": {
    "deleteEmployee": true
  }
}
```

---

## 📝 Notes & Future Enhancements  

### Current Notes  

- This project is a backend-focused API, intended to manage **Employees** and their relationships with **Departments, Salaries, and Titles** using **GraphQL**.  
- All operations are protected by a lightweight API key mechanism using the `X-API-KEY` header.  
- The schema and DTOs are kept clean and follow **GraphQL best practices** for separation of input and output types.  
- DTO input fields are validated using **Java Bean Validation (JSR-380)** annotations such as `@NotBlank`, `@NotNull`, and custom constraints to ensure data integrity at the API level.  
- Validation errors are consistently handled through a custom `GraphQLExceptionConfig`, which transforms exceptions into structured and informative GraphQL error responses.  
- Clean architecture and clear separation of concerns are implemented using service layers, DTO mappings, and repository abstraction.  

### Planned Enhancements  

- **OAuth2 / JWT Security Integration**  

Replace the static API key mechanism with **OAuth2** or **JWT-based authentication** using Spring Security. This will enhance scalability, token expiration control, and multi-user access management.  

- **GraphQL Subscriptions**  

Implement **GraphQL subscriptions** to enable real-time notifications when an employee is created, updated, or deleted. This would be useful for reactive client-side applications.  

---

## 🔗 Related Repositories  

- REST API + PostgreSQL Repository, check out [REST API Spring Boot One To Many example with Hibernate and PostgreSQL](https://github.com/yoanesber/Spring-Boot-Hibernate-One-To-Many-PostgreSQL).  
- REST API + JWT Authentication Repository, check out [Netflix Shows REST API with JWT Authentication](https://github.com/yoanesber/Spring-Boot-JWT-Auth-PostgreSQL).  