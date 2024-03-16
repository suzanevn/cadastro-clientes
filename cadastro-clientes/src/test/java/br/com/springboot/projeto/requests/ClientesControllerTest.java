package br.com.springboot.projeto.requests;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.springboot.projeto.controllers.ClientesController;
import br.com.springboot.projeto.model.Cliente;
import br.com.springboot.projeto.repository.ClienteRepository;

@RunWith(MockitoJUnitRunner.class)
public class ClientesControllerTest {

    @Mock
    private ClienteRepository clienteRepositoryMock;

    @InjectMocks
    private ClientesController clientesController;

    private Cliente cliente1;
    private Cliente cliente2;

    @Before
    public void setUp() {
        cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Cliente 1");

        cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("Cliente 2");
    }

    @Test
    public void testListaClientes() {
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
        when(clienteRepositoryMock.findAll()).thenReturn(clientes);

        ResponseEntity<List<Cliente>> response = clientesController.listaClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientes, response.getBody());
    }

    @Test
    public void testSalvar() {
        when(clienteRepositoryMock.save(any(Cliente.class))).thenReturn(cliente1);

        ResponseEntity<Cliente> response = clientesController.salvar(cliente1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cliente1, response.getBody());
    }

    @Test
    public void testAtualizar() {
        when(clienteRepositoryMock.saveAndFlush(cliente1)).thenReturn(cliente1);

        ResponseEntity<?> response = clientesController.atualizar(cliente1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente1, response.getBody());
    }
}


/*
@RunWith(MockitoJUnitRunner.class)
public class ClientesControllerTest {

	@InjectMocks
	private ClientesController clientesController;
	
	@Mock
	private ClienteRepository clienteRepository;
	
	@Before
	public void setUp(){
		Cliente cliente = new Cliente(1L,"teste", "teste", "teste", null);
//		clientesController.salvar(cliente);
		clienteRepository.save(cliente);
//	    EnderecoData enderecoData = EntityUtils.criarEndereco();
//	    BDDMockito.when(enderecoRespository.findByCep(ArgumentMatchers.any())).thenReturn(enderecoData);
	}
	
	//nome dos metodos ex quandoBuscarClientePorIdDeveRetornarSucesso
	@Test
	public void quandoBuscarClientePorIdDeveRetornarSucesso() {
		Cliente cliente = new Cliente(2L,"teste", "teste", "teste", null);
		when(this.clientesController.buscarClienteId(2L))
			.thenReturn(new ResponseEntity<Cliente>(cliente, HttpStatus.OK));
		
		ResponseEntity<List<Cliente>> lista = clientesController.listaClientes();
		
		verify(lista!=null);
		
//		given().accept(ContentType.JSON)
//		.when().get("buscarClienteId")
//		.then().statusCode(HttpStatus.OK.value());
		
	}
	
}
*/




/*
 * @SpringBootTest //@RunWith(SpringRunner.class) //@WebAppConfiguration
 * //@WebMvcTest public class ClientesControllerTest {
 * 
 * @Autowired private ClientesController clientesController; //TODO controller
 * null qdo roda
 * 
 * @MockBean private ClienteRepository clienteRepository;
 * 
 * @Before public void setup() {
 * RestAssuredMockMvc.standaloneSetup(this.clientesController); }
 * 
 * //nome dos metodos ex quandoBuscarClientePorIdDeveRetornarSucesso
 * 
 * @Test public void quandoBuscarClientePorIdDeveRetornarSucesso() { Cliente
 * cliente = new Cliente(1L,"teste", "teste", "teste", null);
 * when(this.clientesController.buscarClienteId(1L)) .thenReturn(new
 * ResponseEntity<Cliente>(cliente, HttpStatus.OK));
 * 
 * given().accept(ContentType.JSON) .when().get("buscarClienteId")
 * .then().statusCode(HttpStatus.OK.value());
 * 
 * }
 * 
 * }
 */
