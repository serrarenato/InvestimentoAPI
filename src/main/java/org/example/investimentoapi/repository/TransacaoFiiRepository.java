package org.example.investimentoapi.repository;


import org.example.investimentoapi.model.TransacaoFii;
import org.example.investimentoapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoFiiRepository extends JpaRepository<TransacaoFii, Long> {

        List<TransacaoFii> findByUser(User user);
}
