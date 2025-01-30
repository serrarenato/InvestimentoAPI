package org.example.investimentoapi.config;

import org.example.investimentoapi.service.PrecoAcaoService;
import org.example.investimentoapi.service.PrecoFiiService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CronJobConfig {

    private final PrecoAcaoService precoAcaoService;
    private PrecoFiiService precoFiiService;

    public CronJobConfig(PrecoAcaoService precoAcaoService,  PrecoFiiService precoFiiService) {
        this.precoAcaoService = precoAcaoService;
        this.precoFiiService = precoFiiService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updatePrecoAcoes() {
        precoAcaoService.updatePrecoAcoes();
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void updatePrecoFii() {
        precoFiiService.updatePrecoFiis();
    }
}
