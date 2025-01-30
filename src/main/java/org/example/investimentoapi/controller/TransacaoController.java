package org.example.investimentoapi.controller;

import org.example.investimentoapi.dto.AcaoPrecoMedioResponse;
import org.example.investimentoapi.dto.FiiPrecoMedioResponse;
import org.example.investimentoapi.dto.TransacaoRequest;
import org.example.investimentoapi.dto.TransacaoResponse;
import org.example.investimentoapi.mapper.TransacaoMapper;
import org.example.investimentoapi.model.Transacao;
import org.example.investimentoapi.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    @Autowired
    private TransacaoService transacaoService;
    @Autowired
    private TransacaoMapper transacaoMapper;

    @PostMapping
    public String registrarTransacao(@RequestBody TransacaoRequest transacaoRequest) throws Exception {
        return transacaoService.registrarTransacao(transacaoRequest);
    }

    @GetMapping("/portfolio")
    public List<TransacaoResponse> getPortfolio() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Transacao> transacoes = transacaoService.getPortfolio(username);
        return transacaoMapper.toDto(transacoes);
    }

    @GetMapping("/acoes-preco-medio")
    public List<AcaoPrecoMedioResponse> getAcoesComPrecoMedio() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return transacaoService.getAcoesComPrecoMedio(username);
    }

    @GetMapping("/fiis-preco-medio")
    public List<FiiPrecoMedioResponse> getFiisComPrecoMedio() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return transacaoService.getFiisComPrecoMedio(username);
    }
}
