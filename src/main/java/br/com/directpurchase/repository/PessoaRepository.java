package br.com.directpurchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.directpurchase.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
}