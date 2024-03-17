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
//        when(clienteController.listaClientes()).thenReturn(new ResponseEntity<>(clientes, HttpStatus.OK));
        when(clienteRepository.findAll()).thenReturn(clientes);

        ResponseEntity<List<Cliente>> responseEntity = clienteController.listaClientes();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().isEmpty(), false);
        assertEquals(responseEntity.getBody().size(), 2);
    }

    @Test
    public void quandoSalvarDeveRetornarStatusCreated() {
    	when(clienteRepository.save(cliente1)).thenReturn(cliente1);
    	
    	ResponseEntity<?> responseEntity = clienteController.salvar(cliente1);

    	assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    	assertEquals(cliente1, responseEntity.getBody());
    }
    
    @Test
    public void quandoSalvarClienteComNomeMaiorQueDezCaracteresDeveRetornarStatusBadRequest() {
        Cliente cliente = new Cliente();
        cliente.setNome("NomeMaiorQue10Caracteres");

        ResponseEntity<?> responseEntity = clienteController.salvar(cliente);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("O nome do cliente não pode ter mais de 10 caracteres!", responseEntity.getBody());
    }
    
//    @Test
//    public void quandoSalvarClienteComNomeJaExistenteNoBancoDeveRetornarStatusBadRequest() {
//        Cliente cliente = new Cliente();
//        cliente.setNome("Cliente 1");
//        when(clienteRepository.save(cliente)).thenReturn(cliente);
//        
//        ResponseEntity<?> responseEntity = clienteController.salvar(cliente);
////TODO assert msg
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    	// Mock do cliente com nome maior que 10 caracteres
//        Cliente cliente = new Cliente();
//        cliente.setNome("Cliente 1");
//
//        // Chama o método salvar do clienteController
//        ResponseEntity<?> responseEntity = clienteController.salvar(cliente);
//
//        // Verifica se o retorno é um status de erro (BAD_REQUEST)
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//
//        // Verifica se a mensagem de erro está presente no corpo da resposta
//        assertEquals("Nome ja cadastrado!", responseEntity.getBody());
    	
    	
    	
    	
    	
    	
    	// Mock do cliente com nome já cadastrado
//        Cliente cliente = new Cliente();
//        cliente.setNome("Cliente 1");
//
//        // Mock do retorno da validação do cliente já cadastrado
//        ResponseEntity<String> responseEntityValidacao = new ResponseEntity<>("Nome ja cadastrado!", HttpStatus.BAD_REQUEST);
////        when(clienteRepository.buscarPorNome("NomeJaCad")).thenReturn(new ArrayList<>()); // Nenhum cliente encontrado, indicando que o nome não está cadastrado
//        when(clienteController.validaCliente("Cliente 1")).thenReturn(responseEntityValidacao);
//
//        // Chama o método salvar do clienteController
//        ResponseEntity<?> responseEntity = clienteController.salvar(cliente);
//
//        // Verifica se o retorno é um status de erro (BAD_REQUEST)
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//
//        // Verifica se a mensagem de erro está presente no corpo da resposta
//        assertEquals("Nome ja cadastrado!", responseEntity.getBody());
    	
    	//TODO
//    	Cliente cliente = new Cliente();
//        cliente.setNome("Cliente 1");
//
//        ResponseEntity<?> responseEntity = clienteController.salvar(cliente);
//
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertEquals("Nome ja cadastrado!", responseEntity.getBody());
//    }

    @Test
    public void quandoAtualizarDeveRetornarStatusOk() {
    	cliente1.setEndereco("Endereco Novo");
        cliente1.setBairro("Bairro Novo");
//        when(clienteRepository.saveAndFlush(cliente1)).thenReturn(null); //TODO erro mandou tirar essa linha

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
        List<String> telefones = adicionaDoisTelefones();
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
        assertEquals("Cliente 1", resultado.get(0).getNome());
        assertEquals("Cliente 2", resultado.get(1).getNome());
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
        cliente.setNome("invalido");
        List<String> telefones = new ArrayList<String>();
        telefones.add("1234567890");
        cliente.setTelefones(telefones); // Telefone inválido

        // Simular que o cliente já existe no banco de dados
//        when(clienteRepository.save(cliente)).thenReturn(cliente);//TODO erro mandou tirar essa linha

        // Chamar o método atualizar do controlador
        ResponseEntity<?> responseEntity = clienteController.atualizar(cliente);

        // Verificar se o resultado é um BadRequest
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    
    @Test
    public void quandoAtualizarClienteComTelefoneJaCadastrado_DeveRetornarBadRequest() {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(2L); // ID de outro cliente
        clienteExistente.setNome("Maria");
        List<String> telefones = new ArrayList<String>();
        telefones.add("(11) 98888-1234");
        clienteExistente.setTelefones(telefones); // Telefone que já está cadastrado

        // Configurar o cliente que está sendo atualizado
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(1L);
        clienteAtualizado.setNome("João");
        clienteAtualizado.setTelefones(telefones); // Mesmo telefone que já está cadastrado

        List<Cliente> clientesTelefone = new ArrayList<Cliente>();
        clientesTelefone.add(clienteExistente);
        // Simular que o cliente com o mesmo telefone já está cadastrado no banco de dados
//        when(clienteController.buscarPorTelefone("1234567890")).thenReturn(new ResponseEntity<List<Cliente>>(clientesTelefone, HttpStatus.OK));
        when(clienteRepository.buscarPorTelefone("(11) 98888-1234")).thenReturn(clientesTelefone);

        // Chamar o método atualizar do controlador
        ResponseEntity<?> responseEntity = clienteController.atualizar(clienteAtualizado);

        // Verificar se o resultado é um BadRequest
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Telefone ja cadastrado!", responseEntity.getBody());
    }
    
    private List<String> adicionaDoisTelefones() {
		List<String> telefones = new ArrayList<String>();
        telefones.add("(11) 98564-6547");
        telefones.add("(19) 98877-6655");
		return telefones;
	}
    
}