package org.example.investimentoapi.repository;

import org.example.investimentoapi.model.Acao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcaoRepository extends JpaRepository<Acao, String> {
}

