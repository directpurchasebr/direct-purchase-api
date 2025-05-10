package br.com.directpurchase.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Negocio;
import br.com.directpurchase.entity.Perfil;
import br.com.directpurchase.entity.Pessoa;
import br.com.directpurchase.entity.PessoaBanco;
import br.com.directpurchase.entity.PessoaEndereco;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.repository.CompradorRepository;
import br.com.directpurchase.repository.FornecedorRespository;
import br.com.directpurchase.repository.NegocioRepository;
import br.com.directpurchase.repository.PerfilRepository;
import br.com.directpurchase.repository.PessoaBancoRepository;
import br.com.directpurchase.repository.PessoaEnderecoRepository;
import br.com.directpurchase.repository.PessoaRepository;
import br.com.directpurchase.repository.ProdutoRepository;
import br.com.directpurchase.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Repository
public class EntitysFetchDao {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private FornecedorRespository fornecedorRespository;

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private NegocioRepository negocioRepository;

    @Autowired
    private PessoaEnderecoRepository pessoaEnderecoRepository;

    @Autowired
    private PessoaBancoRepository pessoaBancoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    private <T> T findEntityById(Optional<T> entityOpt, String entityName, Integer entityId) {
        return entityOpt
                .orElseThrow(() -> new EntityNotFoundException(entityName + " não encontrado com ID: " + entityId));
    }

    public Usuario findUsuarioById(Integer usuarioId) {
        return findEntityById(usuarioRepository.findById(usuarioId), "Usuario", usuarioId);
    }

    public Perfil getPerfil(Integer perfilId) {
        if (perfilId != null) {
            return findEntityById(perfilRepository.findById(perfilId), "Perfil", perfilId);
        }
        return perfilRepository.buscaRegular().stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Nenhum perfil regular encontrado"));
    }

    public Fornecedor findFornecedorById(Integer fornecedorId) {
        return findEntityById(fornecedorRespository.findById(fornecedorId), "Fornecedor", fornecedorId);
    }

    public Comprador findCompradorById(Integer compradorId) {
        return findEntityById(compradorRepository.findById(compradorId), "Comprador", compradorId);
    }

    public Produto findProdutoById(Integer produtoId) {
        return findEntityById(produtoRepository.findById(produtoId), "Produto", produtoId);
    }

    public Negocio findNegocioById(Integer negocioId) {
        return findEntityById(negocioRepository.findById(negocioId), "Negocio", negocioId);
    }

    public PessoaEndereco findPessoaEnderecoById(Integer pessoaEnderecoId) {
        return findEntityById(pessoaEnderecoRepository.findById(pessoaEnderecoId), "PessoaEndereco", pessoaEnderecoId);
    }

    public PessoaBanco findPessoaBancoById(Integer pessoaBancoId) {
        return findEntityById(pessoaBancoRepository.findById(pessoaBancoId), "PessoaBanco", pessoaBancoId);
    }

    public Pessoa findPessoaById(Integer pessoaId) {
        return findEntityById(pessoaRepository.findById(pessoaId), "Pessoa", pessoaId);
    }
    
}
