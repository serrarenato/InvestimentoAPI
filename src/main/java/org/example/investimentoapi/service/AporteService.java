package org.example.investimentoapi.service;

import org.example.investimentoapi.dto.AporteResponse;
import org.example.investimentoapi.enums.Timeframe;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AporteService {
//TODO: Refazer a classe
    public List<AporteResponse> getAportes(LocalDate dataInicial, LocalDate dataFinal, Timeframe timeframe) {
        // Dados dummy para simulação
        List<Aporte> aportes = Arrays.asList(
                new Aporte(LocalDate.of(2024, 3, 10), 15000.00),
                new Aporte(LocalDate.of(2024, 3, 20), 19186.57),
                new Aporte(LocalDate.of(2019, 7, 5), 2535.00)
        );

        DateTimeFormatter formatter = timeframe == Timeframe.MENSAL ? DateTimeFormatter.ofPattern("yyyy-MM") : DateTimeFormatter.ofPattern("yyyy");

        return aportes.stream()
                .collect(Collectors.groupingBy(aporte -> aporte.getData().format(formatter)))
                .entrySet().stream()
                .map(entry -> new AporteResponse(entry.getKey(), entry.getValue().stream().mapToDouble(Aporte::getValor).sum()))
                .collect(Collectors.toList());
    }

    // Classe dummy para Aporte (substitua pelo modelo real)
    static class Aporte {
        private LocalDate data;
        private double valor;

        public Aporte(LocalDate data, double valor) {
            this.data = data;
            this.valor = valor;
        }

        public LocalDate getData() {
            return data;
        }

        public double getValor() {
            return valor;
        }
    }
}
