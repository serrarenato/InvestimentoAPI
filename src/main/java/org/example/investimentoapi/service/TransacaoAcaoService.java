package org.example.investimentoapi.service;

import org.example.investimentoapi.dto.AcaoPrecoMedioResponse;
import org.example.investimentoapi.dto.TransacaoRequest;
import org.example.investimentoapi.dto.TransacaoResponse;
import org.example.investimentoapi.exception.TickerNotFoundException;
import org.example.investimentoapi.mapper.TransacaoMapper;
import org.example.investimentoapi.model.Acao;
import org.example.investimentoapi.model.PrecoAcao;
import org.example.investimentoapi.model.Transacao;
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
public class TransacaoAcaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrecoAcaoRepository precoAcaoRepository;
    @Autowired
    private AcaoRepository acaoRepository;

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
        Acao acao = acaoRepository.findByTicker(ticker);
        if (acao == null) {
            log.error("Ativo/ticker não encontrado: {}", ticker);
            throw new TickerNotFoundException("Ativo/ticker não encontrado: " + ticker);
        }

        Transacao transacao = new Transacao();
        transacao.setTicker(ticker);
        transacao.setQuantidade(transacaoRequest.getQuantidade());
        transacao.setValor(transacaoRequest.getValor());
        transacao.setTipo(transacaoRequest.getTipo());
        transacao.setDataHora(transacaoRequest.getDataHora());
        transacao.setUser(user);

        transacaoRepository.save(transacao);
        log.info("Transação de ação registrada com sucesso para o ticker: {}", ticker);
        return "Transação de ação registrada com sucesso";
    }

    public List<Transacao> getPortfolio(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found: {}", username);
            throw new UsernameNotFoundException("User not found");
        }

        log.info("Recuperando portfólio de transações para o usuário: {}", username);
        return transacaoRepository.findByUser(user);
    }

    public List<AcaoPrecoMedioResponse> getAcoesComPrecoMedio(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found: {}", username);
            throw new UsernameNotFoundException("User not found");
        }

        log.info("Calculando preço médio das ações para o usuário: {}", username);
        List<Transacao> transacoes = transacaoRepository.findByUser(user);

        Map<String, List<Transacao>> transacoesPorTicker = transacoes.stream()
                .collect(Collectors.groupingBy(Transacao::getTicker));

        List<AcaoPrecoMedioResponse> acoesComPrecoMedio = new ArrayList<>();

        for (Map.Entry<String, List<Transacao>> entry : transacoesPorTicker.entrySet()) {
            String ticker = entry.getKey();
            List<Transacao> transacoesPorAcao = entry.getValue();

            int quantidadeTotal = 0;
            double custoTotal = 0;

            for (Transacao transacao : transacoesPorAcao) {
                if (transacao.getTipo().equals("compra")) {
                    quantidadeTotal += transacao.getQuantidade();
                    custoTotal += transacao.getQuantidade() * transacao.getValor();
                } else if (transacao.getTipo().equals("venda")) {
                    quantidadeTotal -= transacao.getQuantidade();
                    custoTotal -= transacao.getQuantidade() * transacao.getValor();
                }
            }

            double precoMedio = (quantidadeTotal > 0) ? (custoTotal / quantidadeTotal) : 0;
            Optional<PrecoAcao> precoAcao = precoAcaoRepository.findByTicker(ticker);

            if (precoAcao.isPresent()) {
                AcaoPrecoMedioResponse acaoPrecoMedio = new AcaoPrecoMedioResponse();
                acaoPrecoMedio.setQuantidade(quantidadeTotal);
                acaoPrecoMedio.setTicker(ticker);
                acaoPrecoMedio.setPrecoMedio(precoMedio);
                acaoPrecoMedio.setPrecoAtual(precoAcao.get().getValor());
                acoesComPrecoMedio.add(acaoPrecoMedio);
            } else {
                log.warn("Preço atual não encontrado para a ação: {}", ticker);
            }
        }

        return acoesComPrecoMedio;
    }

    public List<TransacaoResponse> toDto(List<Transacao> transacoes) {
        return transacaoMapper.toDto(transacoes);
    }
}
