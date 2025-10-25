package com.outsera.awards.golden_raspberry_awards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.outsera.awards.golden_raspberry_awards.model.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByVencedorTrue();

}
