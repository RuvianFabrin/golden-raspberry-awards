package com.outsera.awards.golden_raspberry_awards.service;

import com.outsera.awards.golden_raspberry_awards.dto.RespostaPremiacaoDTO;
import com.outsera.awards.golden_raspberry_awards.dto.IntervaloDTO;
import com.outsera.awards.golden_raspberry_awards.model.Filme;
import com.outsera.awards.golden_raspberry_awards.model.Produtor;
import com.outsera.awards.golden_raspberry_awards.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PremiacaoService {
    private final FilmeRepository filmeRepository;

    public PremiacaoService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public RespostaPremiacaoDTO buscarIntervalosPremiacao() {
        List<Filme> filmesVencedores = filmeRepository.findByVencedorTrue();
        Map<String, List<Integer>> produtorVencedor = new HashMap<>();
        for (Filme filme : filmesVencedores) {
            for (Produtor produtor : filme.getProdutores()) {
                produtorVencedor.computeIfAbsent(produtor.getNome(), k -> new ArrayList<>()).add(filme.getAno());
            }
        }
        List<IntervaloDTO> todosIntervalos = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> produtor : produtorVencedor.entrySet()) {
            String nomeProdutor = produtor.getKey();
            List<Integer> ano = produtor.getValue();
            if (ano.size() >= 2) {
                Collections.sort(ano);
                for (int i = 0; i < ano.size() - 1; i++) {
                    int previousWin = ano.get(i);
                    int followingWin = ano.get(i + 1);
                    int interval = followingWin - previousWin;
                    todosIntervalos.add(new IntervaloDTO(nomeProdutor, interval, previousWin, followingWin));
                }
            }
        }
        if (todosIntervalos.isEmpty()) {
            return new RespostaPremiacaoDTO(Collections.emptyList(), Collections.emptyList());
        }
        int minInterval = todosIntervalos.stream().mapToInt(IntervaloDTO::interval).min().getAsInt();
        int maxInterval = todosIntervalos.stream().mapToInt(IntervaloDTO::interval).max().getAsInt();
        List<IntervaloDTO> minList = todosIntervalos.stream().filter(i -> i.interval() == minInterval).toList();
        List<IntervaloDTO> maxList = todosIntervalos.stream().filter(i -> i.interval() == maxInterval).toList();
        return new RespostaPremiacaoDTO(minList, maxList);
    }
}
