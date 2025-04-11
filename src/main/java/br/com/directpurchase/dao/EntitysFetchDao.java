package br.com.directpurchase.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Perfil;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.repository.CompradorRepository;
import br.com.directpurchase.repository.FornecedorRespository;
import br.com.directpurchase.repository.PerfilRepository;
import br.com.directpurchase.repository.ProdutoRepository;
import br.com.directpurchase.repository.UsuarioRepository;

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

    public Usuario findUsuariorById(Integer usuarioId) {
        Optional<Usuario> optional = usuarioRepository.findById(usuarioId);
        return optional.get();
    }

    public Perfil getPerfil(Integer perfilId) {
        if (perfilId == null) {
            List<Perfil> list = perfilRepository.buscaRegular();
            return list.stream().findFirst().get();
        } else {
            Optional<Perfil> perfil = perfilRepository.findById(perfilId);
            return perfil.get();
        }
    }

    public Fornecedor findFornecedorById(Integer fornecedorId) {
        Optional<Fornecedor> optional = fornecedorRespository.findById(fornecedorId);
        return optional.get();
    }

    public Comprador findCompradorById(Integer compradorId) {
        Optional<Comprador> optional = compradorRepository.findById(compradorId);
        return optional.get();
    }

    public Produto findProdutoById(Integer produtoId) {
        Optional<Produto> optional = produtoRepository.findById(produtoId);
        return optional.get();
    }

}
