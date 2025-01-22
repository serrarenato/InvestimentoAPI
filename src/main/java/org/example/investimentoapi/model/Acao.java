package org.example.investimentoapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "acoes")
public class Acao {
    @Id
    private String ticker;
    private String nome;
}
