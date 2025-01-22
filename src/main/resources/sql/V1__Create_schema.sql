CREATE TABLE acoes (
    ticker VARCHAR(10) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE transacoes (
    id SERIAL PRIMARY KEY,
    ticker VARCHAR(10) REFERENCES acoes(ticker),
    quantidade INT NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    data_hora TIMESTAMP NOT NULL
);
