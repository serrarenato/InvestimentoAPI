package org.example.investimentoapi.config;

import org.example.investimentoapi.service.PrecoAcaoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CronJobConfig {

    private final PrecoAcaoService precoAcaoService;

    public CronJobConfig(PrecoAcaoService precoAcaoService) {
        this.precoAcaoService = precoAcaoService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updatePrecoAcoes() {
        precoAcaoService.updatePrecoAcoes();
    }
}
