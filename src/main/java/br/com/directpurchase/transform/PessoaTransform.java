package br.com.directpurchase.transform;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.directpurchase.dao.EntitysFetchDao;
import br.com.directpurchase.dto.CompradorDto;
import br.com.directpurchase.dto.FornecedorDto;
import br.com.directpurchase.dto.PessoaBancoDto;
import br.com.directpurchase.dto.PessoaDto;
import br.com.directpurchase.dto.PessoaEnderecoDto;
import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Pessoa;
import br.com.directpurchase.entity.PessoaBanco;
import br.com.directpurchase.entity.PessoaEndereco;

@Component
public class PessoaTransform {

    private final EntitysFetchDao entitysFetchDao;

    public PessoaTransform(EntitysFetchDao entitysFetchDao) {
        this.entitysFetchDao = entitysFetchDao;
    }

    public Fornecedor transform(FornecedorDto dto) {
        Fornecedor entity = entitysFetchDao.findFornecedorById(dto.getFornecedorId());
        if (entity == null) {
            entity = new Fornecedor();
        }
        Pessoa pessoa = transformPessoa(dto);
        entity.setLayoutExcel(dto.getLayoutExcel());
        entity.setPessoa(pessoa);
        return entity;
    }

    public Comprador transform(CompradorDto dto) {
        Comprador entity = entitysFetchDao.findCompradorById(dto.getCompradorId());
        if (entity == null) {
            entity = new Comprador();
        }
        Pessoa pessoa = transformPessoa(dto);
        entity.setPessoa(pessoa);
        return entity;
    }

    public Pessoa transformPessoa(PessoaDto dto) {
        Pessoa entity = null;
        if (dto.getPessoaId() == null) {
            entity = new Pessoa();
            entity.setDataCadastro(LocalDateTime.now());
        } else {
            entity = entitysFetchDao.findPessoaById(dto.getPessoaId());
        }
        entity.setNegocio(entitysFetchDao.findNegocioById(dto.getNegocioId()));
        entity.setCodigo(dto.getCodigo());
        entity.setNome(dto.getNome());
        entity.setNomeFantasia(dto.getNomeFantasia());
        entity.setCpf(dto.getCpf());
        entity.setCnpj(dto.getCnpj());
        entity.setInscricaoEstadual(dto.getInscricaoEstadual());
        entity.setInscricaoMunicipal(dto.getInscricaoMunicipal());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity.setSite(dto.getSite());
        entity.setResponsavel(dto.getResponsavel());
        entity.setTelefoneResponsavel(dto.getTelefoneResponsavel());
        entity.setObservacoes(dto.getObservacoes());
        entity.setAtivo(Boolean.TRUE);
        entity.setDataModif(LocalDateTime.now());
        entity.setEnderecos(dto.getEnderecos().stream().map(this::transform).toList());
        entity.setDadosBancarios(dto.getBancos().stream().map(this::transform).toList());
        return entity;
    }

    public PessoaEndereco transform(PessoaEnderecoDto dto) {
        PessoaEndereco entity = entitysFetchDao.findPessoaEnderecoById(dto.getPessoaEnderecoId());
        if (entity == null) {
            entity = new PessoaEndereco();
        }
        entity.setBairro(null);
        entity.setCep(dto.getCep());
        entity.setCidade(dto.getCidade());
        entity.setComplemento(dto.getComplemento());
        entity.setEstado(dto.getEstado());
        entity.setLogradouro(dto.getLogradouro());
        entity.setNumero(dto.getNumero());
        return entity;
    }

    public PessoaBanco transform(PessoaBancoDto dto) {
        PessoaBanco entity = entitysFetchDao.findPessoaBancoById(dto.getPessoaBancoId());
        if (entity == null) {
            entity = new PessoaBanco();
        }
        entity.setBanco(dto.getBanco());
        entity.setAgencia(dto.getAgencia());
        entity.setConta(dto.getConta());
        entity.setTipoConta(dto.getTipoConta());
        entity.setTitular(dto.getTitular());
        entity.setCnpjTitular(dto.getCnpjTitular());
        return entity;
    }

    public FornecedorDto transform(Fornecedor entity) {
        return FornecedorDto.builder()
                .fornecedorId(entity.getFornecedorId())
                .layoutExcel(entity.getLayoutExcel())
                .pessoaId(entity.getPessoa().getPessoaId())
                .negocioId(entity.getPessoa().getNegocio().getNegocioId())
                .codigo(entity.getPessoa().getCodigo())
                .nome(entity.getPessoa().getNome())
                .nomeFantasia(entity.getPessoa().getNomeFantasia())
                .cpf(entity.getPessoa().getCpf())
                .cnpj(entity.getPessoa().getCnpj())
                .inscricaoEstadual(entity.getPessoa().getInscricaoEstadual())
                .inscricaoMunicipal(entity.getPessoa().getInscricaoMunicipal())
                .telefone(entity.getPessoa().getTelefone())
                .email(entity.getPessoa().getEmail())
                .site(entity.getPessoa().getSite())
                .responsavel(entity.getPessoa().getResponsavel())
                .telefoneResponsavel(entity.getPessoa().getTelefoneResponsavel())
                .observacoes(entity.getPessoa().getObservacoes())
                .enderecos(entity.getPessoa().getEnderecos().stream().map(this::transform).toList())
                .bancos(entity.getPessoa().getDadosBancarios().stream().map(this::transform).toList())
                .build();
    }

    public CompradorDto transform(Comprador entity) {
        return CompradorDto.builder()
                .compradorId(entity.getCompradorId())
                .pessoaId(entity.getPessoa().getPessoaId())
                .negocioId(entity.getPessoa().getNegocio().getNegocioId())
                .codigo(entity.getPessoa().getCodigo())
                .nome(entity.getPessoa().getNome())
                .nomeFantasia(entity.getPessoa().getNomeFantasia())
                .cpf(entity.getPessoa().getCpf())
                .cnpj(entity.getPessoa().getCnpj())
                .inscricaoEstadual(entity.getPessoa().getInscricaoEstadual())
                .inscricaoMunicipal(entity.getPessoa().getInscricaoMunicipal())
                .telefone(entity.getPessoa().getTelefone())
                .email(entity.getPessoa().getEmail())
                .site(entity.getPessoa().getSite())
                .responsavel(entity.getPessoa().getResponsavel())
                .telefoneResponsavel(entity.getPessoa().getTelefoneResponsavel())
                .observacoes(entity.getPessoa().getObservacoes())
                .enderecos(entity.getPessoa().getEnderecos().stream().map(e -> transform(e)).toList())
                .bancos(entity.getPessoa().getDadosBancarios().stream().map(b -> transform(b)).toList())
                .build();
    }

    public PessoaDto transform(Pessoa entity) {
        return PessoaDto.builder()
                .pessoaId(entity.getPessoaId())
                .negocioId(entity.getNegocio().getNegocioId())
                .codigo(entity.getCodigo())
                .nome(entity.getNome())
                .nomeFantasia(entity.getNomeFantasia())
                .cpf(entity.getCpf())
                .cnpj(entity.getCnpj())
                .inscricaoEstadual(entity.getInscricaoEstadual())
                .inscricaoMunicipal(entity.getInscricaoMunicipal())
                .telefone(entity.getTelefone())
                .email(entity.getEmail())
                .site(entity.getSite())
                .responsavel(entity.getResponsavel())
                .telefoneResponsavel(entity.getTelefoneResponsavel())
                .observacoes(entity.getObservacoes())
                .enderecos(entity.getEnderecos().stream().map(this::transform).toList())
                .bancos(entity.getDadosBancarios().stream().map(this::transform).toList())
                .build();
    }

    public PessoaEnderecoDto transform(PessoaEndereco entity) {
        return PessoaEnderecoDto.builder()
                .pessoaEnderecoId(entity.getPessoaEnderecoId())
                .logradouro(entity.getLogradouro())
                .numero(entity.getNumero())
                .complemento(entity.getComplemento())
                .bairro(entity.getBairro())
                .cidade(entity.getCidade())
                .estado(entity.getEstado())
                .cep(entity.getCep())
                .build();
    }

    public PessoaBancoDto transform(PessoaBanco entity) {
        return PessoaBancoDto.builder()
                .pessoaBancoId(entity.getPessoaBancoId())
                .banco(entity.getBanco())
                .agencia(entity.getAgencia())
                .conta(entity.getConta())
                .tipoConta(entity.getTipoConta())
                .titular(entity.getTitular())
                .cnpjTitular(entity.getCnpjTitular())
                .build();
    }
}
