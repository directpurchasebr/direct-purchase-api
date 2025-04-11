package br.com.directpurchase.transform;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.directpurchase.dao.EntitysFetchDao;
import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.entity.Pedido;
import br.com.directpurchase.entity.PedidoProduto;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.request.NovoPedidoRequest;
import br.com.directpurchase.request.ProdutoRequest;

@Component
public class PedidoTransform {

    @Autowired
    private EntitysFetchDao entitysFetchDao;

    public Pedido transform(NovoPedidoRequest body, Integer usuarioId) {

        Usuario usuario = entitysFetchDao.findUsuariorById(usuarioId);
        Comprador comprador = entitysFetchDao.findCompradorById(body.getComprado().getCompradorId())

        return Pedido.builder()
                // FIXME: o codigo deve seguir o pedidoId porem com um prefixo 1000000001 por exemplo
                .codigoPedido(body.getCodigoPedido())
                .comprador(comprador)
                .dataPedido(LocalDateTime.now())
                .precoTotal(body.getValorTotal())
                .pedidoProdutos(null)
                .usuario(usuario)
                .build();
    }

    public PedidoProduto transform(ProdutoRequest body, Pedido pedido) {

        Produto produto = entitysFetchDao.findProdutoById(body.getProduto().getProdutoId());

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
