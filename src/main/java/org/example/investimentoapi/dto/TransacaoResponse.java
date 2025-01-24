package org.example.investimentoapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransacaoResponse {
    private Long id;
    private String ticker;
    private int quantidade;
    private double valor;
    private String tipo; // "compra" ou "venda"
    private LocalDateTime dataHora;
}
