INSERT INTO acoes (ticker, nome) VALUES
('PETR4', 'Petrobras PN'),
('VALE3', 'Vale ON'),
('ITUB4', 'Itaú Unibanco PN');

INSERT INTO transacoes (ticker, quantidade, valor, tipo, data_hora) VALUES
('PETR4', 100, 28.50, 'compra', NOW()),
('VALE3', 50, 75.00, 'compra', NOW()),
('ITUB4', 200, 22.30, 'compra', NOW());
