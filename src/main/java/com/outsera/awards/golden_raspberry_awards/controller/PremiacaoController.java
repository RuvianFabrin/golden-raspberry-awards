package com.outsera.awards.golden_raspberry_awards.controller;

import com.outsera.awards.golden_raspberry_awards.dto.RespostaPremiacaoDTO;
import com.outsera.awards.golden_raspberry_awards.service.PremiacaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/awards")
public class PremiacaoController {
    private final PremiacaoService premiacaoService;

    public PremiacaoController(PremiacaoService premiacaoService) {
        this.premiacaoService = premiacaoService;
    }

    @GetMapping("/intervals")
    public RespostaPremiacaoDTO buscarIntervalosPremiacao() {
        return premiacaoService.buscarIntervalosPremiacao();
    }
}
