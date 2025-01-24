package org.example.investimentoapi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "fiis")
@Data
public class Fii {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ticker;

    private String nome;
    private String setor;

}
