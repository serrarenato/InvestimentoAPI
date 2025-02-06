package org.example.investimentoapi.service;

import org.example.investimentoapi.dto.ProventoResponse;
import org.example.investimentoapi.enums.TipoAtivo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ProventoService {
//TODO:Refactor to use a database
    public List<ProventoResponse> getHistoricoProventos() {
        // Dados dummy para simulação
        return Arrays.asList(
                new ProventoResponse("RNGO11", LocalDateTime.parse("2016-05-06T03:00:00.000"), LocalDateTime.parse("2016-05-16T03:00:00.000"), 11.150087253, TipoAtivo.FII, 501.753926385),
                new ProventoResponse("BRCR11", LocalDateTime.parse("2017-01-06T02:00:00.000"), LocalDateTime.parse("2017-01-13T02:00:00.000"), 0.74, TipoAtivo.FII, 33.3)
        );
    }
}
