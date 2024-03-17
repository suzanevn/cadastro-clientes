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
	List<Cliente> buscarPorNome(String name);
	
//	@Query("SELECT c FROM Cliente c WHERE c.telefones in ?1")
	@Query("SELECT c FROM Cliente c JOIN c.telefones WHERE numero = ?1")
    List<Cliente> buscarPorTelefone(String telefone);
	
	
//	@Query("SELECT c FROM Cliente c WHERE c.telefones = ?1 and CASE WHEN ?2 IS NULL THEN 1=1 ELSE c.id <> ?2 END")
//	@Query("SELECT t.cliente FROM Telefones t WHERE t.numero = ?1 AND (CASE WHEN ?2 IS NULL THEN 1=1 ELSE t.cliente_id <> ?2 END)")
//    List<Cliente> buscarPorTelefone(String telefone, Long idCliente);
	
//	int countByTelefoneAndIdNot(String telefone, Long clienteId);
	
}
