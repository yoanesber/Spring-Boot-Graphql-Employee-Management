# Spring Boot GraphQL API for Employee & Department Management  

## 📖 Overview  

A robust Spring Boot application that exposes **Employee and Department** data via a powerful **GraphQL API**. This service supports complex relationships, including **Employee-Department**, **Employee-Salary**, and **Employee-Title** associations. All API access is securely protected using an API Key passed in the request headers.  


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

- `Spring Boot Starter Web` – Provides the foundational web support for the application, enabling HTTP handling and server-side capabilities for GraphQL over HTTP.  
- `Spring Boot Starter GraphQL` – Integrates GraphQL Java into the Spring ecosystem.  
- `GraphQL Java Extended Scalars` – a library that provides additional scalar types for GraphQL Java, such as `DateTime`, `URL`, and `BigDecimal`.  
- `Spring Boot Starter Validation` – Adds support for Java Bean Validation, ensuring the integrity of incoming request data through declarative annotations like `@NotNull`, `@Size`.  
- `Spring Boot Starter Security` – Provides the necessary components for securing the application.  
- `PostgreSQL` – Database for persisting employee, department, salary, and title data.  
- `Hibernate` – Simplifying database interactions.  
- `Lombok` – Reducing boilerplate code.  

---

## 🏗️ Project Structure  

The project is organized into the following package structure:  

```bash
graphql-employee-management/
│── src/main/java/com/yoanesber/graphql_employee_management/
│   ├── 📂config/            # Configuration classes for GraphQL, Security, CORS, etc.
│   ├── 📂controller/        # GraphQL resolvers for handling queries and mutations
│   ├── 📂dto/               # Data Transfer Objects for request/response shaping
│   ├── 📂entity/            # JPA entities representing tables in the database
│   ├── 📂handler/           # Custom API key authentication filter and related logic
│   ├── 📂repository/        # Spring Data JPA repositories for data access
│   ├── 📂service/           # Service interfaces defining business logic contracts
│   │   ├── 📂impl/          # Implementations of the service interfaces
│── src/main/resources/
│   ├── 📂graphql/           # GraphQL schema files (e.g., schema.graphqls)
│   ├── application.properties  # Application configuration file (API key, DB, etc.)
```
---

## ⚙ Environment Configuration  

The application uses externalized configuration via Spring Boot's `application.properties` file, leveraging environment variables to support flexible deployment across different environments (development, staging, production, etc.).  

Below is a breakdown of the key configurations:  

```properties
# Application environment variables
spring.application.name=graphql-employee-management
server.port=${APP_PORT}
spring.profiles.active=${SPRING_PROFILES_ACTIVE}

# Database configuration
spring.datasource.url=jdbc:postgresql://localhost:${SPRING_DATASOURCE_PORT}/${SPRING_DATASOURCE_DB}?currentSchema=${SPRING_DATASOURCE_SCHEMA}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

# API configuration
app.api.key=${APP_API_KEY}
```

You can configure these variables via a `.env` file, CI/CD pipeline, or your host system’s environment.

---

## 💾 Database Schema (DDL – PostgreSQL)  

The following is the database schema for the PostgreSQL database used in this project:  

```sql
CREATE SCHEMA your_schema;

-- table your_schema.employee
CREATE TABLE IF NOT EXISTS your_schema.employee (
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    birth_date date NOT NULL,
    first_name character varying(20) NOT NULL,
    last_name character varying(20),
    gender character varying(1) NOT NULL,
    hire_date date NOT NULL,
    active boolean DEFAULT false NOT NULL,
    created_by bigint NOT NULL,
    created_date timestamp with time zone DEFAULT now() NOT NULL,
    updated_by bigint NOT NULL,
    updated_date timestamp with time zone DEFAULT now() NOT NULL,
    CONSTRAINT employee_pkey PRIMARY KEY (id),
);

-- table your_schema.department
CREATE TABLE IF NOT EXISTS your_schema.department (
    id character varying(4) NOT NULL,
    dept_name character varying(40) NOT NULL,
    active boolean NOT NULL,
    created_by bigint NOT NULL,
    created_date timestamp with time zone DEFAULT now() NOT NULL,
    updated_by bigint NOT NULL,
    updated_date timestamp with time zone DEFAULT now() NOT NULL,
    CONSTRAINT department_pkey PRIMARY KEY (id)
);

-- table your_schema.department_employee
CREATE TABLE IF NOT EXISTS your_schema.department_employee (
    employee_id bigint NOT NULL,
    department_id character varying(255) NOT NULL,
    from_date date NOT NULL,
    to_date date NOT NULL,
    CONSTRAINT department_employee_pkey PRIMARY KEY (employee_id, department_id),
    CONSTRAINT department_employee_fkey1 FOREIGN KEY (employee_id) REFERENCES your_schema.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT department_employee_fkey2 FOREIGN KEY (department_id) REFERENCES your_schema.department(id) ON UPDATE RESTRICT ON DELETE CASCADE
);


-- table your_schema.salary
CREATE TABLE IF NOT EXISTS your_schema.salary (
    employee_id bigint NOT NULL,
    amount bigint NOT NULL,
    from_date date NOT NULL,
    to_date date NOT NULL,
    CONSTRAINT salary_pkey PRIMARY KEY (employee_id, from_date),
    CONSTRAINT salary_fkey FOREIGN KEY (employee_id) REFERENCES your_schema.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE
);

-- table your_schema.title
CREATE TABLE IF NOT EXISTS your_schema.title (
    employee_id bigint NOT NULL,
    title character varying(50) NOT NULL,
    from_date date NOT NULL,
    to_date date,
    CONSTRAINT title_pkey PRIMARY KEY (employee_id, title, from_date),
    CONSTRAINT title_fkey FOREIGN KEY (employee_id) REFERENCES your_schema.employee(id) ON UPDATE RESTRICT ON DELETE CASCADE
);

```

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

---


## 🛠️ Installation & Setup  

To get started with the **Spring Boot GraphQL API for Employee & Department Management**, follow the steps below to configure your environment properly.  

1. Ensure you have **Git installed on your Windows** machine, then clone the repository to your local environment:  

```bash
git clone https://github.com/yoanesber/Spring-Boot-Graphql-Employee-Management.git
cd Spring-Boot-Graphql-Employee-Management
```

2. Set up PostgreSQL  

The application connects to a PostgreSQL database for persistent storage of employee, department, salary, and title data.

- Run the provided DDL script to set up the database schema  
- Ensure the following environment variables are defined before starting the application:  

```properties
# Database configuration
spring.datasource.url=jdbc:postgresql://localhost:${SPRING_DATASOURCE_PORT}/${SPRING_DATASOURCE_DB}?currentSchema=${SPRING_DATASOURCE_SCHEMA}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

Once configured, make sure the PostgreSQL instance is running and accessible before starting the Spring Boot application.  

3. API Key Configuration  

To secure access to the GraphQL API, the application requires an API key to be included in each request via the `X-API-KEY` header.  

```properties
# API configuration
app.api.key=${APP_API_KEY}
```

Make sure to set the `APP_API_KEY` environment variable in your local environment, `.env` file, or deployment platform. Requests without a valid API key will receive an authorization error.  

4. Run the application locally  

Make sure PostgreSQL is running, then execute:  

```bash
mvn spring-boot:run
```

5. The server will start at:  

```bash
http://localhost:8080/graphql
```

---

## 🧪 Test the GraphQL API  

Once the application is running (at http://localhost:8080/graphql), you can test the GraphQL API using tools like:  

- [GraphQL Playground](https://github.com/graphql/graphql-playground/releases)  
- [Postman](https://www.postman.com/downloads)  


All GraphQL requests to this API must include a valid `X-API-KEY` header for authentication. This API key serves as a security mechanism to restrict unauthorized access to the protected resources. When testing queries or mutations using tools like **Postman** or **GraphQL Playground**, make sure to add the following header to every request:  

**Headers:**  

```http
Content-Type: application/json
X-API-KEY: your-api-key-here
```

**Note:** If the API key is missing or invalid, the server will respond with an authentication error and deny access to the requested operation.  


**Invalid or Missing API Key Response:**  

```json
{
    "error": "Unauthorized: Invalid or missing API key"
}
```

### Department API Endpoints  

Apis to create, retrieve, update, delete Department.  

1. Save a New Department  

**Request:**  

```graphql
mutation SaveDepartment {
    saveDepartment(
        departmentCreateDTO: { id: "d001", deptName: "Support", active: false, createdBy: "1" }
    ) {
        id
        deptName
        active
        createdBy
        createdDate
        updatedBy
        updatedDate
    }
}
```

**Successful Response:**  

```json
{
    "data": {
        "saveDepartment": {
            "id": "d001",
            "deptName": "Support",
            "active": false,
            "createdBy": 1,
            "createdDate": "2025-04-10T13:25:39.874+07:00",
            "updatedBy": 1,
            "updatedDate": "2025-04-10T13:25:39.874+07:00"
        }
    }
}
```

❌ Example: Duplicate Department ID  

**Request:**  

```graphql
mutation SaveDepartment {
    saveDepartment(
        departmentCreateDTO: { id: "d001", deptName: "Support", active: false, createdBy: "1" }
    ) {
        id
        deptName
        active
        createdBy
        createdDate
        updatedBy
        updatedDate
    }
}
```

**Response (Duplicate ID Error):**  

```json
{
    "errors": [
        {
            "message": "Error saving department: Department with id d001 already exists",
            "locations": [
                {
                    "line": 2,
                    "column": 5
                }
            ],
            "path": [
                "saveDepartment"
            ],
            "extensions": {
                "classification": "DataFetchingException"
            }
        }
    ],
    "data": {
        "saveDepartment": null
    }
}
```

2. Get All Departments  

✅ Full Fields Retrieval  

You can request all available fields for each department.  

**Request:**  

```graphql
query GetAllDepartments {
    getAllDepartments {
        id
        deptName
        active
        createdBy
        createdDate
        updatedBy
        updatedDate
    }
}
```

**Successful Response:**  

```json
{
    "data": {
        "getAllDepartments": [
            {
                "id": "d001",
                "deptName": "Marketing",
                "active": true,
                "createdBy": 1,
                "createdDate": "2024-10-07T17:51:24.616Z",
                "updatedBy": 1,
                "updatedDate": "2024-11-11T16:58:30.929Z"
            },
            {
                "id": "d002",
                "deptName": "Finance",
                "active": true,
                "createdBy": 1,
                "createdDate": "2024-10-07T17:51:24.616Z",
                "updatedBy": 1,
                "updatedDate": "2024-10-07T17:51:24.616Z"
            },
            ...
        ]
    }
}
```

✅ Partial Fields Retrieval  

**GraphQL** allows you to **fetch only the fields you need**, reducing unnecessary data transfer and improving performance.  

**Request (only id and deptName):**  

```graphql
query GetAllDepartments {
    getAllDepartments {
        id
        deptName
    }
}
```

**Response:**  

```json
{
    "data": {
        "getAllDepartments": [
            {
                "id": "d001",
                "deptName": "Marketing"
            },
            {
                "id": "d002",
                "deptName": "Finance"
            },
            ...
        ]
    }
}
```

3. Get Department by ID  

**Request:**  

```graphql
query GetDepartmentById {
    getDepartmentById(id: "d001") {
        id
        deptName
        active
        createdBy
        createdDate
        updatedBy
        updatedDate
    }
}
```

**Successful Response:**  

```json
{
    "data": {
        "getDepartmentById": {
            "id": "d001",
            "deptName": "Marketing",
            "active": true,
            "createdBy": 1,
            "createdDate": "2024-10-07T17:51:24.616Z",
            "updatedBy": 1,
            "updatedDate": "2024-11-11T16:58:30.929Z"
        }
    }
}
```

❌ Example: Get Department by Invalid ID  

**Request:**  

```graphql
query GetDepartmentById {
    getDepartmentById(id: "xxx") {
        id
        deptName
        active
        createdBy
        createdDate
        updatedBy
        updatedDate
    }
}
```

**Response (If Department Not Found):**  

```json
{
    "errors": [
        {
            "message": "Error getting department by id: Department with id xxx does not exist",
            "locations": [
                {
                    "line": 2,
                    "column": 5
                }
            ],
            "path": [
                "getDepartmentById"
            ],
            "extensions": {
                "classification": "DataFetchingException"
            }
        }
    ],
    "data": {
        "getDepartmentById": null
    }
}
```

❌ Example: Missing Required Field  

**Request:**  

```graphql
query GetDepartmentById {
    getDepartmentById(id: "") {
        id
        deptName
        active
        createdBy
        createdDate
        updatedBy
        updatedDate
    }
}
```

**Response (Validation Error):**  

```json
{
    "errors": [
        {
            "message": "Department ID cannot be blank",
            "locations": [
                {
                    "line": 2,
                    "column": 5
                }
            ],
            "path": [
                "getDepartmentById"
            ],
            "extensions": {
                "classification": "DataFetchingException"
            }
        }
    ],
    "data": {
        "getDepartmentById": null
    }
}
```

❌ Example: Invalid Syntax  

**Request:**  

```graphql
query GetDepartmentById {
    getDepartmentById() {
        id
        deptName
        active
        createdBy
        createdDate
        updatedBy
        updatedDate
    }
}
```

**Response (Invalid Syntax):**  

```json
{
    "errors": [
        {
            "message": "Invalid syntax with offending token ')' at line 2 column 23",
            "locations": [
                {
                    "line": 2,
                    "column": 23
                }
            ],
            "extensions": {
                "classification": "InvalidSyntax"
            }
        }
    ]
}
```

4. Update an Existing Department  

**Request:**  

```graphql
mutation UpdateDepartment {
    updateDepartment(
        id: "d001"
        departmentUpdateDTO: { deptName: "Supply & Chain", active: true, updatedBy: "2" }
    ) {
        id
        deptName
        active
        createdBy
        createdDate
        updatedBy
        updatedDate
    }
}
```

**Successful Response:**  

```json
{
    "data": {
        "updateDepartment": {
            "id": "d001",
            "deptName": "Supply & Chain",
            "active": true,
            "createdBy": 1,
            "createdDate": "2025-04-10T06:25:39.874Z",
            "updatedBy": 2,
            "updatedDate": "2025-04-10T14:07:11.223+07:00"
        }
    }
}
```

❌ Example: Update Department by Invalid ID  

**Request:**  

```graphql
mutation UpdateDepartment {
    updateDepartment(
        id: "xxx"
        departmentUpdateDTO: { deptName: "Supply & Chain", active: true, updatedBy: "2" }
    ) {
        id
        deptName
        active
        createdBy
        createdDate
        updatedBy
        updatedDate
    }
}
```

**Response (If Department Not Found):**  

```json
{
    "errors": [
        {
            "message": "Department with id xxx does not exist",
            "locations": [
                {
                    "line": 2,
                    "column": 5
                }
            ],
            "path": [
                "updateDepartment"
            ],
            "extensions": {
                "classification": "DataFetchingException"
            }
        }
    ],
    "data": {
        "updateDepartment": null
    }
}
```

5. Delete a Department  

**Request:**  

```graphql
mutation DeleteDepartment {
    deleteDepartment(id: "d001")
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


### Employee Operations  

The following GraphQL operations allow you to manage employee records along with their department, salary, and title associations.  

1. Save a New Employee  

**Request:**  

```graphql
mutation SaveEmployee {
    saveEmployee(
        employeeCreateDTO: {
            birthDate: "1990-07-09"
            firstName: "Jenny"
            lastName: null
            gender: "F"
            hireDate: "2025-01-01"
            activeStatus: true
            createdBy: "1"
            departments: [{
                departmentId: "d001"
                fromDate: "2025-01-01"
                toDate: "2025-03-30"
            }]
          	salaries: [{
                amount: 60116
                fromDate: "2000-01-01"
                toDate: "2005-12-31"
            }]
            titles: [{
                title: "Senior Engineer"
                fromDate: "2000-01-01"
                toDate: "2005-12-31"
            }]
        }
    ) {
        id
        birthDate
        firstName
        lastName
        gender
        hireDate
        activeStatus
        createdBy
        createdDate
        updatedBy
        updatedDate
    }
}
```


**Successful Response:**  

```json
{
    "data": {
        "saveEmployee": {
            "id": 1,
            "birthDate": "1990-07-09",
            "firstName": "Jenny",
            "lastName": null,
            "gender": "F",
            "hireDate": "2025-01-01",
            "activeStatus": true,
            "createdBy": 1,
            "createdDate": "2025-04-10T14:37:03.055+07:00",
            "updatedBy": 1,
            "updatedDate": "2025-04-10T14:37:03.055+07:00"
        }
    }
}
```


❌ Example: Missing First Name  

**Request:**  

```graphql
mutation SaveEmployee {
    saveEmployee(
        employeeCreateDTO: {
            birthDate: "1990-07-09"
            firstName: null
            lastName: null
            gender: "F"
            hireDate: "2025-01-01"
            activeStatus: true
            createdBy: "1"
            departments: [{
                departmentId: "d001"
                fromDate: "2025-01-01"
                toDate: "2025-03-30"
            }]
          	salaries: [{
                amount: 60116
                fromDate: "2000-01-01"
                toDate: "2005-12-31"
            }]
            titles: [{
                title: "Senior Engineer"
                fromDate: "2000-01-01"
                toDate: "2005-12-31"
            }]
        }
    ) {
        id
        birthDate
        firstName
        lastName
        gender
        hireDate
        activeStatus
        createdBy
        createdDate
        updatedBy
        updatedDate
    }
}

```

**Response:**  

```json
{
    "errors": [
        {
            "message": "[saveEmployee.employeeCreateDTO.firstName] First Name cannot be blank",
            "locations": [
                {
                    "line": 3,
                    "column": 5
                }
            ],
            "path": [
                "saveEmployee"
            ],
            "extensions": {
                "classification": "ValidationError"
            }
        }
    ],
    "data": {
        "saveEmployee": null
    }
}
```


2. Get All Employees  

✅ Full Fields Retrieval  

You can request all available fields for each employee.  

**Request:**  

```graphql
query GetAllEmployees {
    getAllEmployees {
        id
        birthDate
        firstName
        lastName
        gender
        hireDate
        activeStatus
        createdBy
        createdDate
        updatedBy
        updatedDate
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
        "getAllEmployees": [
            {
                "id": 10001,
                "birthDate": "1990-07-09",
                "firstName": "Jenny",
                "lastName": null,
                "gender": "F",
                "hireDate": "2025-01-01",
                "activeStatus": true,
                "createdBy": 1,
                "createdDate": "2025-04-10T07:37:03.055Z",
                "updatedBy": 1,
                "updatedDate": "2025-04-10T07:37:03.055Z",
                "departments": [
                    {
                        "departmentId": "d001",
                        "fromDate": "2025-01-01",
                        "toDate": "2025-03-30"
                    }
                ],
                "salaries": [
                    {
                        "amount": 60116,
                        "fromDate": "2000-01-01",
                        "toDate": "2005-12-31"
                    }
                ],
                "titles": [
                    {
                        "title": "Senior Engineer",
                        "fromDate": "2000-01-01",
                        "toDate": "2005-12-31"
                    }
                ]
            },
            ...
        ]
    }
}
```

✅ Partial Fields Retrieval  

**GraphQL** allows you to **fetch only the fields you need**, reducing unnecessary data transfer and improving performance.  

**Request (only id, firstName, departments):**  

```graphql
query GetAllEmployees {
    getAllEmployees {
        id
        firstName
        departments {
            departmentId
            fromDate
            toDate
        }
    }
}
```

**Response:**  

```json
{
    "data": {
        "getAllEmployees": [
            {
                "id": 10001,
                "firstName": "Jenny",
                "departments": [
                    {
                        "departmentId": "d002",
                        "fromDate": "2025-01-01",
                        "toDate": "2025-03-30"
                    }
                ]
            },
            {
                "id": 10002,
                "firstName": "Bezalel",
                "departments": [
                    {
                        "departmentId": "d010",
                        "fromDate": "2024-01-01",
                        "toDate": "2024-09-30"
                    }
                ]
            },
            ...
        ]
    }
}
```

3. Get Employee by ID  

**Request:**  

```graphql
query GetEmployeeById {
    getEmployeeById(id: "10001") {
        id
        birthDate
        firstName
        lastName
        gender
        hireDate
        activeStatus
        createdBy
        createdDate
        updatedBy
        updatedDate
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
            "id": 10001,
            "birthDate": "1990-07-09",
            "firstName": "Jenny",
            "lastName": null,
            "gender": "F",
            "hireDate": "2025-01-01",
            "activeStatus": true,
            "createdBy": 1,
            "createdDate": "2025-04-10T07:37:03.055Z",
            "updatedBy": 1,
            "updatedDate": "2025-04-10T07:37:03.055Z",
            "departments": [
                {
                    "departmentId": "d001",
                    "fromDate": "2025-01-01",
                    "toDate": "2025-03-30"
                }
            ],
            "salaries": [
                {
                    "amount": 60116,
                    "fromDate": "2000-01-01",
                    "toDate": "2005-12-31"
                }
            ],
            "titles": [
                {
                    "title": "Senior Engineer",
                    "fromDate": "2000-01-01",
                    "toDate": "2005-12-31"
                }
            ]
        }
    }
}
```

❌ Example: Invalid ID  

**Request:**  

```graphql
query GetEmployeeById {
    getEmployeeById(id: "101100") {
        id
        birthDate
        firstName
        lastName
        gender
        hireDate
        activeStatus
        createdBy
        createdDate
        updatedBy
        updatedDate
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


**Response (If Department Not Found):**  

```json
{
    "errors": [
        {
            "message": "Employee with id 101100 does not exist",
            "locations": [
                {
                    "line": 2,
                    "column": 5
                }
            ],
            "path": [
                "getEmployeeById"
            ],
            "extensions": {
                "classification": "DataFetchingException"
            }
        }
    ],
    "data": {
        "getEmployeeById": null
    }
}
```


4. Update an Existing Employee  

**Request:**  

```graphql
mutation UpdateEmployee {
    updateEmployee(
        id: "10001"
        employeeUpdateDTO: {
            birthDate: "1990-07-08"
            activeStatus: false
            firstName: "Jenny"
            lastName: "Isabelle"
            gender: "F"
            hireDate: "2025-01-01"
            updatedBy: 1
            departments: [{
                departmentId: "d002"
                fromDate: "2025-01-01"
                toDate: "2025-03-30"
            }]
          	salaries: [{
                amount: 60117
                fromDate: "2000-01-01"
                toDate: "2005-12-31"
            }]
            titles: [{
                title: "Senior Engineer"
                fromDate: "2000-01-01"
                toDate: "2005-12-31"
            }]
        }
    ) {
        id
        birthDate
        firstName
        lastName
        gender
        hireDate
        activeStatus
        createdBy
        createdDate
        updatedBy
        updatedDate
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
            "id": 10001,
            "birthDate": "1990-07-08",
            "firstName": "Jenny",
            "lastName": "Isabelle",
            "gender": "F",
            "hireDate": "2025-01-01",
            "activeStatus": false,
            "createdBy": 1,
            "createdDate": "2025-04-10T07:37:03.055Z",
            "updatedBy": 1,
            "updatedDate": "2025-04-10T14:50:00.269+07:00",
            "departments": [
                {
                    "departmentId": "d002",
                    "fromDate": "2025-01-01",
                    "toDate": "2025-03-30"
                }
            ],
            "salaries": [
                {
                    "amount": 60117,
                    "fromDate": "2000-01-01",
                    "toDate": "2005-12-31"
                }
            ],
            "titles": [
                {
                    "title": "Senior Engineer",
                    "fromDate": "2000-01-01",
                    "toDate": "2005-12-31"
                }
            ]
        }
    }
}
```

❌ Example: Update Employee by Invalid ID  

**Request:**  

```graphql
mutation UpdateEmployee {
    updateEmployee(
        id: "100011"
        employeeUpdateDTO: {
            birthDate: "1990-07-08"
            activeStatus: false
            firstName: "Jenny"
            lastName: "Isabelle"
            gender: "F"
            hireDate: "2025-01-01"
            updatedBy: 1
            departments: [{
                departmentId: "d002"
                fromDate: "2025-01-01"
                toDate: "2025-03-30"
            }]
          	salaries: [{
                amount: 60117
                fromDate: "2000-01-01"
                toDate: "2005-12-31"
            }]
            titles: [{
                title: "Senior Engineer"
                fromDate: "2000-01-01"
                toDate: "2005-12-31"
            }]
        }
    ) {
        id
        birthDate
        firstName
        lastName
        gender
        hireDate
        activeStatus
        createdBy
        createdDate
        updatedBy
        updatedDate
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

**Response (If Employee Not Found):**  

```json
{
    "errors": [
        {
            "message": "Employee with id 100011 does not exist",
            "locations": [
                {
                    "line": 2,
                    "column": 5
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


5. Delete an Employee


**Request:**  

```graphql
mutation DeleteEmployee {
    deleteEmployee(id: "10030")
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

- REST Api Repository, check out [REST API Spring Boot One To Many example with Hibernate and PostgreSQL](https://github.com/yoanesber/Spring-Boot-Hibernate-One-To-Many-PostgreSQL).
- REST API with JWT Authentication Repository, check out [Netflix Shows REST API with JWT Authentication](https://github.com/yoanesber/Spring-Boot-JWT-Auth-PostgreSQL).