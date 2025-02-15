package org.example.investimentoapi.service;

import org.example.investimentoapi.model.PrecoAcao;
import org.example.investimentoapi.model.Acao;
import org.example.investimentoapi.repository.PrecoAcaoRepository;
import org.example.investimentoapi.repository.AcaoRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import lombok.extern.slf4j.Slf4j;
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
public class PrecoAcaoService {

    @Autowired
    private PrecoAcaoRepository precoAcaoRepository;

    @Autowired
    private AcaoRepository acaoRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    public void updatePrecoAcoes() {
        log.info("Iniciando atualização dos preços das ações");
        List<Acao> acoes = acaoRepository.findAll();
        List<CompletableFuture<Void>> futures = acoes.stream()
                .map(acao -> getPrecoAtual(acao.getTicker())
                        .thenAccept(precoAtual -> {
                            if (precoAtual > 0) { // Verifique se o preço é válido
                                PrecoAcao precoAcao = precoAcaoRepository.findByTicker(acao.getTicker())
                                        .orElse(new PrecoAcao());
                                precoAcao.setTicker(acao.getTicker());
                                precoAcao.setValor(precoAtual);
                                precoAcao.setDataAtualizacao(LocalDateTime.now());
                                precoAcaoRepository.save(precoAcao);
                                log.info("Preço da ação {} atualizado para {}", acao.getTicker(), precoAtual);
                            } else {
                                log.warn("Preço inválido para a ação {}", acao.getTicker());
                            }
                        })
                )
                .toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join(); // Aguarda todas as tarefas completarem
        log.info("Atualização dos preços das ações concluída");
    }

    private CompletableFuture<Double> getPrecoAtual(String ticker) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("Buscando preço atual para o ticker: {}", ticker);
                String url = "https://www.google.com/finance/quote/" + ticker + ":BVMF";
                Document doc = Jsoup.connect(url).get();
                String precoAtualStr = doc.select(".YMlKec.fxKbKc").first().text();
                double precoAtual = Double.parseDouble(precoAtualStr.replace(",", "").replace("R$", "").trim());
                log.info("Preço atual para {} é {}", ticker, precoAtual);
                return precoAtual;
            } catch (IOException | NumberFormatException e) {
                log.error("Erro ao buscar o preço atual para {}: {}", ticker, e.getMessage());
                return 0.0; // Valor padrão em caso de erro
            }
        }, executorService);
    }
}
