package org.example.investimentoapi.repository;

import org.example.investimentoapi.model.PrecoAcao;
import org.example.investimentoapi.model.Role;
import org.example.investimentoapi.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrecoAcaoRepository extends JpaRepository<PrecoAcao, Long>{
    Optional<PrecoAcao> findByTicker(String ticker);
}
