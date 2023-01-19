package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaRemocao {
	
	public static void main(String[] args) throws SQLException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection conexao = connectionFactory.recuperarConexao();
		
//		Statement stm = conexao.createStatement(); //Criando um statement para poder fazer um comando SQL
//		stm.execute("DELETE FROM PRODUTO WHERE ID > 2"); //Aqui fizemos um comando para deletar todos os itens com id maior que 2
		
		
		//Aplicando aqui também o novo jeito de executar os statements
		PreparedStatement stm = conexao.prepareStatement("DELETE FROM PRODUTO WHERE ID > ?");
		stm.setInt(1, 2); //Aqui aplicamos o setInt para podermos mudar o parametro do WHERE de uma forma mais segura
		stm.execute();
		
		Integer linhasModificadas = stm.getUpdateCount(); //O método "getUpdateCount" serve para ele retornar quantas linhas foram modificadas na tabela
		
		System.out.println("Quantidade de linhas modificadas: " + linhasModificadas); //Aqui criamos um sysout para podermos ter uma noção se o nosso comando foi bem executado ou não, pois caso deletemos algum item ele vai falar quantas linhas foram editadas (nesse caso deletadas), caso o comando não consiga ser executado o nú7mero de linhas editadas será de 0
		
		conexao.close();
		
	}

}
