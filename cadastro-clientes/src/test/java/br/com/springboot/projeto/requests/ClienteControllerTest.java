package br.com.springboot.projeto.requests;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.springboot.projeto.controllers.ClienteController;
import br.com.springboot.projeto.model.Cliente;

@RunWith(MockitoJUnitRunner.class)
public class ClienteControllerTest {

	@Mock
    private ClienteController clienteController;

    private Cliente cliente1;
    private Cliente cliente2;
    List<Cliente> clientes = new ArrayList<>();

    @Before
    public void setUp() {
        cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Cliente 1");
        cliente1.setEndereco("Endereco 1");
        cliente1.setBairro("Bairro 1");
        List<String> telefones = adicionaDoisTelefones();
        cliente1.setTelefones(telefones);

        cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("Cliente 2");
        cliente2.setEndereco("Endereco 2");
        cliente2.setBairro("Bairro 2");
        List<String> telefones2 = new ArrayList<String>();
        telefones2.add("(11) 99999-8888");
        
        clientes.add(cliente1);
        clientes.add(cliente2);
    }

    //TODO fazer testes para as validações pedidas
    
    @Test
    public void quandoBuscarListaDeClientesDeveRetornarTodosOsClientes() {
        when(clienteController.listaClientes()).thenReturn(new ResponseEntity<>(clientes, HttpStatus.OK));

        ResponseEntity<List<Cliente>> responseEntity = clienteController.listaClientes();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().isEmpty(), false);
        assertEquals(responseEntity.getBody().size(), 2);
    }

    @Test
    public void quandoSalvarDeveRetornarStatusCreated() {
        when(clienteController.salvar(any(Cliente.class))).thenReturn(new ResponseEntity<>(cliente1, HttpStatus.CREATED));

        ResponseEntity<Cliente> response = clienteController.salvar(cliente1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cliente1, response.getBody());
    }

    @Test
    public void quandoAtualizarDeveRetornarStatusOk() {
    	cliente1.setEndereco("Endereco Novo");
        cliente1.setBairro("Bairro Novo");
        when(clienteController.atualizar(cliente1)).thenReturn(new ResponseEntity<>(null,HttpStatus.OK));

        ResponseEntity<?> response = clienteController.atualizar(cliente1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
    
    @Test
    public void quandoBuscarClientePorIdDeveRetornarSomenteOsTelefonesDoClienteInformado() {
        when(clienteController.buscarClienteId(1L)).thenReturn(new ResponseEntity<>(cliente1, HttpStatus.OK));

        ResponseEntity<Cliente> responseEntity = clienteController.buscarClienteId(1L);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), cliente1);
        List<String> telefones = adicionaDoisTelefones();
        assertEquals(responseEntity.getBody().getTelefones(), telefones);
    }
    
    @Test
    public void quandoBuscarClientePorNomeDeveRetornarTodosOsClienteComNomeInformado() {
        when(clienteController.buscarPorNome("Cliente")).thenReturn(new ResponseEntity<>(clientes, HttpStatus.OK));

        ResponseEntity<List<Cliente>> responseEntity = clienteController.buscarPorNome("Cliente");

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), clientes);
    }
    
    private List<String> adicionaDoisTelefones() {
		List<String> telefones = new ArrayList<String>();
        telefones.add("(11) 98564-6547");
        telefones.add("(19) 98877-6655");
		return telefones;
	}
    
}