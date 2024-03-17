package br.com.springboot.projeto.requests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.springboot.projeto.controllers.ClienteController;
import br.com.springboot.projeto.model.Cliente;
import br.com.springboot.projeto.repository.ClienteRepository;

/**
 * @author suzane
 * Classe para teste da classe ClienteController.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClienteControllerTest {

	@Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteController clienteController;

    private Cliente cliente1;
    private Cliente cliente2;
    List<Cliente> clientes = new ArrayList<>();

    @Before
    public void setUp() {
        cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Cliente Teste 1");
        cliente1.setEndereco("Endereco 1");
        cliente1.setBairro("Bairro 1");
        List<String> telefones = adicionarDoisTelefones();
        cliente1.setTelefones(telefones);

        cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("Cliente Teste 2");
        cliente2.setEndereco("Endereco 2");
        cliente2.setBairro("Bairro 2");
        List<String> telefones2 = new ArrayList<String>();
        telefones2.add("(11) 99999-8888");
        
        clientes.add(cliente1);
        clientes.add(cliente2);
    }

    @Test
    public void quandoBuscarListaDeClientesDeveRetornarTodosOsClientes() {
        when(clienteRepository.findAll()).thenReturn(clientes);

        ResponseEntity<List<Cliente>> responseEntity = clienteController.listaClientes();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().isEmpty(), false);
        assertEquals(responseEntity.getBody().size(), 2);
    }

    @Test
    public void quandoSalvarClienteDeveRetornarStatusCreated() {
    	when(clienteRepository.save(cliente1)).thenReturn(cliente1);
    	
    	ResponseEntity<?> responseEntity = clienteController.salvar(cliente1);

    	assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    	assertEquals(cliente1, responseEntity.getBody());
    }
    
    @Test
    public void quandoSalvarClienteComNomeMenorQueDezCaracteresDeveRetornarStatusBadRequest() {
        Cliente cliente = new Cliente();
        cliente.setNome("NomeMenor");

        ResponseEntity<?> responseEntity = clienteController.salvar(cliente);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("O nome do cliente não pode ter menos de 10 caracteres!", responseEntity.getBody());
    }
    
    @Test
    public void quandoSalvarClienteComNomeJaExistenteNoBancoDeveRetornarStatusBadRequest() {
    	 Cliente cliente = new Cliente();
         cliente.setNome("João");

        ResponseEntity<?> responseEntity = clienteController.salvar(cliente);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("O nome do cliente não pode ter menos de 10 caracteres!", responseEntity.getBody());
    }

    @Test
    public void quandoAtualizarClienteDeveRetornarStatusOk() {
    	cliente1.setEndereco("Endereco Novo");
        cliente1.setBairro("Bairro Novo");

        ResponseEntity<?> response = clienteController.atualizar(cliente1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
    
    @Test
    public void quandoBuscarClientePorIdDeveRetornarSomenteOClienteInformado() {
    	when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente1));

        ResponseEntity<Cliente> responseEntity = clienteController.buscarClienteId(1L);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), cliente1);
        List<String> telefones = adicionarDoisTelefones();
        assertEquals(responseEntity.getBody().getTelefones(), telefones);
    }
    
    @Test
    public void quandoBuscarClientePorIdNaoExistenteDeveRetornarStatusNotFound() {
        when(clienteRepository.findById(3L)).thenReturn(Optional.empty());

        ResponseEntity<Cliente> responseEntity = clienteController.buscarClienteId(3L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    
    @Test
    public void quandoBuscarClientePorNomeDeveRetornarTodosOsClienteComNomeInformado() {
    	when(clienteRepository.buscarPorNome("Cliente")).thenReturn(clientes);

        List<Cliente> resultado = clienteRepository.buscarPorNome("Cliente");

        assertEquals(2, resultado.size());
        assertEquals("Cliente Teste 1", resultado.get(0).getNome());
        assertEquals("Cliente Teste 2", resultado.get(1).getNome());
    }
    
    @Test
    public void quandoDeletarClientePorIdDeveRetornarStatusOK() {
        ResponseEntity<String> responseEntity = clienteController.delete(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void quandoAtualizarClienteComTelefoneInvalidoDeveRetornarBadRequest() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Telefone Invalido");
        List<String> telefones = new ArrayList<String>();
        telefones.add("1234567890");
        cliente.setTelefones(telefones);

        ResponseEntity<?> responseEntity = clienteController.atualizar(cliente);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Telefone deve estar no formato (99) 99999-9999!", responseEntity.getBody());
    }
    
    @Test
    public void quandoAtualizarClienteComTelefoneJaCadastradoDeveRetornarBadRequest() {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(2L);
        clienteExistente.setNome("Maria da Silva");
        List<String> telefones = new ArrayList<String>();
        telefones.add("(11) 98888-1234");
        clienteExistente.setTelefones(telefones);

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(1L);
        clienteAtualizado.setNome("João da Silva");
        clienteAtualizado.setTelefones(telefones);

        List<Cliente> clientesTelefone = new ArrayList<Cliente>();
        clientesTelefone.add(clienteExistente);
        when(clienteRepository.buscarPorTelefone("(11) 98888-1234")).thenReturn(clientesTelefone);

        ResponseEntity<?> responseEntity = clienteController.atualizar(clienteAtualizado);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Telefone ja cadastrado!", responseEntity.getBody());
    }
    
    private List<String> adicionarDoisTelefones() {
		List<String> telefones = new ArrayList<String>();
        telefones.add("(11) 98564-6547");
        telefones.add("(19) 98877-6655");
		return telefones;
	}
    
}