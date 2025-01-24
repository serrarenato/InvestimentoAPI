package org.example.investimentoapi.service;

import org.example.investimentoapi.dto.TransacaoResponse;
import org.example.investimentoapi.model.Transacao;
import org.example.investimentoapi.model.User;
import org.example.investimentoapi.repository.TransacaoRepository;
import org.example.investimentoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UserRepository userRepository;

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
        List<Transacao> transacoes =transacaoRepository.findByUser(user);
        return transacoes;
    }
}
