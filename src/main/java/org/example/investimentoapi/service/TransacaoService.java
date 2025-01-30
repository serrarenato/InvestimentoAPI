package org.example.investimentoapi.service;

import org.example.investimentoapi.dto.AcaoPrecoMedioResponse;
import org.example.investimentoapi.dto.FiiPrecoMedioResponse;
import org.example.investimentoapi.dto.TransacaoRequest;
import org.example.investimentoapi.enums.TipoAtivo;
import org.example.investimentoapi.model.*;
import org.example.investimentoapi.repository.*;
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
    private PrecoFiiRepository precoFiiRepository;
    @Autowired
    private TransacaoFiiRepository transacaoFiiRepository;



    @Autowired
    private PrecoFiiService precoFiiService;

    @Autowired
    private PrecoAcaoService precoAcaoService; // Adicionado o serviço de preços das ações

    public String registrarTransacao(TransacaoRequest transacaoRequest) throws Exception {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUsername);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        TipoAtivo tipoAtivo = transacaoRequest.getTipoAtivo();

        switch (tipoAtivo) {
            case ACAO:
                return registrarTransacaoAcao(transacaoRequest, user);
            case FII:
                return registrarTransacaoFii(transacaoRequest, user);
            // Adicione outros tipos de ativo conforme necessário
            default:
                throw new IllegalArgumentException("Tipo de ativo não suportado: " + tipoAtivo);
        }
    }
    private String registrarTransacaoAcao(TransacaoRequest transacaoRequest, User user) {
        Transacao transacao = new Transacao();
        transacao.setTicker(transacaoRequest.getTicker());
        transacao.setQuantidade(transacaoRequest.getQuantidade());
        transacao.setValor(transacaoRequest.getValor());
        transacao.setTipo(transacaoRequest.getTipo());
        transacao.setDataHora(transacaoRequest.getDataHora());
        transacao.setUser(user);

        transacaoRepository.save(transacao);
        return "Transação de ação registrada com sucesso";
    }
    private String registrarTransacaoFii(TransacaoRequest transacaoRequest, User user) {
        TransacaoFii transacaoFii = new TransacaoFii();
        transacaoFii.setTicker(transacaoRequest.getTicker());
        transacaoFii.setQuantidade(transacaoRequest.getQuantidade());
        transacaoFii.setValor(transacaoRequest.getValor());
        transacaoFii.setTipo(transacaoRequest.getTipo());
        transacaoFii.setDataHora(transacaoRequest.getDataHora());
        transacaoFii.setUser(user);

        transacaoFiiRepository.save(transacaoFii);
        return "Transação de FII registrada com sucesso";
    }
    public String registrarTransacaoFii(TransacaoRequest transacaoRequest) throws Exception {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUsername);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        TransacaoFii transacaoFii = new TransacaoFii();
        transacaoFii.setTicker(transacaoRequest.getTicker());
        transacaoFii.setQuantidade(transacaoRequest.getQuantidade());
        transacaoFii.setValor(transacaoRequest.getValor());
        transacaoFii.setTipo(transacaoRequest.getTipo());
        transacaoFii.setDataHora(transacaoRequest.getDataHora());
        transacaoFii.setUser(user);

        transacaoFiiRepository.save(transacaoFii);

        return "Transação de FII registrada com sucesso";

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
    public List<FiiPrecoMedioResponse> getFiisComPrecoMedio(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<TransacaoFii> transacoesFii = transacaoFiiRepository.findByUser(user);

        Map<String, List<TransacaoFii>> transacoesPorTicker = transacoesFii.stream()
                .collect(Collectors.groupingBy(TransacaoFii::getTicker));

        List<FiiPrecoMedioResponse> fiisComPrecoMedio = new ArrayList<>();

        for (Map.Entry<String, List<TransacaoFii>> entry : transacoesPorTicker.entrySet()) {
            String ticker = entry.getKey();
            List<TransacaoFii> transacoesPorFii = entry.getValue();

            double quantidadeTotal = 0;
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
            Optional<PrecoFii> precoFii =  precoFiiRepository.findByTicker(ticker); // Obtém o preço atual do FII

            FiiPrecoMedioResponse fiiPrecoMedio = new FiiPrecoMedioResponse();
            fiiPrecoMedio.setTicker(ticker);
            fiiPrecoMedio.setPrecoMedio(precoMedio);
            fiiPrecoMedio.setPrecoAtual(precoFii.get().getValor()); // Define o preço atual do FII
            fiisComPrecoMedio.add(fiiPrecoMedio);
        }

        return fiisComPrecoMedio;
    }
}
