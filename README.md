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
- Model Mapper


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

The API allows for the following actions:

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

#### Get Portfolio

- **Endpoint**: `GET /transacoes/portfolio`

#### Get Stocks with Average and Current Price

- **Endpoint**: `GET /transacoes/acoes-preco-medio`

#### Get REITs with Average Price

- **Endpoint**: `GET /transacoes/fiis-preco-medio`

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

#### User Login

- **Endpoint**: `POST /auth/login`
- **Request Body**:
    ```json
    {
        "username": "testuser",
        "password": "testpassword"
    }
    ```

This will return a JWT token, for example:
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
   ```
## Postman Collection

- You can find a Postman collection with all the API endpoints in the root directory of the project: **API.postman_collection.json**

## Swagger
- http://localhost:8080/swagger-ui/
