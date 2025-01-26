CREATE TABLE preco_acao (
    id SERIAL PRIMARY KEY,
    ticker VARCHAR(10) NOT NULL,
      valor NUMERIC(10, 2) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL
);
