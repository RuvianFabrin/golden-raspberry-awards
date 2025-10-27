package com.outsera.awards.golden_raspberry_awards.config;

import com.outsera.awards.golden_raspberry_awards.model.Filme;
import com.outsera.awards.golden_raspberry_awards.model.Produtor;
import com.outsera.awards.golden_raspberry_awards.repository.FilmeRepository;
import com.outsera.awards.golden_raspberry_awards.repository.ProdutorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.ClassPathResource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CargaBancoDados implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(CargaBancoDados.class);
    private static final String CAMINHO_CSV = "movielist.csv";
    private static final String DELIMITADOR_CSV = ";";
    private final FilmeRepository filmeRepository;
    private final ProdutorRepository produtorRepository;

    public CargaBancoDados(FilmeRepository filmeRepository, ProdutorRepository produtorRepository) {
        this.filmeRepository = filmeRepository;
        this.produtorRepository = produtorRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Importando o CSV para o banco de dados: {}", CAMINHO_CSV);
        try (InputStream is = new ClassPathResource(CAMINHO_CSV).getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            if (line == null) {
                log.warn("O arquivo CSV está vazio.");
                return;
            }
            while ((line = reader.readLine()) != null) {
                try {
                    processCsvLine(line);
                } catch (Exception e) {
                    log.error("Erro na linha '{}' do CSV. Erro: {}", line, e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Falha ao ler o arquivo CSV: {}", CAMINHO_CSV, e);
            throw new RuntimeException("Não foi possível carregar o arquivo CSV.", e);
        }
        log.info("Importação do CSV para o banco de dados concluída.");
    }

    private void processCsvLine(String line) {
        String[] fields = line.split(Pattern.quote(DELIMITADOR_CSV), -1);
        if (fields.length < 5) {
            log.warn("Linha do CSV está inválida, têm poucos campos: {}", line);
            return;
        }
        Integer ano;
        try {
            ano = Integer.parseInt(fields[0].trim());
        } catch (NumberFormatException e) {
            log.warn("Na linha '{}' o ano não é um número.", line);
            return;
        }
        String titulo = fields[1].trim();
        String estudios = fields[2].trim();
        String produtoresString = fields[3].trim();
        boolean vencedor = "yes".equalsIgnoreCase(fields[4].trim());
        Set<Produtor> produtores = ajustaSeparaProdutores(produtoresString);
        Filme filme = new Filme();
        filme.setAno(ano);
        filme.setTitulo(titulo);
        filme.setEstudios(estudios);
        filme.setVencedor(vencedor);
        filme.setProdutores(produtores);
        filmeRepository.save(filme);
    }

    private Set<Produtor> ajustaSeparaProdutores(String stringProdutores) {
        if (stringProdutores == null || stringProdutores.trim().isEmpty()) {
            return new HashSet<>();
        }
        String[] nomeProdutores = stringProdutores.replace(" and ", ",").split(",");
        return Arrays.stream(nomeProdutores).map(String::trim).filter(name -> !name.isEmpty())
                .map(this::procuraCriaProdutor).collect(Collectors.toSet());
    }

    private Produtor procuraCriaProdutor(String name) {
        return produtorRepository.findByNome(name).orElseGet(() -> produtorRepository.save(new Produtor(name)));
    }
}
