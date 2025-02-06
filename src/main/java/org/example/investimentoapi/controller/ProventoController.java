package org.example.investimentoapi.controller;

import org.example.investimentoapi.dto.ProventoResponse;
import org.example.investimentoapi.service.ProventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historico-proventos")
public class ProventoController {

    @Autowired
    private ProventoService proventoService;

    @GetMapping
    public List<ProventoResponse> getHistoricoProventos() {
        return proventoService.getHistoricoProventos();
    }
}
