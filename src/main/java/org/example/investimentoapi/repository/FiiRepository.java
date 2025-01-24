package org.example.investimentoapi.repository;

import org.example.investimentoapi.model.Fii;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiiRepository extends JpaRepository<Fii, Long> {
    Fii findByTicker(String ticker);
}
