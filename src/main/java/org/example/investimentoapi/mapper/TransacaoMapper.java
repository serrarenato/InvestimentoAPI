package org.example.investimentoapi.mapper;

import org.example.investimentoapi.dto.TransacaoResponse;
import org.example.investimentoapi.model.Transacao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransacaoMapper {


    private ModelMapper modelMapper;
    @Autowired
    public TransacaoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransacaoResponse toDto(Transacao transacao) {
        return modelMapper.map(transacao, TransacaoResponse.class);
    }

    public List<TransacaoResponse> toDto(List<Transacao> transacoes) {
        return transacoes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

