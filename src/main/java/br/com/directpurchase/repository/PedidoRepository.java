package br.com.directpurchase.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.directpurchase.entity.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {

}
