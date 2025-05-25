package br.com.directpurchase.dao;

import org.springframework.stereotype.Repository;

import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Pessoa;
import br.com.directpurchase.repository.CompradorRepository;
import br.com.directpurchase.repository.FornecedorRespository;
import br.com.directpurchase.repository.PessoaRepository;
import jakarta.transaction.Transactional;

@Repository
public class PessoaDao {

    private final PessoaRepository pessoaRepository;
    private final FornecedorRespository fornecedorRespository;
    private final CompradorRepository compradorRepository;

    public PessoaDao(PessoaRepository pessoaRepository, FornecedorRespository fornecedorRespository,
            CompradorRepository compradorRepository) {
        this.pessoaRepository = pessoaRepository;
        this.fornecedorRespository = fornecedorRespository;
        this.compradorRepository = compradorRepository;
    }

    @Transactional
    public void salvar(Pessoa entity) {
        pessoaRepository.save(entity);
        pessoaRepository.flush();
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
