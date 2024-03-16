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
public class ClientesController {
	
	@Autowired
	private ClienteRepository clienteRepository;
    
    /**
    * Listagem dos clientes
    * @return lista completa dos clientes
    */
    @GetMapping(value = "listatodos")
    @ResponseBody
    public ResponseEntity<List<Cliente>> listaClientes() {
    	List<Cliente> clientes = clienteRepository.findAll();
    	return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    }
    
    //TODO criar javadoc
    /**
    *
    * @param name the name to greet
    * @return greeting text
    */
    @PostMapping(value = "salvar")
    @ResponseBody
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente){
    	Cliente user = clienteRepository.save(cliente);
    	return new ResponseEntity<Cliente>(user, HttpStatus.CREATED);
    }
    
    @PutMapping(value = "atualizar")
    @ResponseBody
    public ResponseEntity<?> atualizar(@RequestBody Cliente cliente){
    	if(cliente.getId() == null)
    		return new ResponseEntity<String>("Id nao informado para atualizacao", HttpStatus.OK);
    	Cliente user = clienteRepository.saveAndFlush(cliente);
    	return new ResponseEntity<Cliente>(user, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long idCliente){
    	clienteRepository.deleteById(idCliente);
    	return new ResponseEntity<String>("Cliente deletado com sucesso", HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarClienteId")
    @ResponseBody
    public ResponseEntity<Cliente> buscarClienteId(@RequestParam(name = "idCliente") Long idCliente){
    	Cliente cliente = clienteRepository.findById(idCliente).get();
    	return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarPorNome")
    @ResponseBody
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam(name = "name") String name){
    	List<Cliente> user = clienteRepository.buscaPorNome(name.trim().toUpperCase());
    	return new ResponseEntity<List<Cliente>>(user, HttpStatus.OK);
    }
    
}
