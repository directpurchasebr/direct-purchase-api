package br.com.directpurchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.directpurchase.entity.Comprador;

public interface CompradorRepository extends CrudRepository<Comprador, Integer> {

	@Query("select c from Comprador c where c.compradorId IN (:compradores) ")
	public List<Comprador> buscaPorCompradores(@Param("compradores") List<Integer> compradores);

}
