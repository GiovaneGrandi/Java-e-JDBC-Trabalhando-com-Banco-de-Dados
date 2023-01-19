package br.com.alura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.modelo.Categoria;
import br.com.alura.jdbc.modelo.Produto;

public class ProdutoDAO { //O sufixo DAO se refere a um "Data Access Object" todos os objetos que são usados para fazer comandos diretamente para o SQL são chamados dessa maneira, é uma convenção que esses objetos tenham o sufixo DAO no nome
	
	private Connection conexao;
	
	public ProdutoDAO(Connection conexao) { //O construtor do objeto irá receber uma conexão
		this.conexao = conexao;
	}
	
	public void salvar(Produto produto) throws SQLException { //Aqui nesse método eu estou importando todo o código que usamos para salvar um produto anteriormente, porém encapsulando-o em um objeto não precisaremos repetir código e será mais fácil de fazer manutenções
		
		String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO) VALUES (?, ?)"; 
		
		try(PreparedStatement pstm = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { //Aqui ele irá receber a conexão que foi vinculada ao objeto na assinatura do seu construtor e então executará aquele comando SQL que já estamos acostumados gerando também as chaves primárias
			
			pstm.setString(1, produto.getNome()); //Aqui ele pegará o nome do produto e então irá setar no lugar correto
			pstm.setString(2, produto.getDescricao()); //A mesma coisa para a descrição
			
			pstm.execute(); 
			
			try(ResultSet rst = pstm.getGeneratedKeys()) {
				while(rst.next()) {
					produto.setId(rst.getInt(1)); 
				}
				
			}
			
		}
		
		
	}
	
	public List<Produto> listar() throws SQLException { //Criando um método que será responsável por mostrar todos os produtos da tabela
		List<Produto> produtos = new ArrayList<>(); //Para isso criamos uma ArrayList de produtos
		
		String sql = "SELECT * FROM PRODUTO"; //Guardamos o comando SQL de exibição em uma referencia
		
		try(PreparedStatement pstm = conexao.prepareStatement(sql)) { //Passamos a conexão para o PreparedStatement 
			
			pstm.execute(); //Executamos o statement
			
			try(ResultSet rst = pstm.getResultSet()) {
				
				while(rst.next()) {
					Produto produto = new Produto(rst.getInt(1), rst.getString(2), rst.getString(3)); //Aqui para cada item adicionado nós adicionamos os seus atributos nos devidos lugares utilizando os gets corretos e suas posições no índice
					produtos.add(produto); //Adicionamos o produto a recém criado na lista
				}
				
			}
			
		}
		return produtos; //E por fim retornamos a lista com os produtos presentes na nossa tabela
	}

	public List<Produto> buscar(Categoria ct) throws SQLException {
		
		List<Produto> produtos = new ArrayList<>(); 
		
		String sql = "SELECT * FROM PRODUTO WHERE CATEGORIA_ID = ?"; 
		
		try(PreparedStatement pstm = conexao.prepareStatement(sql)) {  
			pstm.setInt(1, ct.getId());
			pstm.execute(); 
			
			try(ResultSet rst = pstm.getResultSet()) {
				
				while(rst.next()) {
					Produto produto = new Produto(rst.getInt(1), rst.getString(2), rst.getString(3)); 
					produtos.add(produto); 
				}
				
			}
			
		}
		return produtos;
		
	}

}
