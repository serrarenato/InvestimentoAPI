package org.example.investimentoapi.service;

import lombok.extern.slf4j.Slf4j;
import org.example.investimentoapi.model.Fii;
import org.example.investimentoapi.model.PrecoFii;
import org.example.investimentoapi.repository.FiiRepository;
import org.example.investimentoapi.repository.PrecoFiiRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class PrecoFiiService {

    @Autowired
    private PrecoFiiRepository precoFiiRepository;
    @Autowired
    private FiiRepository fiiRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    public void updatePrecoFiis() {
        log.info("Iniciando atualização dos preços dos FIIs");
        List<Fii> fiis = fiiRepository.findAll();
        List<CompletableFuture<Void>> futures = fiis.stream()
                .map(fii -> getPrecoAtual(fii.getTicker())
                        .thenAccept(precoAtual -> {
                            if (precoAtual > 0) { // Verifique se o preço é válido

                                PrecoFii precofii = precoFiiRepository.findByTicker(fii.getTicker())
                                        .orElse(new PrecoFii());
                                precofii.setTicker(fii.getTicker());
                                precofii.setValor(precoAtual);
                                precofii.setDataAtualizacao(LocalDateTime.now());
                                precoFiiRepository.save(precofii);
                                log.info("Preço do FII {} atualizado para {}", fii.getTicker(), precoAtual);
                            }
                        })
                )
                .toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join(); // Aguarda todas as tarefas completarem
        log.info("Atualização dos preços dos FIIs concluída");
    }

    public CompletableFuture<Double> getPrecoAtual(String ticker) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = "https://www.google.com/finance/quote/" + ticker + ":BVMF";
                Document doc = Jsoup.connect(url).get();
                String precoAtualStr = doc.select(".YMlKec.fxKbKc").first().text();
                double precoAtual = Double.parseDouble(precoAtualStr.replace(",", "").replace("R$", "").trim());
                log.info("Preço atual para {} é {}", ticker, precoAtual);
                return precoAtual;
            } catch (Exception e) {
                log.error("Erro ao buscar o preço atual para {}: {}", ticker, e.getMessage());

                return 0.0; // Valor padrão em caso de erro
            }

        }, executorService);
    }

}
