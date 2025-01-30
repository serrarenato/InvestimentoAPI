package org.example.investimentoapi.dto;

import lombok.Data;
import org.example.investimentoapi.enums.TipoAtivo;

import java.time.LocalDateTime;

@Data
public class TransacaoRequest {
    private String ticker;
    private int quantidade;
    private double valor;
    private String tipo; // "compra" ou "venda"
    private LocalDateTime dataHora;
    private TipoAtivo tipoAtivo; // Enum para o tipo de ativo
}
