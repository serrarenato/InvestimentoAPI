package org.example.investimentoapi.controller;

import org.example.investimentoapi.service.PrecoAcaoService;

import org.example.investimentoapi.service.PrecoFiiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @Autowired
    private PrecoAcaoService precoAcaoService;
    @Autowired
    private PrecoFiiService precoFiiService;

//    @Autowired
//    private PrecoFiiService precoFiiService; TODO: fii
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/run-job")
    public String runJob() {
        precoAcaoService.updatePrecoAcoes();
        precoFiiService.updatePrecoFiis();

        return "Job executed";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/run-job-fii")
    public String runJobFii() {
        precoFiiService.updatePrecoFiis();

        return "Job executed";
    }
}
