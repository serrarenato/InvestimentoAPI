# Investment API

## Description

The **Investment API** is a Java-based application using Spring Boot that allows users to manage their transactions of stocks and REITs listed on B3. The API provides functionalities to record buy and sell transactions, calculate quantities, average prices, and display the current value of stocks.

## Prerequisites

- Java 11+
- Docker and Docker Compose
- PostgreSQL

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Flyway
- Lombok
- PostgreSQL
- Docker
- Spring Security
- Mapper
- mapstruct

## Project Setup

### Steps to Setup

1. **Clone the Repository**
    ```bash
    git clone https://github.com/your-username/investment-api.git
    cd investment-api
    ```

2. **Setup the Database with Docker Compose**
    ```bash
    docker-compose up db
    ```

3. **Run the Application**
    - In IntelliJ (or your preferred IDE), open the project and run `InvestimentoApiApplication.java`.

### API Usage

#### Register Transaction

- **Endpoint**: `POST /transacoes`
- **Request Body**:
    ```json
    {
        "ticker": "PETR4",
        "quantidade": 50,
        "valor": 28.50,
        "tipo": "compra",
        "dataHora": "2023-09-15T14:30:00"
    }
    ```

- **cURL Command**:
    ```bash
    curl -X POST http://localhost:8080/transacoes \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN" \
    -d '{
        "ticker": "PETR4",
        "quantidade": 50,
        "valor": 28.50,
        "tipo": "compra",
        "dataHora": "2023-09-15T14:30:00"
    }'
    ```

#### Get Portfolio

- **Endpoint**: `GET /transacoes/portfolio`

- **cURL Command**:
    ```bash
    curl -X GET http://localhost:8080/transacoes/portfolio \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"
    ```

### Authentication and Authorization

The API uses JWT (JSON Web Tokens) for authentication and authorization.

#### Register a New User

- **Endpoint**: `POST /auth/register`
- **Request Body**:
    ```json
    {
        "username": "testuser",
        "password": "testpassword"
    }
    ```

- **cURL Command**:
    ```bash
    curl -X POST http://localhost:8080/auth/register \
    -H "Content-Type: application/json" \
    -d '{
        "username": "testuser",
        "password": "testpassword"
    }'
    ```

#### User Login

- **Endpoint**: `POST /auth/login`
- **Request Body**:
    ```json
    {
        "username": "testuser",
        "password": "testpassword"
    }
    ```

- **cURL Command**:
    ```bash
    curl -X POST http://localhost:8080/auth/login \
    -H "Content-Type: application/json" \
    -d '{
        "username": "testuser",
        "password": "testpassword"
    }'
    ```

This will return a JWT token, for example:
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
