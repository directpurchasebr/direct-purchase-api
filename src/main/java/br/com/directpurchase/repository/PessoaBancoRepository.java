package br.com.directpurchase.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.directpurchase.entity.PessoaBanco;

public interface PessoaBancoRepository extends CrudRepository<PessoaBanco, Integer> { }