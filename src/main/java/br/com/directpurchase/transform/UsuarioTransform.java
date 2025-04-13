package br.com.directpurchase.transform;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.payload.UsuarioSessionPayload;
import br.com.directpurchase.dao.EntitysFetchDao;
import br.com.directpurchase.dto.CompradorDto;
import br.com.directpurchase.dto.FornecedorDto;
import br.com.directpurchase.dto.PerfilDto;
import br.com.directpurchase.dto.PessoaBancoDto;
import br.com.directpurchase.dto.PessoaDto;
import br.com.directpurchase.dto.PessoaEnderecoDto;
import br.com.directpurchase.dto.UsuarioDto;
import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Perfil;
import br.com.directpurchase.entity.Pessoa;
import br.com.directpurchase.entity.PessoaBanco;
import br.com.directpurchase.entity.PessoaEndereco;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.entity.UsuarioSession;
import br.com.directpurchase.util.PasswordUtil;

@Component
public class UsuarioTransform {

	@Value("${jwt.expiration}")
	private long expiration;

	private final EntitysFetchDao entitysFetchDao;

	public UsuarioTransform(EntitysFetchDao entitysFetchDao) {
		this.entitysFetchDao = entitysFetchDao;
	}

	public UsuarioPayload fetchUsuarioPayload(Integer usuarioId) {
		Usuario entity = entitysFetchDao.findUsuarioById(usuarioId);

		UsuarioDto transform = transform(entity);

		List<Integer> compradores = transform.getCompradores().stream()
				.map(CompradorDto::getCompradorId).toList();

		List<Integer> fornecedores = transform.getFornecedores().stream()
				.map(FornecedorDto::getFornecedorId).toList();

		UsuarioSessionPayload session = transformSessionPayload(entity);

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
				.session(session)
				.build();
	}

	public UsuarioSessionPayload transformSessionPayload(Usuario entity) {
		return entity.getSessoes().stream()
				.filter(s -> s.getIndSession())
				.map(s -> {
					return UsuarioSessionPayload.builder()
							.indSession(s.getIndSession())
							.usuarioSessionId(s.getUsuarioSessionId())
							.deviceId(s.getDeviceId())
							.userAgent(s.getUserAgent())
							.platform(s.getPlatform())
							.language(s.getLanguage())
							.tokenUser(s.getTokenUser())
							.tokenAccess(s.getTokenAccess())
							.dataModif(s.getDataModif())
							.dataSession(s.getDataSession())
							.build();
				}).findFirst().orElse(null);
	}

	public UsuarioDto fetchUsuarioDto(Integer usuarioId) {
		Usuario entity = entitysFetchDao.findUsuarioById(usuarioId);
		return transform(entity);
	}

	public UsuarioDto transform(Usuario entity) {
		PerfilDto perfil = transform(entity.getPerfil());
		List<CompradorDto> compradores = entity.getCompradores().stream().map(this::transform).toList();
		List<FornecedorDto> fornecedores = entity.getFornecedores().stream().map(this::transform).toList();

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

		List<Fornecedor> fornecedores = bean.getFornecedores().stream()
				.map(f -> entitysFetchDao.findFornecedorById(f.getFornecedorId())).toList();
		entity.setFornecedores(fornecedores);

		List<Comprador> compradores = bean.getCompradores().stream()
				.map(co -> entitysFetchDao.findCompradorById(co.getCompradorId())).toList();
		entity.setCompradores(compradores);

		return entity;
	}

	public UsuarioSession transform(Integer usuarioId, String deviceId, String userAgent, String platform,
			String language, String tokenUser, String tokenAccess) {

		Usuario usuario = entitysFetchDao.findUsuarioById(usuarioId);

		UsuarioSession entity = new UsuarioSession();
		entity.setUsuarioSessionId(null);
		entity.setUsuario(usuario);
		entity.setDeviceId(deviceId);
		entity.setUserAgent(userAgent);
		entity.setPlatform(platform);
		entity.setLanguage(language);
		entity.setTokenUser(tokenUser);
		entity.setTokenAccess(tokenAccess);
		entity.setIndSession(Boolean.TRUE);
		entity.setDataModif(LocalDateTime.now());

		LocalDateTime expirationDateTime = LocalDateTime.now().plus(Duration.ofMillis(expiration));
		entity.setDataSession(expirationDateTime);
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
				.enderecos(entity.getPessoa().getEnderecos().stream().map(e -> transform(e)).toList())
				.bancos(entity.getPessoa().getDadosBancarios().stream().map(b -> transform(b)).toList())
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

	public PerfilDto transform(Perfil entity) {
		return PerfilDto.builder()
				.perfilId(entity.getPerfilId())
				.descricao(entity.getDescricao())
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
