package org.example.investimentoapi.service;

import org.example.investimentoapi.dto.AcaoPrecoMedioResponse;
import org.example.investimentoapi.model.PrecoAcao;
import org.example.investimentoapi.model.Transacao;
import org.example.investimentoapi.model.User;
import org.example.investimentoapi.repository.PrecoAcaoRepository;
import org.example.investimentoapi.repository.TransacaoRepository;
import org.example.investimentoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrecoAcaoRepository precoAcaoRepository;

    @Autowired
    private PrecoAcaoService precoAcaoService; // Adicionado o serviço de preços das ações

    public String registrarTransacao(Transacao transacao) throws Exception {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUsername);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        transacao.setUser(user);
        transacaoRepository.save(transacao);

        return "Transação registrada com sucesso";
    }

    public List<Transacao> getPortfolio(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return transacaoRepository.findByUser(user);
    }

    public List<AcaoPrecoMedioResponse> getAcoesComPrecoMedio(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

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
            Optional<PrecoAcao> precoAcao = precoAcaoRepository.findByTicker(ticker); // Obtém o preço atual da ação

            AcaoPrecoMedioResponse acaoPrecoMedio = new AcaoPrecoMedioResponse();
            acaoPrecoMedio.setQuantidade(quantidadeTotal);
            acaoPrecoMedio.setTicker(ticker);
            acaoPrecoMedio.setPrecoMedio(precoMedio);
            acaoPrecoMedio.setPrecoAtual(precoAcao.get().getValor()); // Define o preço atual da ação
            acoesComPrecoMedio.add(acaoPrecoMedio);
        }

        return acoesComPrecoMedio;
    }
}
