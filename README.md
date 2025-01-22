# Investimento API

## Descrição

A **Investimento API** é uma aplicação desenvolvida em Java com Spring Boot, permitindo aos usuários gerenciar suas transações de ações e FIIs da B3. A API oferece funcionalidades para registrar compras e vendas, calcular quantidades, preços médios, e exibir o valor atual das ações.

## Pré-requisitos

- Java 11+
- Docker e Docker Compose
- PostgreSQL

## Configuração do Projeto

### Passos para Configuração

1. **Clone o Repositório**
    ```bash
    git clone https://github.com/seu-usuario/investimento-api.git
    cd investimento-api
    ```

2. **Configurar o Banco de Dados com Docker Compose**
    ```bash
    docker-compose up db
    ```

3. **Rodar a Aplicação**
    - No IntelliJ (ou sua IDE preferida), abra o projeto e execute `InvestimentoApiApplication.java`.


### Uso da API

#### Registrar Transação

- **Endpoint**: `POST /transacoes`
- **Body da Requisição**:
    ```json
    {
        "ticker": "PETR4",
        "quantidade": 50,
        "valor": 28.50,
        "tipo": "compra",
        "dataHora": "2023-09-15T14:30:00"
    }
    ```

#### Obter Portfólio

- **Endpoint**: `GET /transacoes/portfolio`

### Scripts de Migração do Banco de Dados

Os scripts de migração são gerenciados pelo Flyway e localizados em `src/main/resources/db/migration`.

### Contato

Para qualquer dúvida ou suporte, entre em contato com [serra.renato@gmail.com](mailto:serra.renato@gmail.com).

---
