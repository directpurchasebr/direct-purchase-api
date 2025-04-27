package br.com.directpurchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.directpurchase.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query(value = "SELECT MAX(CAST(SUBSTRING(codigo_pedido FROM POSITION('-' IN codigo_pedido) + 1) AS INTEGER)) FROM pedido WHERE usuario_id = :usuarioId", nativeQuery = true)
    Integer buscarUltimoCodigoPorUsuario(@Param("usuarioId") Integer usuarioId);

}
