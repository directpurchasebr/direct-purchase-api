package br.com.directpurchase.service;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dao.PedidoDao;
import br.com.directpurchase.entity.Pedido;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.request.NovoPedidoRequest;
import br.com.directpurchase.response.Status;
import br.com.directpurchase.transform.PedidoTransform;

@Service
public class PedidoService {

    private final AuthUtils authUtils;
    private final PedidoTransform pedidoTransform;
    private final PedidoDao pedidoDao;


    public PedidoService(AuthUtils authUtils, PedidoTransform pedidoTransform, PedidoDao pedidoDao) {
        this.authUtils = authUtils;
        this.pedidoTransform = pedidoTransform;
        this.pedidoDao = pedidoDao;
    }

    public Status salvarPedido(NovoPedidoRequest request) throws ValidationException {
        UsuarioPayload usuario = authUtils.getUsuarioLogado();
        if (usuario == null) {
            throw new ValidationException("Usuário não está logado!");
        }

        Pedido pedido = pedidoTransform.transform(request, usuario.getUsuarioId());

        pedidoDao.salvar(pedido);
        
        return new Status(true, "Pedido salvo com sucesso", "", pedido);
    }
}
