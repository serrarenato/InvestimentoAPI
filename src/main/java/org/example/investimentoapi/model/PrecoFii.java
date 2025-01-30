package org.example.investimentoapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "preco_fii")
public class PrecoFii {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;
    private double valor;
    private LocalDateTime dataAtualizacao;


}
