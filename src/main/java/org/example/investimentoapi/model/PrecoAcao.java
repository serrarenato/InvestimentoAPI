package org.example.investimentoapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "preco_acao")
public class PrecoAcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;
    private double valor;
    private LocalDateTime dataAtualizacao;


}
