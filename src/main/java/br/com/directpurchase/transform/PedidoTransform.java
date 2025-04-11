package br.com.directpurchase.transform;

import org.springframework.beans.factory.annotation.Autowired;
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

        var usuario = entitysFetchDao.findUsuariorById(usuarioId);
        var comprador = entitysFetchDao.findCompradorById(body.getComprado().getCompradorId())

        return Pedido.builder()
                // FIXME: o codigo deve seguir o pedidoId porem com um prefixo 1000000001 por exemplo
                .codigoPedido(body.getCodigoPedido())
                .comprador(comprador)
                .dataPedido(LocalDateTime.now())
                .precoTotal(body.getValorTotal())

                .pedidoProdutos(body.getProdutos().stream()
                    .map(p -> transform(p, this)).collect(Collectors.toList())) 
                
                    // FIXME: o indicador de estoque sera implementado futuramente (null)
                .usuario(usuario)
                .build();
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
                .build();
    }

}
