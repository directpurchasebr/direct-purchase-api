package br.com.directpurchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.directpurchase.entity.Perfil;

public interface PerfilRepository  extends CrudRepository<Perfil, Integer> {

	@Query("select p from Perfil p where p.indRegular = true ")
	public List<Perfil> buscaRegular();
}
