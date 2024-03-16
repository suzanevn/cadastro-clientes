package br.com.springboot.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.projeto.model.Cliente;

/**
 * 
 * @author suzane
 * 
 * Classe onde os dados são obtidos do banco de dados e ocorre também a regra de negócio. 
 *
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("select c from Cliente c where upper(trim(c.nome)) like %?1%")
	List<Cliente> buscaPorNome(String name);
	
}
