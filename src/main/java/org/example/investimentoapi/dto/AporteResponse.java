package org.example.investimentoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AporteResponse {
    private String date;
    private double total;
}
