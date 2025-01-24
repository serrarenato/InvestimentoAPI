package org.example.investimentoapi.service;

import org.example.investimentoapi.model.Acao;
import org.example.investimentoapi.model.Transacao;
import org.example.investimentoapi.repository.AcaoRepository;
import org.example.investimentoapi.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcaoService {
    @Autowired
    private AcaoRepository acaoRepository;


    public String registrarAcao(Acao acao) throws Exception {
        Optional<Acao> acaoOpt = acaoRepository.findById(acao.getTicker());
        if (acaoOpt.isPresent()) {
            return "Ação ja cadastrada";
        }

        // Salvar a transação no banco de dados
        acaoRepository.save(acao);

        return "Transação registrada com sucesso";
    }

    public List<Acao> getPortfolio() {
        return acaoRepository.findAll();
    }
}
