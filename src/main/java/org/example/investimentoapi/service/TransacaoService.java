package org.example.investimentoapi.service;

import org.example.investimentoapi.model.Acao;
import org.example.investimentoapi.model.Transacao;
import org.example.investimentoapi.repository.AcaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {
    @Autowired
    private AcaoRepository acaoRepository;

    public String registrarTransacao(Transacao transacao) throws Exception {
        Optional<Acao> acaoOpt = acaoRepository.findById(transacao.getTicker());
        if (!acaoOpt.isPresent()) {
            return "Ação não cadastrada";
        }

        // Supondo que a lógica para salvar transação é implementada aqui
        // Salve a transação no banco de dados (not implemented)

        return "Transação registrada com sucesso";
    }

    public List<Acao> getPortfolio() {
        return acaoRepository.findAll();
    }
}
