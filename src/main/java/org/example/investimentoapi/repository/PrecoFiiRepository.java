package org.example.investimentoapi.repository;

import org.example.investimentoapi.model.PrecoAcao;
import org.example.investimentoapi.model.PrecoFii;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrecoFiiRepository extends JpaRepository<PrecoFii, Long>{
    Optional<PrecoFii> findByTicker(String ticker);
}
