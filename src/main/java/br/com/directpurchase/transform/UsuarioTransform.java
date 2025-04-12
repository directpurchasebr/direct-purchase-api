package br.com.directpurchase.transform;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.dao.EntitysFetchDao;
import br.com.directpurchase.dto.CompradorDto;
import br.com.directpurchase.dto.FornecedorDto;
import br.com.directpurchase.dto.PerfilDto;
import br.com.directpurchase.dto.UsuarioDto;
import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Perfil;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.util.PasswordUtil;

@Component
public class UsuarioTransform {

	private final EntitysFetchDao entitysFetchDao;

	public UsuarioTransform(EntitysFetchDao entitysFetchDao) {
		this.entitysFetchDao = entitysFetchDao;
	}

	public UsuarioPayload fetchUsuarioPayload(Integer usuarioId) {
		Usuario entity = entitysFetchDao.findUsuarioById(usuarioId);

		UsuarioDto transform = transform(entity);

		List<Integer> compradores = transform.getCompradores().stream()
				.map(CompradorDto::getCompradorId)
				.collect(Collectors.toList());

		List<Integer> fornecedores = transform.getFornecedores().stream()
				.map(FornecedorDto::getFornecedorId)
				.collect(Collectors.toList());

		return UsuarioPayload.builder()
				.usuarioId(transform.getUsuarioId())
				.nome(transform.getNome())
				.email(transform.getEmail())
				.login(transform.getLogin())
				.senha(transform.getSenha())
				.indEstoque(transform.getIndEstoque())
				.perfil(transform.getPerfil().getDescricao())
				.status(null)
				.fornecedores(fornecedores)
				.compradores(compradores)
				.build();
	}

	public UsuarioDto fetchUsuarioDto(Integer usuarioId) {
		Usuario entity = entitysFetchDao.findUsuarioById(usuarioId);
		return transform(entity);
	}

	public UsuarioDto transform(Usuario entity) {
		PerfilDto perfil = transform(entity.getPerfil());
		List<CompradorDto> compradores = entity.getCompradores().stream()
				.map(this::transform)
				.collect(Collectors.toList());
		List<FornecedorDto> fornecedores = entity.getFornecedores().stream()
				.map(this::transform)
				.collect(Collectors.toList());

		return UsuarioDto.builder()
				.usuarioId(entity.getUsuarioId())
				.nome(entity.getNome())
				.email(entity.getEmail())
				.login(entity.getLogin())
				// .senha(entity.getSenha()) **Nunca enviar senha para o front-end**
				.indEstoque(entity.getIndEstoque())
				.dataNascimento(entity.getDataNascimento() != null ? entity.getDataNascimento().toLocalDate() : null)
				.perfil(perfil)
				.fornecedores(fornecedores)
				.compradores(compradores)
				.build();
	}

	public Usuario transform(UsuarioDto bean) {
		Usuario entity = Optional.ofNullable(bean.getUsuarioId())
				.map(id -> entitysFetchDao.findUsuarioById(id))
				.orElseGet(Usuario::new); // Cria um novo objeto Usuario se o id for nulo

		if (entity.getUsuarioId() == null) {
			entity.setSenha(Optional.ofNullable(bean.getSenha())
					.map(PasswordUtil::encryptPassword)
					.orElse("123456")); // Senha padrão de primeiro acesso
			entity.setDataCadastro(LocalDateTime.now());
		}

		entity.setNome(bean.getNome());
		entity.setDataNascimento(bean.getDataNascimento() != null ? bean.getDataNascimento().atStartOfDay() : null);
		entity.setDataModif(LocalDateTime.now());
		entity.setIndEstoque(bean.getIndEstoque());

		Perfil perfil = Optional.ofNullable(bean.getPerfil())
				.map(p -> entitysFetchDao.getPerfil(p.getPerfilId())).orElse(null);
		entity.setPerfil(perfil);

		List<Fornecedor> fornecedores = Optional.ofNullable(bean.getFornecedores())
				.map(f -> f.stream().map(f -> entitysFetchDao.findFornecedorById(f.getFornecedorId()))
						.collect(Collectors.toList()))
				.orElse(null);
		entity.setFornecedores(fornecedores);

		List<Comprador> compradores = Optional.ofNullable(bean.getCompradores())
				.map(c -> c.stream()
						.map(co -> entitysFetchDao.findCompradorById(co.getCompradorId()))
						.collect(Collectors.toList()))
				.orElse(null);
		entity.setCompradores(compradores);

		return entity;
	}

	public FornecedorDto transform(Fornecedor entity) {
		return FornecedorDto.builder()
				.fornecedorId(entity.getFornecedorId())
				.codigo(entity.getCodigo())
				.nome(entity.getNome())
				.layoutExcel(entity.getLayoutExcel())
				.build();
	}

	public CompradorDto transform(Comprador entity) {
		return CompradorDto.builder()
				.compradorId(entity.getCompradorId())
				.codigo(entity.getCodigo())
				.nome(entity.getNome())
				.negocioId(entity.getNegocio().getNegocioId())
				.build();
	}

	public PerfilDto transform(Perfil entity) {
		return PerfilDto.builder()
				.perfilId(entity.getPerfilId())
				.descricao(entity.getDescricao())
				.build();
	}
}
