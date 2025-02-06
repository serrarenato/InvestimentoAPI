package org.example.investimentoapi.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.example.investimentoapi.dto.AporteResponse;
import org.example.investimentoapi.enums.Timeframe;
import org.example.investimentoapi.service.AporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/aportes")
public class AporteController {

    @Autowired
    private AporteService aporteService;
    @ApiOperation(value = "Obtém a lista de aportes entre as datas especificadas", notes = "Os parâmetros dataInicial e dataFinal devem estar no formato yyyy-MM-dd.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataInicial", value = "Data inicial no formato yyyy-MM-dd", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dataFinal", value = "Data final no formato yyyy-MM-dd", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timeframe", value = "Intervalo de tempo para filtrar ANUAL/MENSAL", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping
    public List<AporteResponse> aportes(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
            @RequestParam Timeframe timeframe) {
        return aporteService.getAportes(dataInicial, dataFinal, timeframe);
    }
}
