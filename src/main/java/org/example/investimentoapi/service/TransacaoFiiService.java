package org.example.investimentoapi.service;

import org.example.investimentoapi.dto.FiiPrecoMedioResponse;
import org.example.investimentoapi.dto.TransacaoRequest;

import org.example.investimentoapi.exception.TickerNotFoundException;
import org.example.investimentoapi.mapper.TransacaoMapper;
import org.example.investimentoapi.model.Fii;
import org.example.investimentoapi.model.PrecoFii;
import org.example.investimentoapi.model.TransacaoFii;
import org.example.investimentoapi.model.User;
import org.example.investimentoapi.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransacaoFiiService {
    @Autowired
    private TransacaoFiiRepository transacaoFiiRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrecoFiiRepository precoFiiRepository;
    @Autowired
    private FiiRepository fiiRepository;

    @Autowired
    private TransacaoMapper transacaoMapper;

    public String registrarTransacao(TransacaoRequest transacaoRequest) throws Exception {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUsername);
        if (user == null) {
            log.error("User not found: {}", currentUsername);
            throw new UsernameNotFoundException("User not found");
        }

        String ticker = transacaoRequest.getTicker();
        Fii fii = fiiRepository.findByTicker(ticker);
        if (fii == null) {
            log.error("Ativo/ticker não encontrado: {}", ticker);
            throw new TickerNotFoundException("Ativo/ticker não encontrado: " + ticker);
        }

        TransacaoFii transacaoFii = new TransacaoFii();
        transacaoFii.setTicker(ticker);
        transacaoFii.setQuantidade(transacaoRequest.getQuantidade());
        transacaoFii.setValor(transacaoRequest.getValor());
        transacaoFii.setTipo(transacaoRequest.getTipo());
        transacaoFii.setDataHora(transacaoRequest.getDataHora());
        transacaoFii.setUser(user);

        transacaoFiiRepository.save(transacaoFii);
        log.info("Transação de FII registrada com sucesso para o ticker: {}", ticker);
        return "Transação de FII registrada com sucesso";
    }

    public List<TransacaoFii> getPortfolio(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found: {}", username);
            throw new UsernameNotFoundException("User not found");
        }

        log.info("Recuperando portfólio de transações para o usuário: {}", username);
        return transacaoFiiRepository.findByUser(user);
    }

    public List<FiiPrecoMedioResponse> getFiisComPrecoMedio(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found: {}", username);
            throw new UsernameNotFoundException("User not found");
        }

        log.info("Calculando preço médio dos FIIs para o usuário: {}", username);
        List<TransacaoFii> transacoesFii = transacaoFiiRepository.findByUser(user);

        Map<String, List<TransacaoFii>> transacoesPorTicker = transacoesFii.stream()
                .collect(Collectors.groupingBy(TransacaoFii::getTicker));

        List<FiiPrecoMedioResponse> fiisComPrecoMedio = new ArrayList<>();

        for (Map.Entry<String, List<TransacaoFii>> entry : transacoesPorTicker.entrySet()) {
            String ticker = entry.getKey();
            List<TransacaoFii> transacoesPorFii = entry.getValue();

            int quantidadeTotal = 0;
            double custoTotal = 0;

            for (TransacaoFii transacaoFii : transacoesPorFii) {
                if (transacaoFii.getTipo().equals("compra")) {
                    quantidadeTotal += transacaoFii.getQuantidade();
                    custoTotal += transacaoFii.getQuantidade() * transacaoFii.getValor();
                } else if (transacaoFii.getTipo().equals("venda")) {
                    quantidadeTotal -= transacaoFii.getQuantidade();
                    custoTotal -= transacaoFii.getQuantidade() * transacaoFii.getValor();
                }
            }

            double precoMedio = (quantidadeTotal > 0) ? (custoTotal / quantidadeTotal) : 0;
            Optional<PrecoFii> precoFii = precoFiiRepository.findByTicker(ticker);

            if (precoFii.isPresent()) {
                FiiPrecoMedioResponse fiiPrecoMedio = new FiiPrecoMedioResponse();
                fiiPrecoMedio.setTicker(ticker);
                fiiPrecoMedio.setQuantidade(quantidadeTotal);
                fiiPrecoMedio.setPrecoMedio(precoMedio);
                fiiPrecoMedio.setPrecoAtual(precoFii.get().getValor());
                fiisComPrecoMedio.add(fiiPrecoMedio);
            } else {
                log.warn("Preço atual não encontrado para o FII: {}", ticker);
            }
        }

        return fiisComPrecoMedio;
    }


}
