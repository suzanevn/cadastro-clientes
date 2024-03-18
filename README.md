cadastro-clientes

Projeto básico para cadastro de clientes

- Instalar o banco postgres; 
- Configurar as variaveis de ambiente;
- Criar o banco: CREATE DATABASE bancoClientes
- no arquivo application.properties alterar usuario e senha conforme o seu postgres
	spring.datasource.username=USUARIO
	spring.datasource.password=SENHA

- Link para acesso da tela principal http://localhost:8000/cadastroClientes/

A tela possui os campos Nome, Endereço, Bairro e Telefones.
Ao preencher o campo telefone clique em adicionar para que ele seja enviado para a lista de inserção.
Os botões são Salvar, Novo, Pesquisar e Deletar.
Ao pesquisar será aberta uma tela para que seja informado o nome para busca, ao clicar em buscar é mostrada a lista com id e nome do cliente, e também os botões Ver e Deletar.
O botão Ver traz os dados para edição na tela e o deletar apaga o registro.

- pode ser feita a requição atraves de endpoint, como: http://localhost:8000/cadastroClientes/listaTodos

Para o desenvolvimento foi utilizado Java 11, Spring, Maven e tela html com javascript
