package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercaoComParametro {

	public static void main(String[] args) throws SQLException {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		try (Connection conexao = connectionFactory.recuperarConexao()) { //Colocando a connection dentro de um try também para que ela se feche automaticamente

			conexao.setAutoCommit(false); //Para que nós controlemos como será feito as inserções no comando SQL ao invés do JDBC fazer isso pra nós precisamos chamar o método "setAutoCommit" e passar false como parametro, porém assim que fizemos isso os itens são criados porém eles não são inseridos na tabela, ao executar a listagem os novos itens não aparecem


			try (PreparedStatement stm = conexao.prepareStatement("INSERT INTO PRODUTO (NOME, DESCRICAO) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS); //Aqui nós passamos a string dos comandos SQL para o preparedStatement fazer a checagem, nós deixamos o campo de values vazio para poder inserir a informação de forma mais segura
					//Dessa forma, usando o PreparedStatement nós conseguimos evitar que alguma inserção que venha por engano com uma aspa simples ou então até algum comando SQL que possa ser prejudicial seja tratada extritamente como uma string, na forma antiga de se tratar os valores dos campos podia se ter uma "SQL injection" que seria alguém inseri um valor SQL no campo e ele seria executado no nosso programa sem que soubessemos //Agora inserimos todo o trecho de código da inserção dos dados em um try para podermos fazer o controle das transações caso ocorra um erro
					) {
				//O prepared statement herda da classe "AutoCloseable" então ao passarmos o preparedstatement dentro dos parenteses do try ele será fechado de maneira automatica sem que tenhamos que lembrar de fecha-lo no fim do código

				adicionarVariavel("SmarTV", "45 Polegadas", stm); //Dessa vez não estamos usando variaveis externas, estamos adicionando os valores que queremos inserir na tabela diretamente nos parametros do método que irá colocar os valores no statement, além de informar os valores nós informamos o statement que devrá ser executado levando em consideração esses valores
				adicionarVariavel("Radio", "Radio de Bateria", stm); //Com esse modelo poderemos criar multiplos itens sem repetir muito código, apenas invocar novamente o método e indicar seus parametros corretamente

				conexao.commit(); //O método "commit" da "Connection" será consegue fazer com que as transações sejam inseridas na tabela normalmente

			} catch (Exception e) { //Aqui fazemos um catch que captura qualquer exceção pois ele busca a classe mais genérica possível
				e.printStackTrace();
				System.out.println("ROLLBACK EXECUTADO"); //Aqui caso seja pego uma exception nós exibimos uma mensagem
				conexao.rollback(); //Com o método "rollback" da interface "Connection" nós conseguimos fazer com que caso haja uma exception no meio da transação ela será cancelada e então retornada, porque as transações só podem ser efetuadas se todos os dados não apresentarem erros, assim como uma transação bancária onde não tem como passar só uma parte do dinheiro, ou passa tudo ou nada 
			}

		}

	}



	//E aqui está o método que está sendo usado nesse trecho de código
	private static void adicionarVariavel(String nome, String descricao, PreparedStatement stm) throws SQLException { //Ao invés de deixar o código como antes eu passei todo o código escrito para dentro de um método usando o "Extract Method" no quick acess, esse método é responsavel por pegar os valores inseridos nele como parametros e rodar o statement desejado inserindo esses valores nele

		stm.setString(1, nome); //Com os métodos do PreparedStatement nós conseguimos aqui informar que valor cada espaço do value vai receber (o primeiro oaramentro é o indice de qual campo esse valor pertencerá e o segundo é o valor propriamente dito, nesse caso uma variavel)
		stm.setString(2, descricao);

		if(nome.equals("Radio")) { 
			throw new RuntimeException("Não foi possível adicionar o produto!"); //Jogando uma exception para ver como a inserção se comporta, caso o segundo item tenha uma exception, o primeiro será adicionado normalmente porém na hora do segundo ser adicionado a exception será jogada no console
		}

		stm.execute(); //Aqui executamos o nosso statement

		try (ResultSet rst = stm.getGeneratedKeys()) { //Aqui no ResultSet aplicamos o mesmo método de auto fechamento utilizando o try que está presente logo acima, assim evitando que esqueçamos de fechar o ResultSet 
			while(rst.next()) { 
				Integer id = rst.getInt(1);
				System.out.println("O id criado foi: " + id);
			}
		}
	}

}
