package br.com.directpurchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.request.NovoPedidoRequest;

@Service
public class PedidoService {

    @Autowired
    private AuthUtils authUtils;

    public Object salvarPedido(NovoPedidoRequest request) {

        UsuarioPayload usuario = authUtils.getUsuarioLogado();

        return null;
    }

}
