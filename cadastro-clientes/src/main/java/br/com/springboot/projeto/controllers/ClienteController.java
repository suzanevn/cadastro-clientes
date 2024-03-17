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
    @GetMapping(value = "listatodos")
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
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente){
    	try {
            Cliente cli = clienteRepository.save(cliente);
            return new ResponseEntity<>(cli, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
    			return new ResponseEntity<String>("Cliente não informado", HttpStatus.OK);
    		Cliente user = clienteRepository.saveAndFlush(cliente);
    		return new ResponseEntity<Cliente>(user, HttpStatus.OK);
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
     * Busca de clientes
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
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
    		List<Cliente> user = clienteRepository.buscaPorNome(name.trim().toUpperCase());
        	return new ResponseEntity<List<Cliente>>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
