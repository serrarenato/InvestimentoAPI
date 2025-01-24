package org.example.investimentoapi.service;

import org.example.investimentoapi.model.Fii;
import org.example.investimentoapi.repository.FiiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiiService {
    @Autowired
    private FiiRepository fiiRepository;

    public Fii saveFii(Fii fii) {
        return fiiRepository.save(fii);
    }

    public List<Fii> getAllFiis() {
        return fiiRepository.findAll();
    }
}
