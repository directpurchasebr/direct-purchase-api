package br.com.directpurchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.directpurchase.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	@Query("select p from Produto p where p.usuario.usuarioId = :usuarioId ")
	public List<Produto> buscaProdutoByUsuario(@Param("usuarioId") Integer usuarioId);

	@Query("select p from Produto p where p.usuario.usuarioId = :usuarioId " +
			"and p.codigo = :codigo and p.fornecedor.fornecedorId = :fornecedorId ")
	public List<Produto> buscaProdutoMain(
			@Param("usuarioId") Integer usuarioId,
			@Param("codigo") String codigo,
			@Param("fornecedorId") Integer fornecedorId);

	@Query("select p from Produto p where p.usuario.usuarioId = :usuarioId " +
			"AND UPPER(TRIM(p.descricao)) LIKE CONCAT(UPPER(COALESCE(:descricao, '')), '%') " +
			"and p.fornecedor.fornecedorId IN (:fornecedores) ")
	public List<Produto> buscar(
			@Param("usuarioId") Integer usuarioId,
			@Param("descricao") String descricao,
			@Param("fornecedores") List<Integer> fornecedores);

	@Query("select p from Produto p where p.usuario.usuarioId = :usuarioId " +
			"and p.fornecedor.fornecedorId = :fornecedorId ")
	public List<Produto> buscaPorFornecedor(
			@Param("usuarioId") Integer usuarioId,
			@Param("fornecedorId") Integer fornecedorId);
}
