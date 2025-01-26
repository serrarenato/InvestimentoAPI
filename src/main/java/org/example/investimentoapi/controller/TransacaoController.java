package org.example.investimentoapi.controller;


import org.example.investimentoapi.dto.AcaoPrecoMedioResponse;
import org.example.investimentoapi.dto.TransacaoResponse;
import org.example.investimentoapi.mapper.TransacaoMapper;
import org.example.investimentoapi.model.Transacao;
import org.example.investimentoapi.service.TransacaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private TransacaoMapper transacaoMapper;
    @PostMapping
    public String registrarTransacao(@RequestBody Transacao transacao) throws Exception {
        return transacaoService.registrarTransacao(transacao);
    }

    @GetMapping("/portfolio")
    public List<TransacaoResponse> getPortfolio() throws UsernameNotFoundException, Exception {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Transacao> transacoes = transacaoService.getPortfolio(currentUsername);
        List<TransacaoResponse> transacaoResponseList= transacaoMapper.toDto(transacoes);
        return  transacaoResponseList;
     }
    @GetMapping("/acoes-preco-medio")
    public List<AcaoPrecoMedioResponse> getAcoesComPrecoMedio() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return transacaoService.getAcoesComPrecoMedio(username);
    }
}