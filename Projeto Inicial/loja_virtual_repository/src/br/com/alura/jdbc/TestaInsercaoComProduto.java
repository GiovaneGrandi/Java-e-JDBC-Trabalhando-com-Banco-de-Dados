package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.alura.jdbc.modelo.Produto;

public class TestaInsercaoComProduto {
	
	public static void main(String[] args) throws SQLException {
		
		Produto comoda = new Produto("Cômoda", "Cômoda Vertical"); //Criando um novo produto, com essa classe no Java fica muito mais fácil para representar um produto do que fazer apenas Strings
		
		try(Connection conexao = new ConnectionFactory().recuperarConexao()) {
			
			String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO) VALUES (?, ?)"; //Inserindo o comando sql dentro de uma referencia do tipo string
			
			try(PreparedStatement pstm = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { //Usando a referencia para inserir o comando de forma resumida além de retornar a chave primária gerada
				
				pstm.setString(1, comoda.getNome()); //Passando o nome do objeto comoda para o comando sql através do seu get
				pstm.setString(2, comoda.getDescricao()); //Aqui passando a sua descricao
				
				pstm.execute(); //Executando o comando
				
				try(ResultSet rst = pstm.getGeneratedKeys()) {
					while(rst.next()) {
						comoda.setId(rst.getInt(1)); //Aqui através do while para cada item inserido na tabela o seu id será setado automaticamente
					}
					
				}
				
			}
			
		}
		
		System.out.println(comoda); //Chamando o toString da classe Produto para exibir o resultado
		
	}

}
