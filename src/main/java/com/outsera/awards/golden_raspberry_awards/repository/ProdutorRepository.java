package com.outsera.awards.golden_raspberry_awards.repository;

import com.outsera.awards.golden_raspberry_awards.model.Produtor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
    Optional<Produtor> findByNome(String nome);

}
