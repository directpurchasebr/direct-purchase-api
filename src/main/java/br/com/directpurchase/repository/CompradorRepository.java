package br.com.directpurchase.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.directpurchase.entity.Comprador;

public interface CompradorRepository extends CrudRepository<Comprador, Integer> {

}
