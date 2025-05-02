package br.com.directpurchase.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.directpurchase.entity.Pedido;
import br.com.directpurchase.repository.PedidoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PedidoDao {

    private final PedidoRepository pedidoRepository;
    private final EntityManager roEM;

    public PedidoDao(PedidoRepository pedidoRepository, EntityManager roEM) {
        this.pedidoRepository = pedidoRepository;
        this.roEM = roEM;
    }

    public void salvar(Pedido entity) {
        pedidoRepository.save(entity);
        pedidoRepository.flush();
    }

    public List<Pedido> buscar(Integer usuarioId, String codigoPedido,
            LocalDate dataPedido, Integer compradorId) {

        StringBuilder sql = new StringBuilder();
        sql.append("select p from Pedido p ");
        sql.append("where p.usuario.usuarioId = :usuarioId ");
        sql.append("and (:compradorId is null or p.comprador.compradorId = :compradorId) ");

        sql.append(
                "and (:codigoPedido is null or p.codigoPedido like concat('%', lpad(cast(:codigoPedido as string), 7, '0')))");

        if (dataPedido != null)
            sql.append("and p.dataPedido between :dataPedidoInicial and :dataPedidoFinal ");

        TypedQuery<Pedido> query = roEM.createQuery(sql.toString(), Pedido.class);
        query.setParameter("usuarioId", usuarioId);
        query.setParameter("compradorId", compradorId);
        query.setParameter("codigoPedido", codigoPedido);

        if (dataPedido != null) {
            query.setParameter("dataPedidoInicial", dataPedido.atStartOfDay());
            query.setParameter("dataPedidoFinal", dataPedido.atTime(LocalTime.MAX));
        }

        return query.getResultList();
    }

}
