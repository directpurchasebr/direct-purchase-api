package br.com.directpurchase.transform;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.directpurchase.dao.EntitysFetchDao;
import br.com.directpurchase.entity.Pedido;
import br.com.directpurchase.entity.PedidoProduto;
import br.com.directpurchase.request.NovoPedidoRequest;
import br.com.directpurchase.request.ProdutoRequest;

@Component
public class PedidoTransform {

    private final EntitysFetchDao entitysFetchDao;

    public PedidoTransform(EntitysFetchDao entitysFetchDao) {
        this.entitysFetchDao = entitysFetchDao;
    }

    public Pedido transform(NovoPedidoRequest body, Integer usuarioId) {

        var usuario = entitysFetchDao.findUsuarioById(usuarioId);
        var comprador = entitysFetchDao.findCompradorById(body.getComprador().getCompradorId());

        Pedido pedido = Pedido.builder()
                // FIXME: o codigo deve seguir o pedidoId porem com um prefixo 1000000001 por
                .comprador(comprador)
                .dataPedido(LocalDateTime.now())
                .precoTotal(body.getValorTotal())
                // FIXME: o indicador de estoque sera implementado futuramente (null)
                .usuario(usuario)
                .build();

        List<PedidoProduto> produtos = body.getProdutos().stream().map(p -> transform(p, pedido)).toList();
        pedido.setPedidoProdutos(produtos);

        return pedido;
    }

    public PedidoProduto transform(ProdutoRequest body, Pedido pedido) {

        var produto = entitysFetchDao.findProdutoById(body.getProduto().getProdutoId());

        // FIXME: deve registrar tambem o precoOriginal caso o usuario tenha alterado
        return PedidoProduto.builder()
                // FIXME: o indicador de estoque sera implementado futuramente
                .estoque(null)
                .pedido(pedido)
                .preco(body.getPreco())
                .produto(produto)
                .quantidade(body.getQuantidade())
                .precoOriginal(produto.getPreco())
                .build();
    }

}
