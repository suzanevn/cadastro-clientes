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
 * Classe onde os dados são obtidos do banco de dados. 
 *
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("select c from Cliente c where upper(trim(c.nome)) like %?1%")
	List<Cliente> buscarPorNome(String name);
	
	@Query("SELECT c FROM Cliente c JOIN c.telefones WHERE numero = ?1")
    List<Cliente> buscarPorTelefone(String telefone);
	
}
