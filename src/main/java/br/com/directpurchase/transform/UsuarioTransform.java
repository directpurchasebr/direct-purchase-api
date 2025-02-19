package br.com.directpurchase.transform;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Perfil;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.repository.CompradorRepository;
import br.com.directpurchase.repository.FornecedorRespository;
import br.com.directpurchase.repository.PerfilRepository;
import br.com.directpurchase.repository.UsuarioRepository;
import br.com.directpurchase.request.CompradorRequest;
import br.com.directpurchase.request.FornecedorRequest;
import br.com.directpurchase.request.UsuarioRequest;
import br.com.directpurchase.util.PasswordUtil;

@Component
public class UsuarioTransform {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private FornecedorRespository fornecedorRespository;

	@Autowired
	private CompradorRepository compradorRepository;

	public Usuario transform(UsuarioRequest bean) {

		Usuario entity = null;
		if (bean.getUsuarioId() != null) {
			entity = usuarioRepository.findById(bean.getUsuarioId()).get();
		} else {
			entity = new Usuario();
			entity.setUsuarioId(null);
			final String senhaEnc = PasswordUtil.encryptPassword(bean.getSenha());
			entity.setSenha(senhaEnc);
			entity.setEmail(bean.getEmail());
			entity.setLogin(bean.getLogin());
			entity.setDataCadastro(LocalDateTime.now());
		}

		entity.setNome(bean.getNome());
		entity.setDataNascimento(bean.getDataNascimento() == null ? null : bean.getDataNascimento().atStartOfDay());
		entity.setDataModif(LocalDateTime.now());
		entity.setIndEstoque(bean.getIndEstoque());

		Perfil perfil = getPerfil(bean.getPerfilId());
		entity.setPerfil(perfil);

		List<Fornecedor> fornecedores = bean.getFornecedores().stream().map(f -> findFornecedorById(f))
				.collect(Collectors.toList());
		entity.setFornecedores(fornecedores);

		List<Comprador> compradores = bean.getCompradores().stream().map(c -> findCompradorById(c))
				.collect(Collectors.toList());
		entity.setCompradores(compradores);

		return entity;
	}

	private Perfil getPerfil(Integer perfilId) {
		if (perfilId == null) {
			List<Perfil> list = perfilRepository.buscaRegular();
			return list.stream().findFirst().get();
		} else {
			Optional<Perfil> perfil = perfilRepository.findById(perfilId);
			return perfil.get();
		}
	}

	private Fornecedor findFornecedorById(FornecedorRequest bean) {
		Optional<Fornecedor> optional = fornecedorRespository.findById(bean.getFornecedorId());
		return optional.get();
	}

	private Comprador findCompradorById(CompradorRequest bean) {
		Optional<Comprador> optional = compradorRepository.findById(bean.getCompradorId());
		return optional.get();
	}

}
