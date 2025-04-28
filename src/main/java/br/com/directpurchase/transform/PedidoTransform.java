package br.com.directpurchase.transform;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.directpurchase.dao.EntitysFetchDao;
import br.com.directpurchase.dto.PedidoDto;
import br.com.directpurchase.dto.ProdutoPedidoDto;
import br.com.directpurchase.entity.Pedido;
import br.com.directpurchase.entity.PedidoProduto;

@Component
public class PedidoTransform {

    private final EntitysFetchDao entitysFetchDao;
    private final UsuarioTransform usuarioTransform;
    private final ProdutoTransform produtoTransform;

    public PedidoTransform(EntitysFetchDao entitysFetchDao, UsuarioTransform usuarioTransform,
            ProdutoTransform produtoTransform) {
        this.entitysFetchDao = entitysFetchDao;
        this.usuarioTransform = usuarioTransform;
        this.produtoTransform = produtoTransform;
    }

    public Pedido transform(PedidoDto body, Integer usuarioId) {
        var usuario = entitysFetchDao.findUsuarioById(usuarioId);
        var comprador = entitysFetchDao.findCompradorById(body.getComprador().getCompradorId());

        Pedido pedido = Pedido.builder()
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

    public PedidoProduto transform(ProdutoPedidoDto body, Pedido pedido) {
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

    public PedidoDto transform(Pedido entity) {
        return PedidoDto.builder()
                .pedidoId(entity.getPedidoId())
                .codigoPedido(entity.getCodigoPedido())
                .comprador(usuarioTransform.transform(entity.getComprador()))
                .produtos(entity.getPedidoProdutos().stream().map(this::transformA).toList())
                .valorTotal(entity.getPrecoTotal())
                .observacao("")
                .status("")
                .build();
    }

    public ProdutoPedidoDto transformA(PedidoProduto entity) {
        return ProdutoPedidoDto.builder()
                .produto(produtoTransform.transform(entity.getProduto()))
                .fornecedor(null)
                .codigo(null)
                .descricaoProduto(null)
                .unidade(null)
                .quantidade(entity.getQuantidade())
                .preco(entity.getPreco())
                .precoTotal(entity.getPrecoOriginal())
                .build();
    }
}
