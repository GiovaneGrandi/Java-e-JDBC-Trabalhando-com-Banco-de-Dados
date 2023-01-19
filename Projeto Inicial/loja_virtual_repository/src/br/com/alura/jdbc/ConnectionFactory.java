package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory { //O "Factory Method" é um jeito de encapsular a criação de um objeto, nesse caso nós encapsulamos a criação da conexão com o banco de dados em uma classe separada para evitar muitas manutenções nos códigos, por isso usamos o "Factory" no nome da classe
	
	public DataSource dataSource; //Criando um atributo do tipo "DataSource" para a classe
	
	public ConnectionFactory() { //Implementando um construtor para a classe, para que assim que ela for chamada já efetue alguns processos relacionados ao "pool" de conexões
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource(); //Criando um novo objeto "ComboPooledDataSource" que vem do "C3P0"
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC"); //Setando a url de conexão no nosso pool
		comboPooledDataSource.setUser("root"); //Setando o usuário do banco de dados que será buscado
		comboPooledDataSource.setPassword("Gi150920021903"); //E a senha
		
		comboPooledDataSource.setMaxPoolSize(15);
		
		//O pool de conexões nada mais é que uma conexão que fica sempre aberta para caso ela receba uma requisição, assim evita um "abre e fecha" de conexões que gera custos e mais desempenho para o servidor
		
		this.dataSource = comboPooledDataSource; //Aqui nós colocamos esse pool acima dentro do "this dataSource" ou seja, para cada objeto "ConnectionFactory" criado será criado um novo dataSource exclusivamente para cada objeto, aí esse atributo servirá para guardar as configurações do pool feitas acima
	}
	
	public Connection recuperarConexao() throws SQLException {
		return this.dataSource.getConnection(); //Aqui criamos um método de recuperarConexao um pouco diferente do antigo, ao invés de conectarmos com o banco de dados diretamente com a url, aqui nós passamos o this dataSource chamando o método "getConnection" assim ele irá conectar segundo as configurações do pool caso tenha alguma conexão disponível
	}
	
//	public Connection recuperarConexao() throws SQLException { //Criando um método dentro de uma classe que irá armazenar os dados para a conexão do banco de dados, caso um dia precisarmos mudar a senha do banco de dados será necessário apenas muda-la aqui no método que todos os outros códigos serão corrigidos
//		return DriverManager.getConnection("jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC", "root", "Gi150920021903"); 
//	}

}
