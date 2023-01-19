package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class TestaConexao {
	
	public static void main(String[] args) throws SQLException { //Ao tentar rodar o código abaixo ele exige que seja colocado um "throws" na assinatura do método pois podem ter algumas exceptions do tipo checked na conexão com o SQL
		
//		Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC", "root", "Gi150920021903"); //Para estabelecer uma conexão com uma database do mySQL precisamos utilizar a interface "Connection" e então chamar o método "getConnection" da classe "DriveManager" e passarmos uma url com o endereço da databse que queremos conectar, o usuário e também a senha, no caso aqui conectamos com uma database do mySQL, que consta inclusive essa informação na url
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection conexao = connectionFactory.recuperarConexao();
		
		System.out.println("Fechando a conexão!");
		
		conexao.close(); //Aqui chamamos o método para encerrar uma conexão, afinal quando se trata de databases, tudo o que for aberto precisa ser fechado
		
	}

}
