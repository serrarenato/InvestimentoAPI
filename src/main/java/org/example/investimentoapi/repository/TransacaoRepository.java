package org.example.investimentoapi.repository;

import org.example.investimentoapi.model.Transacao;
import org.example.investimentoapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

        List<Transacao> findByUser(User user);
}
