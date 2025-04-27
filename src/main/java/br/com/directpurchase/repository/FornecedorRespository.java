package br.com.directpurchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.directpurchase.entity.Fornecedor;

public interface FornecedorRespository extends JpaRepository<Fornecedor, Integer> {

	@Query("select f from Fornecedor f where f.fornecedorId IN (:fornecedores) ")
	public List<Fornecedor> buscaPorFornecedores(@Param("fornecedores") List<Integer> fornecedores);

}
