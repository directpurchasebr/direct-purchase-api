package br.com.directpurchase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dao.PedidoDao;
import br.com.directpurchase.dto.PedidoDto;
import br.com.directpurchase.dto.PedidoFornecedorDto;
import br.com.directpurchase.dto.ProdutoPedidoDto;
import br.com.directpurchase.entity.Pedido;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.repository.PedidoRepository;
import br.com.directpurchase.request.ConsultaPedido;
import br.com.directpurchase.response.Status;
import br.com.directpurchase.transform.PedidoTransform;

@Service
public class PedidoService {

    private final AuthUtils authUtils;
    private final PedidoTransform pedidoTransform;
    private final PedidoDao pedidoDao;
    private final PedidoRepository pedidoRepository;

    public PedidoService(AuthUtils authUtils, PedidoTransform pedidoTransform, PedidoDao pedidoDao,
            PedidoRepository pedidoRepository) {
        this.authUtils = authUtils;
        this.pedidoTransform = pedidoTransform;
        this.pedidoDao = pedidoDao;
        this.pedidoRepository = pedidoRepository;
    }

    public Status salvarPedido(PedidoDto request) throws ValidationException {
        UsuarioPayload usuario = authUtils.getUsuarioLogado();
        Integer ultimoCodigo = pedidoRepository.buscarUltimoCodigoPorUsuario(usuario.getUsuarioId());
        int proximoCodigo = (ultimoCodigo != null ? ultimoCodigo : 0) + 1;

        Pedido pedido = pedidoTransform.transform(request, usuario.getUsuarioId());
        pedido.setCodigoPedido(String.format("USR%07d-%07d", pedido.getUsuario().getUsuarioId(), proximoCodigo));
        pedidoDao.salvar(pedido);

        request.setCodigoPedido(pedido.getCodigoPedido());
        return new Status(true, "Pedido salvo com sucesso", "", request);
    }

    public List<PedidoDto> listarPedidos() {
        UsuarioPayload usuario = authUtils.getUsuarioLogado();
        List<Pedido> entitys = pedidoRepository.listar(usuario.getUsuarioId());
        return entitys.stream().map(pedidoTransform::transform).toList();
    }

    public List<PedidoDto> consultarPedidos(ConsultaPedido request) throws ValidationException {
        UsuarioPayload usuario = authUtils.getUsuarioLogado();
        List<Pedido> entitys = pedidoDao.buscar(
                usuario.getUsuarioId(),
                request.getCodigoPedido(),
                request.getDataPedido(),
                request.getCompradorId());

        return entitys.stream().map(pedidoTransform::transform).toList();
    }

    public List<PedidoFornecedorDto> listarPedidosPorFornecedor() {
        UsuarioPayload usuario = authUtils.getUsuarioLogado();
        List<Pedido> entitys = pedidoRepository.listar(usuario.getUsuarioId());

        List<PedidoFornecedorDto> resultado = new ArrayList<>();
        entitys.stream().map(pedidoTransform::transform).toList().forEach(p -> {
            Map<String, List<ProdutoPedidoDto>> agrupadoPorFornecedor = p.getProdutos().stream()
                    .collect(Collectors.groupingBy(pe -> pe.getFornecedor().getNome()));

            resultado.add(PedidoFornecedorDto.builder()
                    .pedidoId(p.getPedidoId())
                    .codigoPedido(p.getCodigoPedido())
                    .dataPedido(p.getDataPedido())
                    .comprador(p.getComprador())
                    .descricaoComprador(p.getComprador().getNome())
                    .valorTotal(p.getValorTotal())
                    .pedidosFornecedor(agrupadoPorFornecedor)
                    .status(p.getStatus())
                    .build());
        });

        return resultado;
    }

    public List<PedidoFornecedorDto> consultarPedidosPorFornecedor(ConsultaPedido request) throws ValidationException {
        UsuarioPayload usuario = authUtils.getUsuarioLogado();
        List<Pedido> entitys = pedidoDao.buscar(
                usuario.getUsuarioId(),
                request.getCodigoPedido(),
                request.getDataPedido(),
                request.getCompradorId());

        List<PedidoFornecedorDto> resultado = new ArrayList<>();
        entitys.stream().map(pedidoTransform::transform).toList().forEach(p -> {
            Map<String, List<ProdutoPedidoDto>> agrupadoPorFornecedor = p.getProdutos().stream()
                    .collect(Collectors.groupingBy(pe -> pe.getFornecedor().getNome()));

            resultado.add(PedidoFornecedorDto.builder()
                    .pedidoId(p.getPedidoId())
                    .codigoPedido(p.getCodigoPedido())
                    .dataPedido(p.getDataPedido())
                    .comprador(p.getComprador())
                    .descricaoComprador(p.getComprador().getNome())
                    .valorTotal(p.getValorTotal())
                    .pedidosFornecedor(agrupadoPorFornecedor)
                    .status(p.getStatus())
                    .build());
        });

        return resultado;
    }

}
