package br.com.springboot.projeto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.projeto.model.Cliente;
import br.com.springboot.projeto.repository.ClienteRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /*IC/CD ou CDI - Injecao de dependencia*/
	private ClienteRepository clienteRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot api " + name + "!";
    }
    
    @GetMapping(value = "listatodos")
    @ResponseBody
    public ResponseEntity<List<Cliente>> listaClientes() {
    	List<Cliente> clientes = clienteRepository.findAll();
    	return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);//retorna json
    }
    
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
    
    @GetMapping(value = "buscarUserId")
    @ResponseBody
    public ResponseEntity<Cliente> buscarClienteId(@RequestParam(name = "idCliente") Long cliente){
    	Cliente user = clienteRepository.findById(cliente).get();
    	return new ResponseEntity<Cliente>(user, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarPorNome")
    @ResponseBody
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam(name = "name") String name){
    	List<Cliente> user = clienteRepository.buscaPorNome(name.trim().toUpperCase());
    	return new ResponseEntity<List<Cliente>>(user, HttpStatus.OK);
    }
    
}
