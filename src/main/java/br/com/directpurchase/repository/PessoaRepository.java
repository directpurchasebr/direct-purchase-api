package br.com.directpurchase.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.directpurchase.entity.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Integer> { }