package br.com.directpurchase.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.directpurchase.entity.Pedido;
import br.com.directpurchase.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PedidoDao {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void salvar(Pedido entity) {
        pedidoRepository.save(entity);
    }

}
