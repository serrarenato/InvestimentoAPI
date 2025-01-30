CREATE TABLE preco_fii (
    id SERIAL PRIMARY KEY,
    ticker VARCHAR(10) NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    CONSTRAINT fk_ticker_precofii FOREIGN KEY (ticker) REFERENCES public.fiis (ticker)
);

