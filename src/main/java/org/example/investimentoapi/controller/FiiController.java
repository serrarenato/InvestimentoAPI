package org.example.investimentoapi.controller;

import org.example.investimentoapi.model.Fii;
import org.example.investimentoapi.service.FiiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fiis")
public class FiiController {
    @Autowired
    private FiiService fiiService;

    @PostMapping
    public Fii registerFii(@RequestBody Fii fii) {
        return fiiService.saveFii(fii);
    }

    @GetMapping
    public List<Fii> getAllFiis() {
        return fiiService.getAllFiis();
    }
}
