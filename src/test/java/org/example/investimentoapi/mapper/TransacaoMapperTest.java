package org.example.investimentoapi.mapper;

import org.example.investimentoapi.dto.TransacaoResponse;
import org.example.investimentoapi.model.Transacao;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransacaoMapperTest {

    private TransacaoMapper transacaoMapper;
    private ModelMapper modelMapper;

    public TransacaoMapperTest() {
        modelMapper = new ModelMapper();
        transacaoMapper= new TransacaoMapper(modelMapper);
    }
    @Test
    public void testToDto() {

        Transacao transacao = new Transacao();
        transacao.setId(1L);
        transacao.setTicker("PETR4");
        transacao.setQuantidade(50);
        transacao.setValor(28.50);
        transacao.setTipo("compra");
        transacao.setDataHora(LocalDateTime.of(2023, 9, 15, 14, 30, 0));

        TransacaoResponse response = transacaoMapper.toDto(transacao);

        assertNotNull(response);
        assertEquals(transacao.getId(), response.getId());
        assertEquals(transacao.getTicker(), response.getTicker());
        assertEquals(transacao.getQuantidade(), response.getQuantidade());
        assertEquals(transacao.getValor(), response.getValor());
        assertEquals(transacao.getTipo(), response.getTipo());
        assertEquals(transacao.getDataHora(), response.getDataHora());
    }
}
