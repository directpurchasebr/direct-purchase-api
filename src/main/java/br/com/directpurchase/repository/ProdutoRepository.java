package br.com.directpurchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.directpurchase.entity.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer> {

	@Query("select p from Produto p where p.codigo = :codigo and p.fornecedor.fornecedorId = :fornecedorId ")
	public List<Produto> buscaProdutoMain(@Param("codigo") String codigo, @Param("fornecedorId") Integer fornecedorId);

	@Query("select p from Produto p where UPPER(TRIM(p.descricao)) like CONCAT(UPPER(:descricao), '%') "
	        + "and p.fornecedor.fornecedorId IN (:fornecedores) ")
	public List<Produto> buscaPorDescricao(@Param("descricao") String descricao,
	        @Param("fornecedores") List<Integer> fornecedores);

	@Query("select p from Produto p where p.fornecedor.fornecedorId = :fornecedorId ")
	public List<Produto> buscaPorFornecedor(@Param("fornecedorId") Integer fornecedorId);
}
