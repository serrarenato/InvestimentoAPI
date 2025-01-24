package org.example.investimentoapi.mapper;

import org.example.investimentoapi.dto.TransacaoResponse;
import org.example.investimentoapi.model.Transacao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransacaoMapper {
    TransacaoMapper INSTANCE = Mappers.getMapper(TransacaoMapper.class);

    TransacaoResponse toDto(Transacao transacao);

    List<TransacaoResponse> toDto(List<Transacao> transacoes);
}
