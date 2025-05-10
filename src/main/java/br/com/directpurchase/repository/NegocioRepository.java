package br.com.directpurchase.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.directpurchase.entity.Negocio;

public interface NegocioRepository extends CrudRepository<Negocio, Integer> {
}
