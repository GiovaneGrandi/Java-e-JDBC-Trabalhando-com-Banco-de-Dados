package br.com.alura.jdbc;

import java.sql.SQLException;
import java.util.Iterator;

public class TestaPoolConexoes {
	
	public static void main(String[] args) throws SQLException {
		
		ConnectionFactory conexao = new ConnectionFactory();
		
		for (int i = 0; i < 20; i++) { //Criando um laço que vai até 20 repetições para testar o nosso pool
			conexao.recuperarConexao(); //Para cada iteração do laço uma conexão nova vai ser estabelecida, porém como o tamanho do nosso pool foi setado para 15 as conexões não irão chegar até 20, ficando somente nas 15 garantidas no comboPoolDataSource (as conexões começam em índice 0)
			//Porém mesmo que o limite seja 15 conexões assim que a primeira completar a sua requisição ela já ficará disponível para fazer a requisição do próximo usuário, assim não cria infinitas requisições para sobrecarregar o banco de dados e não fica uma fila enorme caso fosse apenas uma conexão com o servidor, pois agora dividimos as requisições entre várias conexões
			System.out.println("Conexão de número: " + i);
		}
		
	}

}
