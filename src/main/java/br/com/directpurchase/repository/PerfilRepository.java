package br.com.directpurchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.directpurchase.entity.Perfil;

public interface PerfilRepository  extends JpaRepository<Perfil, Integer> {

	@Query("select p from Perfil p where p.indRegular = true ")
	public List<Perfil> buscaRegular();
}
