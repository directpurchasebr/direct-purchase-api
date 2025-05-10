package br.com.directpurchase.dao;

import org.springframework.stereotype.Repository;

import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.repository.ProdutoRepository;

@Repository
public class ProdutoDao {

    private final ProdutoRepository produtoRepository;

    public ProdutoDao(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

     public void salvar(Produto entity) {
        produtoRepository.save(entity);
    }
}
