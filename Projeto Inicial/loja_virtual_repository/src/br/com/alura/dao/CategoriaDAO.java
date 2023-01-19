package br.com.alura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.modelo.Categoria;
import br.com.alura.jdbc.modelo.Produto;

public class CategoriaDAO {
	
	private Connection conexao;

	public CategoriaDAO(Connection conexao) { //Criando um construtor que receberá uma conexão
		this.conexao = conexao;
	}
	
	public List<Categoria> listar() throws SQLException { //Criando o método para exibir os elementos da tabela de Categoria assim como fizemos para a de Produto
		List<Categoria> categorias = new ArrayList<>();
		
		String sql = "SELECT * FROM CATEGORIA";
		
		try(PreparedStatement pstm = conexao.prepareStatement(sql)) {
			pstm.execute();
			
			try(ResultSet rst = pstm.getResultSet()) {
				while(rst.next()) {
					Categoria categoria = new Categoria(rst.getInt(1), rst.getString(2));
					
					categorias.add(categoria);
				}
			}
		}
		return categorias;
	}

	public List<Categoria> listarComProdutos() throws SQLException { //Aqui estamos criando um método que fará a exibição das categorias já com os seus produtos relacionados, sem ter que fazer muitas requisições para o banco de dados, fazendo tudpo de uma vez
		
		Categoria ultima = null; //Criando um atributo para representar a "ultima categoria mostrada"
		
		List<Categoria> categorias = new ArrayList<>();
		
		String sql = "SELECT * FROM CATEGORIA C INNER JOIN PRODUTO P ON C.ID = P.CATEGORIA_ID"; //O comando "INNER JOIN" não foi muito explicado na aula, mas pelo resultado eu imagino que seja algo que junte as duas tabelas fazendo que elas funcionem quase como uma única tabela, com isso esse método poderá fazer com que mostrar as categorias e seus produtos linkados seja quase como listar uma tabela normalmente
		
		try(PreparedStatement pstm = conexao.prepareStatement(sql)) {
			pstm.execute();
			
			try(ResultSet rst = pstm.getResultSet()) {
				while(rst.next()) {
					if(ultima == null || !ultima.getNome().equals(rst.getString(2))) { //Antes nós estavamos tendo um problema que as categorias estavam repetindo para cada produto que se tinha dentro delas, por isso através desse if nós falamos "Só crie uma nova categoria caso a "ultima" seja null ou então caso o seu nome seja diferente das outras
						Categoria categoria = new Categoria(rst.getInt(1), rst.getString(2));
						ultima = categoria; //Aqui inserimos a categoria dentro de "ultima" portanto ela não é mais null e a partir dessa iteração só será criada uma nova categoria caso os nomes sejam diferentes
						categorias.add(categoria);						
					}
					Produto produto = new Produto(rst.getInt(3), rst.getString(4), rst.getString(5)); //Aqui nós criamos os produtos que serão exibidos juntos da categoria, como dá para ver pelos indices as duas tabelas acabam se comportando como uma só grande tabela, por isso acaba deixando o id do produto apenas na 3 coluna
					ultima.adicionar(produto); //E aqui nós adicionamos o produto criado dentro da "ultima"
				}
			}
		}
		return categorias;
		
	}

}
