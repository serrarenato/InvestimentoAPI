package org.example.investimentoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.investimentoapi.enums.TipoAtivo;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProventoResponse {
    private String codigo;
    private LocalDateTime direito;
    private LocalDateTime pagamento;
    private double valor;
    private TipoAtivo tipoAtivo;
    private double total;
}
