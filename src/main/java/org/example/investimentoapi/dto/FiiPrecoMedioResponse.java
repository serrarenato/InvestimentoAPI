package org.example.investimentoapi.dto;

import lombok.Data;

@Data
public class FiiPrecoMedioResponse {
    private String ticker;
    private double precoMedio;
    private double precoAtual;

    private int quantidade;
}
