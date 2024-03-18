package br.com.springboot.projeto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.projeto.model.Cliente;
import br.com.springboot.projeto.repository.ClienteRepository;

/**
 * @author suzane
 * Classe para lidar com a ligação da View com as outras partes do sistema que são a regra de negócio e banco de dados.
 */
@RestController
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
    
    /**
    * Listagem de todos os clientes
    * @return lista completa dos clientes
    */
    @GetMapping(value = "listarTodos")
    @ResponseBody
    public ResponseEntity<List<Cliente>> listaClientes() {
    	List<Cliente> clientes = clienteRepository.findAll();
    	return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    }
    
    /**
    * Salva os clientes
    * @param objeto Cliente
    * @return http status
    */
    @PostMapping(value = "salvar")
    @ResponseBody
    public ResponseEntity<?> salvar(@RequestBody Cliente cliente){
    	try {
    		ResponseEntity<String> retornoValida = validaClienteETelefone(cliente, false);
    		if(retornoValida == null){
    			Cliente cli = clienteRepository.save(cliente);
    			return new ResponseEntity<>(cli, HttpStatus.CREATED);
    		} else {
    			return retornoValida;
    		}
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
	/**
     * Atualiza os clientes
     * @param objeto Cliente
     * @return http status
     */
    @PutMapping(value = "atualizar")
    @ResponseBody
    public ResponseEntity<?> atualizar(@RequestBody Cliente cliente){
    	try {
    		if(cliente.getId() == null)
    			return new ResponseEntity<String>("Cliente não informado", HttpStatus.BAD_REQUEST);
    		ResponseEntity<String> retornoValidaTelefone = validaClienteETelefone(cliente, true);
    		if(retornoValidaTelefone == null) {
	    		Cliente user = clienteRepository.save(cliente);
	    		return new ResponseEntity<Cliente>(user, HttpStatus.OK);
    		}else {
    			return retornoValidaTelefone;
    		}
    	} catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Apaga os clientes
     * @param id do Cliente
     * @return http status
     */
    @DeleteMapping(value = "delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long idCliente){
    	try {
    		clienteRepository.deleteById(idCliente);
        	return new ResponseEntity<String>("Cliente deletado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Busca de clientes por id
     * @param id do Cliente
     * @return objeto cliente
     */
    @GetMapping(value = "buscarClienteId")
    @ResponseBody
    public ResponseEntity<Cliente> buscarClienteId(@RequestParam(name = "idCliente") Long idCliente){
    	try {
    		Cliente cliente = clienteRepository.findById(idCliente).get();
        	return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Busca de clientes por nome
     * @param nome do Cliente
     * @return objeto cliente
     */
    @GetMapping(value = "buscarPorNome")
    @ResponseBody
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam(name = "name") String name){
    	try {
    		List<Cliente> user = clienteRepository.buscarPorNome(name.trim().toUpperCase());
        	return new ResponseEntity<List<Cliente>>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Busca de telefones por numero do telefone
     * @param telefone e idCliente
     * @return objeto cliente
     */
    @GetMapping(value = "buscarPorTelefone")
    @ResponseBody
    public ResponseEntity<List<Cliente>> buscarPorTelefone(@RequestParam(name = "telefone") String telefone){
    	try {
    		List<Cliente> clientes = clienteRepository.buscarPorTelefone(telefone);
    		return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    /**
     * Valida cliente e telefone
     * @param objeto Cliente e booleano para informar se é inserir ou editar
     * @return ResponseEntity null se tudo estiver ok, ou retorna mensagem informando o problema
     */
	private ResponseEntity<String> validaClienteETelefone(Cliente cliente, boolean atualizar) {
		ResponseEntity<List<Cliente>> existe = buscarPorNome(cliente.getNome());
		if(existe.getBody().size() > 0) {
			//Melhorar buscando o id direto no select
			if(!atualizar) {
				return new ResponseEntity<>("Nome ja cadastrado!", HttpStatus.BAD_REQUEST);
			} else {
				List<Cliente> clienteRetornado = existe.getBody();
				for (Cliente c : clienteRetornado) {
					if(!c.getId().equals(cliente.getId())) {
						return new ResponseEntity<>("Nome ja cadastrado!", HttpStatus.BAD_REQUEST);
					}
				} 
			}
		}
		if(cliente.getNome().length() <= 10)
			return new ResponseEntity<>("O nome do cliente não pode ter menos de 10 caracteres!", HttpStatus.BAD_REQUEST);
		boolean existeTelefone = false;
		if( cliente.getTelefones() != null) {
			for (String telefone : cliente.getTelefones()) {
				if(telefone==null || telefone.isEmpty()) {
					return new ResponseEntity<>("Telefone não pode ter valor vazio!", HttpStatus.BAD_REQUEST);
				}
				if(!telefone.matches("\\(\\d{2}\\) \\d{5}-\\d{4}")) {
					return new ResponseEntity<>("Telefone deve estar no formato (99) 99999-9999!", HttpStatus.BAD_REQUEST);
				}
				ResponseEntity<List<Cliente>> existeTelefoneList = buscarPorTelefone(telefone);
				if(existeTelefoneList.getBody().size() > 0) {
					if(atualizar) {
						List<Cliente> clientesRetornados = existeTelefoneList.getBody();
						for (Cliente clienteRetornado : clientesRetornados) {
							if(!clienteRetornado.getId().equals(cliente.getId())) {
								existeTelefone = true;
								break;
							}
						}
					} else {
						existeTelefone = true;
						break;
					}
				} 
			}
		}
		return existeTelefone ? new ResponseEntity<>("Telefone ja cadastrado!", HttpStatus.BAD_REQUEST) : null;
	}
    
}
