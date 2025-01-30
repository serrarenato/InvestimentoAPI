package org.example.investimentoapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transacoes_fii")
public class TransacaoFii {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticker;
    private int quantidade;
    private double valor;
    private String tipo; // "compra" ou "venda"
    private LocalDateTime dataHora;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
