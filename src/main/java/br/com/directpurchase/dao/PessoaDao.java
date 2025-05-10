package br.com.directpurchase.dao;

import org.springframework.stereotype.Repository;

import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.repository.CompradorRepository;
import br.com.directpurchase.repository.FornecedorRespository;
import jakarta.transaction.Transactional;

@Repository
public class PessoaDao {

    private final FornecedorRespository fornecedorRespository;
    private final CompradorRepository compradorRepository;

    public PessoaDao(FornecedorRespository fornecedorRespository, CompradorRepository compradorRepository) {
        this.fornecedorRespository = fornecedorRespository;
        this.compradorRepository = compradorRepository;
    }

    @Transactional
    public void salvar(Fornecedor entity) {
        fornecedorRespository.save(entity);
    }

    @Transactional
    public void salvar(Comprador entity) {
        compradorRepository.save(entity);
    }

}
