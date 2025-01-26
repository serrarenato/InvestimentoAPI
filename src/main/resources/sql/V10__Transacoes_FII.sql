CREATE TABLE public.transacoes_fii (
    id SERIAL PRIMARY KEY,
    ticker VARCHAR(10) NOT NULL,
    quantidade INT NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_user_id_fii FOREIGN KEY (user_id) REFERENCES public.users (id),
    CONSTRAINT fk_ticker_fii FOREIGN KEY (ticker) REFERENCES public.fiis (ticker)