package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercao {
	
	public static void main(String[] args) throws SQLException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection conexao = connectionFactory.recuperarConexao();
		
		Statement stm = conexao.createStatement(); //Criando um statement para poder utilizar os comandos do SQL
		stm.execute("INSERT INTO PRODUTO (NOME, DESCRICAO) VALUES ('Mouse', 'Mouse Sem Fio')", Statement.RETURN_GENERATED_KEYS); //Aqui eu insiro os comandos de inserção do SQL, no final dos parametros eu utilizo o método "RETURN_GENERATED_KEYS" do "Statement" para que seja retornada a chave primária que foi criada, no caso da nossa tabela a chave primária é a id
		//Porém o problema de usar o código dessa maneira é que sempre que o executarmos para testar um novo item cópia será adicionado na tabela criando assim um excesso de lixo, pois aqui ao contrário do workbench não podemos selecionar apenas u7ma linha de comando para executa-la em separado
		
		ResultSet rst = stm.getGeneratedKeys(); //Aqui usamos o método "getGeneratedKeys" para poder pegar a chave primária criada e fazermos a iteração em cima apenas das ids
		
		while(rst.next()) { //Aqui é o mesmo while de sempre, buscamos por objetos próximos, caso eles existam o while adiciona eles, caso não existam o laço termina
			Integer id = rst.getInt(1);
			System.out.println("O id criado foi: " + id); //Aqui temos uma confirmação que um item novo foi criado, pos assim que um novo item for criado ele terá o seu id impresso no console junto da mensagem no sysout
		}
		
		conexao.close();
		
	}

}
